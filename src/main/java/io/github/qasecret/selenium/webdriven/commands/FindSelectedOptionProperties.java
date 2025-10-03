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

package io.github.qasecret.selenium.webdriven.commands;

import io.github.qasecret.selenium.webdriven.ElementFinder;
import io.github.qasecret.selenium.webdriven.JavascriptLibrary;
import io.github.qasecret.selenium.webdriven.SeleneseCommand;

import io.github.qasecret.selenium.qt.WebDriver;
import io.github.qasecret.selenium.qt.WebElement;

import java.util.List;

public class FindSelectedOptionProperties extends SeleneseCommand<String[]> {

  private JavascriptLibrary library;
  private final ElementFinder finder;
  private final String property;

  public FindSelectedOptionProperties(JavascriptLibrary library, ElementFinder finder,
      String property) {
    this.library = library;
    this.finder = finder;
    this.property = property;
  }

  @Override
  protected String[] handleSeleneseCommand(WebDriver driver, String selectLocator, String ignored) {
    SeleniumSelect select = new SeleniumSelect(library, finder, driver, selectLocator);
    List<WebElement> allOptions = select.getSelectedOptions();
    String[] values = new String[allOptions.size()];

    for (int i = 0; i < allOptions.size(); i++) {
      WebElement element = allOptions.get(i);
      values[i] = element.getAttribute(property);
    }

    return values;
  }
}
