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

import com.google.common.base.Joiner;

import io.github.qasecret.selenium.webdriven.ElementFinder;

import io.github.qasecret.selenium.qt.JavascriptExecutor;
import io.github.qasecret.selenium.qt.WebDriver;
import io.github.qasecret.selenium.qt.WebElement;
import io.github.qasecret.selenium.webdriven.SeleneseCommand;

public class GetCursorPosition extends SeleneseCommand<Number> {

  private final ElementFinder finder;

  public GetCursorPosition(ElementFinder finder) {
    this.finder = finder;
  }

  @Override
  protected Number handleSeleneseCommand(WebDriver driver, String locator, String value) {
    // All supported browsers apparently support "document.selection". Let's use that and the
    // relevant snippet of code from the original selenium core to implement this. What could
    // possibly go wrong?

    WebElement element = finder.findElement(driver, locator);

    return (Number) ((JavascriptExecutor) driver).executeScript(
      Joiner.on("\n").join(
        "try {",
        "  var selectRange = document.selection.createRange().duplicate();",
        "  var elementRange = arguments[0].createTextRange();",
        "  selectRange.move('character', 0)",
        "  elementRange.move('character', 0);",
        "  var inRange1 = selectRange.inRange(elementRange);",
        "  var inRange2 = elementRange.inRange(selectRange);",
        "  elementRange.setEndPoint('EndToEnd', selectRange);",
        "} catch (e) {",
        "  throw Error('There is no cursor on this page!');",
        "}",
        "return String(elementRange.text).replace(/\r/g,' ').length;"),
      element);
  }
}
