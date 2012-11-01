package com.chrisgammage.ginjitsu_test.client;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;

import static com.google.inject.name.Names.named;

/**
 * Created with IntelliJ IDEA.
 * User: gammagec
 * Date: 10/29/12
 * Time: 3:09 PM
 */
public class TestModule extends AbstractGinModule {
  @Override
  protected void configure() {
    bindConstant().annotatedWith(named("testString")).to("test");

    install(new GinFactoryModuleBuilder()
            .implement(AssistedObj.class, AssistedObjImpl.class)
            .build(TestFactory.class));
  }
}
