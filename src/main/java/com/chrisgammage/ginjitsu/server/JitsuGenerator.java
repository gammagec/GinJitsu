package com.chrisgammage.ginjitsu.server;

import com.chrisgammage.ginjitsu.client.GinExtension;
import com.chrisgammage.ginjitsu.client.GinExtensions;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.inject.rebind.GinjectorGenerator;

/**
 * Created with IntelliJ IDEA.
 * User: gammagec
 * Date: 10/29/12
 * Time: 11:44 AM
 */
public class JitsuGenerator extends Generator {

  @Override
  public String generate(TreeLogger treeLogger,
                         GeneratorContext generatorContext,
                         String s) throws UnableToCompleteException {
    GinjectorGenerator ginjectorGenerator = new GinjectorGenerator();

    JClassType clazz = generatorContext.getTypeOracle().findType(s);
    GinExtension[] ginExtensions = null;
    if(clazz.isAnnotationPresent(GinExtensions.class)) {
      ginExtensions = clazz.getAnnotation(GinExtensions.class).value();
    }
    GeneratorContextWrapper gcw =
            new GeneratorContextWrapper(generatorContext, ginExtensions);

    return ginjectorGenerator.generate(treeLogger, gcw, s);
  }
}
