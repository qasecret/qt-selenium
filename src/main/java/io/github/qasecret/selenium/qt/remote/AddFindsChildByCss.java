// Licensed to the Software Freedom Conservancy (SFC) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The SFC licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package io.github.qasecret.selenium.qt.remote;

import com.google.common.collect.ImmutableMap;

import io.github.qasecret.selenium.qt.WebDriverException;
import io.github.qasecret.selenium.qt.internal.FindsByCssSelector;

import java.util.Map;

/**
 * @deprecated Everything now finds by css
 */
@Deprecated
public class AddFindsChildByCss implements AugmenterProvider {
  public Class<?> getDescribedInterface() {
    return FindsByCssSelector.class;
  }

  public InterfaceImplementation getImplementation(Object value) {
    return (executeMethod, self, method, args) -> {
      Object id = ((RemoteWebElement) self).getId();
      Map<String, ?> commandArgs =
          ImmutableMap.of("id", id, "using", "css selector", "value", args[0]);

      if ("findElementByCssSelector".equals(method.getName())) {
        return executeMethod.execute(DriverCommand.FIND_ELEMENT, commandArgs);
      } else if ("findElementsByCssSelector".equals(method.getName())) {
        return executeMethod.execute(DriverCommand.FIND_ELEMENTS, commandArgs);
      }

      throw new WebDriverException("Unmapped method: " + method.getName());
    };
  }
}
