package com.chrisgammage.ginjitsu.client;

import com.google.gwt.core.ext.Generator;

/**
 * Created with IntelliJ IDEA.
 * User: gammagec
 * Date: 10/29/12
 * Time: 2:12 PM
 */
public @interface GinExtension {
  Class<?> clazz();
  Class<? extends Generator> generator();
}
