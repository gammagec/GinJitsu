package com.chrisgammage.ginjitsu.server;

import com.chrisgammage.ginjitsu.client.AfterInject;
import com.chrisgammage.ginjitsu.client.GinExtension;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: gammagec
 * Date: 10/29/12
 * Time: 1:22 PM
 */
public class PrintWriterWrapper extends PrintWriter {

  static final private Logger LOG = Logger.getLogger(PrintWriterWrapper.class.getName());

  private final PrintWriter pw;
  private final GinExtension[] ginExtensions;
  private final TreeLogger treeLogger;
  private final GeneratorContext generatorContext;
  private final HashMap<String, String> generatedClasses = new HashMap<String, String>();

  public PrintWriterWrapper(PrintWriter writer,
                            TreeLogger treeLogger,
                            GeneratorContext generatorContext,
                            GinExtension[] ginExtensions) {
    super(writer);
    this.treeLogger = treeLogger;
    this.generatorContext = generatorContext;
    pw = writer;
    this.ginExtensions = ginExtensions;
  }

  private class Scope {
    String clazz;
    public Scope(String clazz) {
      this.clazz = clazz;
    }
  }

  private class BindingScope extends Scope {
    public BindingScope(String clazz) {
      super(clazz);
    }
  }

  private class AssistedInjectScope extends Scope {
    public AssistedInjectScope(String clazz) {
      super(clazz);
    }
  }

  private class SingletonScope extends Scope {
    public SingletonScope(String clazz) {
      super(clazz);
    }
  }

  private Stack<Scope> scopeQueue = new Stack<Scope>();

  public PrintWriter getOriginal() {
    return pw;
  }

  private void writeAfterInjects(String clazz) {

    JClassType clazzType = generatorContext.getTypeOracle().findType(clazz);
    if(clazzType != null) {
      Map<String, JMethod> methods = new HashMap<String, JMethod>();
      for(JClassType c : clazzType.getFlattenedSupertypeHierarchy()) {
        for(JMethod method : c.getMethods()) {
          if(method.isAnnotationPresent(AfterInject.class)) {
            methods.put(method.getName(), method);
          }
        }
      }
      for(JMethod method : methods.values()) {
        super.write("// performing AfterInject\n\t\t");
        super.write("result." + method.getName() + "();\n\t\t");
      }
    }
  }

  @Override
  public void write(String s) {
    LOG.log(Level.FINE, "write " + s);
    if(s.contains("private ") && s.contains(" singleton_Key")) {
      int startInd = s.indexOf("private ") + 8;
      int endInd = s.indexOf(" singleton_Key");
      String clazz = s.substring(startInd, endInd);
      scopeQueue.push(new SingletonScope(clazz));
    } else if(s.contains(" assistedInject_") && s.contains("public ")) {
      int startInd = s.indexOf("public ") + 7;
      int endInd = s.indexOf(" assistedInject_");
      String clazz = s.substring(startInd, endInd);
      scopeQueue.push(new AssistedInjectScope(clazz));
    } else if(s.contains("Binding for ")) {
      int startInd = s.indexOf("Binding for ") + 12;
      int endInd = s.indexOf(" declared at");
      String clazz = s.substring(startInd, endInd);
      scopeQueue.push(new BindingScope(clazz));
    } else if(s.contains("return result;") && !scopeQueue.empty()) {
      Scope scope = scopeQueue.pop();
      if(scope instanceof BindingScope) {
        super.write("// done binding " + scope.clazz + "\n\t\t");
        writeAfterInjects(scope.clazz);
      } else if(scope instanceof AssistedInjectScope) {
        super.write("// done with assisted inject " + scope.clazz + "\n\t\t");
        writeAfterInjects(scope.clazz);
      }
    } else if(s.contains("singleton_Key") && s.contains(" = result")) {
      Scope scope  = scopeQueue.pop();
      writeAfterInjects(scope.clazz);
    } else if(scopeQueue.size() > 0 && scopeQueue.peek() instanceof AssistedInjectScope &&
            s.contains(" result =")) {
      scopeQueue.peek().clazz = s.substring(0, s.indexOf(" result ="));
    } else if(scopeQueue.size() > 0 && scopeQueue.peek() instanceof BindingScope &&
            s.contains("ImplementedBy")) {
      scopeQueue.pop();
    }
    if(ginExtensions != null) {
      if(s.contains("GWT.create(") || s.contains("return new")) {
        boolean isNew = s.contains("return new");
        String className;
        int startInd;
        int endInd;
        if(!isNew) {
          startInd = s.indexOf("GWT.create(") + 11;
          endInd = s.indexOf(".class);");
        } else {
          startInd = s.indexOf("return new") + 11;
          endInd = s.indexOf("(");
        }
        className = s.substring(startInd, endInd);
        for(String generatedClass : generatedClasses.values()) {
          if(className.equals(generatedClass)) {
            super.write(s);
            return;
          }
        }
        JClassType clazz = generatorContext.getTypeOracle().findType(className);
        try {
          for(GinExtension extension : ginExtensions) {
            JClassType extClazz =
                    generatorContext.getTypeOracle().findType(extension.clazz().getCanonicalName());
            if(clazz.isAssignableTo(extClazz)) {
              String newClass = runExtension(clazz, extension.generator());
              assert newClass != null;
              generatedClasses.put(className, newClass);
              LOG.log(Level.INFO, "Executing Extension for " +
                      extension.clazz().getCanonicalName());
              LOG.log(Level.INFO, "writing " + s);
              super.write(s.replaceAll(className, newClass));
              return;
            }
          }
        } catch(Exception e) {
          LOG.log(Level.SEVERE, "Caught exception running extension", e);
        }

      }
    }
    super.write(s);
  }

  private String runExtension(JClassType clazz, Class<? extends Generator> generator)
          throws Exception {
    Generator gen = generator.newInstance();
    return gen.generate(treeLogger, generatorContext, clazz.getQualifiedSourceName());
  }
}
