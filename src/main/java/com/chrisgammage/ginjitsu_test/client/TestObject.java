package com.chrisgammage.ginjitsu_test.client;

import com.google.inject.ImplementedBy;

/**
 * Created with IntelliJ IDEA.
 * User: gammagec
 * Date: 10/29/12
 * Time: 3:05 PM
 */
@ImplementedBy(TestObjectImpl.class)
public interface TestObject {
  String getTestString();
  boolean isTestAfterInject();

  String generatedMethod();
}
