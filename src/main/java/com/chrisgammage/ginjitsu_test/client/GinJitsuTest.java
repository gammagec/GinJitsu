package com.chrisgammage.ginjitsu_test.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

/**
 * Created with IntelliJ IDEA.
 * User: gammagec
 * Date: 10/29/12
 * Time: 3:03 PM
 */
public class GinJitsuTest implements EntryPoint {
  public void onModuleLoad() {
    TestInjector injector = GWT.create(TestInjector.class);
    TestObject obj = injector.testObject();
    assert obj.getTestString() != null;
    assert obj.getTestString().equals("test");

    assert obj.generatedMethod() != null;
    assert obj.generatedMethod().equals("test");

    assert obj.isTestAfterInject();

    AssistedObjImpl assObj = injector.factory().getAssistedObj("test");
    assert assObj.getText() != null;
    assert assObj.getText().endsWith("test");
    assert assObj.isAfterInjected();

    assert injector.testSingleton().isAfterInjected();

    Window.alert("test passed");
  }
}
