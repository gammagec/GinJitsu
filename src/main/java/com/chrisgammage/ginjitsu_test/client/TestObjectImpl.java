package com.chrisgammage.ginjitsu_test.client;

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

  @Override
  public String getTestString() {
    return testString;
  }
}
