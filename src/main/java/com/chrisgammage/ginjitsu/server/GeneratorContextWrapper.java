package com.chrisgammage.ginjitsu.server;

import com.chrisgammage.ginjitsu.client.GinExtension;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.Artifact;
import com.google.gwt.core.ext.linker.GeneratedResource;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.dev.resource.ResourceOracle;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: gammagec
 * Date: 10/29/12
 * Time: 1:01 PM
 */
public class GeneratorContextWrapper implements GeneratorContext {

  private final GeneratorContext generatorContext;

  static final private Logger LOG = Logger.getLogger(GeneratorContextWrapper.class.getName());

  static {
    LOG.setLevel(Level.WARNING);
  }

  private final GinExtension[] ginExtensions;

  public GeneratorContextWrapper(GeneratorContext generatorContext,
                                 GinExtension[] ginExtensions) {
    this.generatorContext = generatorContext;
    this.ginExtensions = ginExtensions;
  }

  @Override
  public boolean checkRebindRuleAvailable(String s) {
    LOG.log(Level.INFO, "checkRebindRuleAvailable " + s);
    return generatorContext.checkRebindRuleAvailable(s);
  }

  @Override
  public void commit(TreeLogger treeLogger, PrintWriter writer) {
    LOG.log(Level.INFO, "commit");
    if(writer instanceof PrintWriterWrapper) {
      generatorContext.commit(treeLogger,
              ((PrintWriterWrapper)writer).getOriginal());
    } else {
      generatorContext.commit(treeLogger, writer);
    }
  }

  @Override
  public void commitArtifact(TreeLogger treeLogger, Artifact<?> artifact) throws UnableToCompleteException {
    LOG.log(Level.INFO, "commitArtifact");
    generatorContext.commitArtifact(treeLogger, artifact);
  }

  @Override
  public GeneratedResource commitResource(TreeLogger treeLogger, OutputStream outputStream) throws UnableToCompleteException {
    LOG.log(Level.INFO, "commitResource");
    return generatorContext.commitResource(treeLogger, outputStream);
  }

  @Override
  public PropertyOracle getPropertyOracle() {
    return generatorContext.getPropertyOracle();
  }

  @Override
  public ResourceOracle getResourcesOracle() {
    LOG.log(Level.INFO, "getResourcesOracle");
    return generatorContext.getResourcesOracle();
  }

  @Override
  public TypeOracle getTypeOracle() {
    LOG.log(Level.INFO, "getTypeOracle");
    return generatorContext.getTypeOracle();
  }

  @Override
  public PrintWriter tryCreate(TreeLogger treeLogger, String s, String s1) {
    LOG.log(Level.INFO, "tryCreate " + s + " " + s1);
    PrintWriter pw = generatorContext.tryCreate(treeLogger, s, s1);
    if(pw == null) {
      return null;
    }
    return new PrintWriterWrapper(
            pw,
            treeLogger,
            generatorContext,
            ginExtensions);
  }

  @Override
  public OutputStream tryCreateResource(TreeLogger treeLogger, String s) throws UnableToCompleteException {
    LOG.log(Level.INFO, "tryCreateResource " + s);
    return generatorContext.tryCreateResource(treeLogger, s);
  }
}
