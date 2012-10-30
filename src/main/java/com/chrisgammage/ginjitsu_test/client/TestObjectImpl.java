package com.chrisgammage.ginjitsu_test.client;

import com.chrisgammage.ginjitsu.client.AfterInject;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: gammagec
 * Date: 10/29/12
 * Time: 3:05 PM
 */
public abstract class TestObjectImpl implements TestObject {

  @Inject @Named("testString")
  private String testString;

  boolean testAfterInject;

  @AfterInject
  void afterInject() {
    assert testString != null;
    assert testString.equals("test");
    testAfterInject = true;
  }

  @Override
  public boolean isTestAfterInject() {
    return testAfterInject;
  }

  @Override
  public String getTestString() {
    return testString;
  }
}
