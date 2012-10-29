package com.chrisgammage.ginjitsu_test.server;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: gammagec
 * Date: 10/29/12
 * Time: 3:07 PM
 */
public class TestGenerator extends Generator {
  @Override
  public String generate(TreeLogger logger,
                         GeneratorContext context,
                         String typeName) throws UnableToCompleteException {
    JClassType classType;
    try {

      classType = context.getTypeOracle().getType(typeName);

      SourceWriter src = getSourceWriter(classType, context, logger);
      if(src == null) {
        return typeName + "Generated";
      }

      src.println("@Override");
      src.println("public String generatedMethod() { return \"test\"; }");

      src.commit(logger);
      System.out.println("Generating for: " + typeName);
      return typeName + "Generated";

    } catch(Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private SourceWriter getSourceWriter(JClassType classType,
                                       GeneratorContext context,
                                       TreeLogger logger) throws UnableToCompleteException {
    String packageName = classType.getPackage().getName();

    String simpleName = classType.getSimpleSourceName() + "Generated";
    ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(
            packageName, simpleName);

    composer.setSuperclass(classType.getQualifiedSourceName());

    PrintWriter printWriter = context.tryCreate(logger, packageName,
            simpleName);
    if(printWriter == null) {
      return null;
    } else {
      SourceWriter sw = composer.createSourceWriter(context, printWriter);
      return sw;
    }
  }
}
