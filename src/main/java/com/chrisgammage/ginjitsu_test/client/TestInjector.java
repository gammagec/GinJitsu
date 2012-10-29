package com.chrisgammage.ginjitsu_test.client;

import com.chrisgammage.ginjitsu.client.GinExtension;
import com.chrisgammage.ginjitsu.client.GinExtensions;
import com.chrisgammage.ginjitsu.client.JitsuInjector;
import com.chrisgammage.ginjitsu_test.server.TestGenerator;
import com.google.gwt.inject.client.GinModules;

/**
 * Created with IntelliJ IDEA.
 * User: gammagec
 * Date: 10/29/12
 * Time: 3:04 PM
 */
@GinExtensions({ @GinExtension(clazz = TestObjectImpl.class,
                               generator = TestGenerator.class) })
@GinModules(TestModule.class)
public interface TestInjector extends JitsuInjector {

  TestObject testObject();
}
