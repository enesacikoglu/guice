/**
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.inject.visitable;

import com.google.inject.matcher.Matcher;
import com.google.inject.Binder;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * Immutable snapshot of a request to bind an interceptor.
 *
 * @author jessewilson@google.com (Jesse Wilson)
 */
public final class BindInterceptorCommand implements Command {
  
  private final Matcher<? super Class<?>> classMatcher;
  private final Matcher<? super Method> methodMatcher;
  private final MethodInterceptor[] interceptors;

  BindInterceptorCommand(
      Matcher<? super Class<?>> classMatcher,
      Matcher<? super Method> methodMatcher,
      MethodInterceptor[] interceptors) {
    this.classMatcher = classMatcher;
    this.methodMatcher = methodMatcher;
    this.interceptors = interceptors;
  }

  public Matcher<? super Class<?>> getClassMatcher() {
    return classMatcher;
  }

  public Matcher<? super Method> getMethodMatcher() {
    return methodMatcher;
  }

  public MethodInterceptor[] getInterceptors() {
    return interceptors;
  }

  public void execute(Binder binder) {
    binder.bindInterceptor(classMatcher, methodMatcher, interceptors);
  }

  public <T> T acceptVisitor(BinderVisitor<T> visitor) {
    return visitor.visitBindInterceptor(this);
  }
}
