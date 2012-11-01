package com.chrisgammage.ginjitsu_test.client;

import com.chrisgammage.ginjitsu.client.AfterInject;

import javax.inject.Singleton;

/**
 * Created with IntelliJ IDEA.
 * User: gammagec
 * Date: 10/31/12
 * Time: 3:28 PM
 */
@Singleton
public class TestSingleton {

  private boolean afterInjected;

  @AfterInject
  public void afterInject() {
    afterInjected = true;
  }

  public boolean isAfterInjected() {
    return afterInjected;
  }
}
