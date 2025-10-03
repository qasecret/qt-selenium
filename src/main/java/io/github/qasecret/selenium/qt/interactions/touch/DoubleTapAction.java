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

package io.github.qasecret.selenium.qt.interactions.touch;

import io.github.qasecret.selenium.qt.interactions.Action;
import io.github.qasecret.selenium.qt.interactions.Locatable;
import io.github.qasecret.selenium.qt.interactions.TouchScreen;
import io.github.qasecret.selenium.qt.interactions.internal.TouchAction;

/**
 * Creates a double tap gesture on a touch screen.
 */
@Deprecated
public class DoubleTapAction extends TouchAction implements Action {

  public DoubleTapAction(TouchScreen touchScreen, Locatable locationProvider) {
    super(touchScreen, locationProvider);
  }

  public void perform() {
    touchScreen.doubleTap(getActionLocation());
  }

}
