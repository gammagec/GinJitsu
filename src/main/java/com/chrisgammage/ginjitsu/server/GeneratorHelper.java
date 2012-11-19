package com.chrisgammage.ginjitsu.server;

import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JConstructor;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.user.rebind.SourceWriter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: gammagec
 * Date: 10/31/12
 * Time: 3:05 PM
 */
public class GeneratorHelper {

  private GeneratorHelper() {
  }
  static public void writeConstructor(SourceWriter src, JClassType classType)
          throws UnableToCompleteException {

    src.print("public " + classType.getName() + "Generated(");
    if(classType.getConstructors().length == 0) {
      src.println(") {");
    } else if(classType.getConstructors().length == 1) {
      int cnt = 0;
      JConstructor constructor = classType.getConstructors()[0];
      if(constructor.getParameters().length == 0) {
      } else {
        for(JParameter p : constructor.getParameters()) {
          if(cnt != 0) {
            src.print(", ");
          }
          src.print(p.getType().getQualifiedSourceName() + " p" + cnt);
          cnt++;
        }
      }
      src.println(") {");
      src.print("super(");
      cnt = 0;
      for(JParameter p : constructor.getParameters()) {
        if(cnt != 0) {
          src.print(", ");
        }
        src.print("p" + cnt);
        cnt++;
      }
      src.println(");");
    } else {
      throw new UnableToCompleteException();
    }
    src.println("}");
  }

  public static Set<JMethod> getAllMethods(JClassType classType) {
    Set<JMethod> methods = new HashSet<JMethod>();
    for(JClassType c : classType.getFlattenedSupertypeHierarchy()) {
      for(JMethod method : c.getMethods()) {
        methods.add(method);
      }
    }
    return methods;
  }
}
