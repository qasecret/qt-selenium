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

// Generated source.
package io.github.qasecret.selenium.qt.lift;

import io.github.qasecret.selenium.qt.lift.find.DivFinder;
import io.github.qasecret.selenium.qt.lift.find.HtmlTagFinder;
import io.github.qasecret.selenium.qt.lift.find.ImageFinder;
import io.github.qasecret.selenium.qt.lift.find.InputFinder;
import io.github.qasecret.selenium.qt.lift.find.LinkFinder;
import io.github.qasecret.selenium.qt.lift.find.PageTitleFinder;
import io.github.qasecret.selenium.qt.lift.find.TableCellFinder;
import io.github.qasecret.selenium.qt.lift.find.TableFinder;
import org.hamcrest.Description;
import io.github.qasecret.selenium.qt.WebDriver;
import io.github.qasecret.selenium.qt.WebElement;
import io.github.qasecret.selenium.qt.lift.find.BaseFinder;
import io.github.qasecret.selenium.qt.lift.find.Finder;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;


public class Finders {

  public static HtmlTagFinder div() {
    return DivFinder.div();
  }

  public static HtmlTagFinder div(String id) {
    return DivFinder.div(id);
  }

  public static HtmlTagFinder link() {
    return LinkFinder.link();
  }

  public static HtmlTagFinder link(java.lang.String anchorText) {
    return LinkFinder.link(anchorText);
  }

  public static HtmlTagFinder links() {
    return LinkFinder.links();
  }

  public static HtmlTagFinder titles() {
    return PageTitleFinder.titles();
  }

  public static HtmlTagFinder title() {
    return PageTitleFinder.title();
  }

  public static HtmlTagFinder title(String title) {
    return PageTitleFinder.title(title);
  }

  public static HtmlTagFinder images() {
    return ImageFinder.images();
  }

  public static HtmlTagFinder image() {
    return ImageFinder.image();
  }

  public static HtmlTagFinder table() {
    return TableFinder.table();
  }

  public static HtmlTagFinder tables() {
    return TableFinder.tables();
  }

  public static HtmlTagFinder cell() {
    return TableCellFinder.cell();
  }

  public static HtmlTagFinder cells() {
    return TableCellFinder.cells();
  }

  public static HtmlTagFinder imageButton() {
    return InputFinder.imageButton();
  }

  public static HtmlTagFinder imageButton(String label) {
    return InputFinder.imageButton(label);
  }

  public static HtmlTagFinder radioButton() {
    return InputFinder.radioButton();
  }

  public static HtmlTagFinder radioButton(String id) {
    return InputFinder.radioButton(id);
  }

  public static HtmlTagFinder textbox() {
    return InputFinder.textbox();
  }

  public static HtmlTagFinder button() {
    return InputFinder.submitButton();
  }

  public static HtmlTagFinder button(String label) {
    return InputFinder.submitButton(label);
  }

  /**
   * A finder which returns the first element matched - such as if you have multiple elements which
   * match the finder (such as multiple links with the same text on a page etc)
   *
   * @param finder finder from which context to search
   * @return finder that will return the first match
   */
  public static Finder<WebElement, WebDriver> first(final Finder<WebElement, WebDriver> finder) {
    return new BaseFinder<WebElement, WebDriver>() {

      @Override
      public Collection<WebElement> findFrom(WebDriver context) {
        Collection<WebElement> collection = super.findFrom(context);
        if (!collection.isEmpty()) {
          Iterator<WebElement> iter = collection.iterator();
          iter.hasNext();
          return Collections.singletonList(iter.next());
        }
        return collection;
      }

      @Override
      protected Collection<WebElement> extractFrom(WebDriver context) {
        return finder.findFrom(context);
      }

      @Override
      protected void describeTargetTo(Description description) {
        description.appendText("first ");
        finder.describeTo(description);
      }
    };
  }
}
