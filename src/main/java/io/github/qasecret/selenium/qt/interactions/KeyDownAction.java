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

package io.github.qasecret.selenium.qt.interactions;

import io.github.qasecret.selenium.qt.Keys;
import io.github.qasecret.selenium.qt.WebElement;
import io.github.qasecret.selenium.qt.interactions.internal.SingleKeyAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Emulates key press only, without the release.
 *
 * @deprecated Use {@link Actions#keyDown(WebElement, CharSequence)}
 */
@Deprecated
public class KeyDownAction extends SingleKeyAction implements Action {
  public KeyDownAction(Keyboard keyboard, Mouse mouse, Locatable locationProvider, Keys key) {
    super(keyboard, mouse, locationProvider, key);
  }

  public KeyDownAction(Keyboard keyboard, Mouse mouse, Keys key) {
    super(keyboard, mouse, key);
  }

  public void perform() {
    focusOnElement();

    keyboard.pressKey(key);
  }

  @Override
  public List<Interaction> asInteractions(PointerInput mouse, KeyInput keyboard) {
    ArrayList<Interaction> interactions = new ArrayList<>();

    interactions.addAll(optionallyClickElement(mouse));
    interactions.add(keyboard.createKeyDown(key.getCodePoint()));

    return Collections.unmodifiableList(interactions);
  }
}
