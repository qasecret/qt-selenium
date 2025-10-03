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

import com.google.common.annotations.VisibleForTesting;

import io.github.qasecret.selenium.webdriven.ElementFinder;
import io.github.qasecret.selenium.webdriven.JavascriptLibrary;
import io.github.qasecret.selenium.webdriven.SeleneseCommand;

import io.github.qasecret.selenium.qt.WebDriver;
import io.github.qasecret.selenium.qt.WebDriverException;
import io.github.qasecret.selenium.qt.WebElement;

public class GetAttribute extends SeleneseCommand<String> {
  private final String getAttribute;
  private final JavascriptLibrary library;
  private final ElementFinder finder;

  public GetAttribute(JavascriptLibrary library, ElementFinder finder) {
    this.library = library;
    this.finder = finder;
    getAttribute =
        "return (" + library.getSeleniumScript("getAttribute.js") + ").apply(null, arguments);";
  }

  @Override
  protected String handleSeleneseCommand(WebDriver driver, String attributeLocator, String ignored) {
    try {
      return (String) library.executeScript(driver, getAttribute, attributeLocator);
    } catch (WebDriverException e) {
      String[] nameAndAttribute = getNameAndAttribute(attributeLocator);

      WebElement element = finder.findElement(driver, nameAndAttribute[0]);
      return element.getAttribute(nameAndAttribute[1]);
    }
  }

  @VisibleForTesting
  protected String[] getNameAndAttribute(String attributeLocator) {
    int atSign = attributeLocator.lastIndexOf("@");

    String[] toReturn = new String[2];

    toReturn[0] = attributeLocator.substring(0, atSign);
    toReturn[1] = attributeLocator.substring(atSign + 1);

    return toReturn;
  }
}
