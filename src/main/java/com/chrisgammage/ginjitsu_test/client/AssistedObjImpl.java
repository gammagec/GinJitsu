package com.chrisgammage.ginjitsu_test.client;

import com.chrisgammage.ginjitsu.client.AfterInject;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: gammagec
 * Date: 10/31/12
 * Time: 2:58 PM
 */
public abstract class AssistedObjImpl implements AssistedObj {

  private String text;
  private boolean afterInjected;

  @Inject
  public AssistedObjImpl(@Assisted String text) {
    this.text = text;
  }

  @AfterInject
  public void afterInject() {
    assert afterInjected == false;
    afterInjected = true;
  }

  public String getText() {
    return text;
  }

  public boolean isAfterInjected() {
    return afterInjected;
  }
}
