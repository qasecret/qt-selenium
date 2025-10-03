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

package io.github.qasecret.selenium.qt.ie;

import static io.github.qasecret.selenium.qt.remote.CapabilityType.BROWSER_NAME;

import com.google.auto.service.AutoService;

import io.github.qasecret.selenium.qt.Capabilities;
import io.github.qasecret.selenium.qt.ImmutableCapabilities;
import io.github.qasecret.selenium.qt.SessionNotCreatedException;
import io.github.qasecret.selenium.qt.WebDriver;
import io.github.qasecret.selenium.qt.WebDriverException;
import io.github.qasecret.selenium.qt.WebDriverInfo;
import io.github.qasecret.selenium.qt.remote.BrowserType;

import java.util.Optional;

@AutoService(WebDriverInfo.class)
public class InternetExplorerDriverInfo implements WebDriverInfo {

  @Override
  public String getDisplayName() {
    return "Internet Explorer";
  }

  @Override
  public Capabilities getCanonicalCapabilities() {
    return new ImmutableCapabilities(BROWSER_NAME, BrowserType.IE);
  }

  @Override
  public boolean isSupporting(Capabilities capabilities) {
    return BrowserType.IE.equals(capabilities.getBrowserName()) ||
           capabilities.getCapability("se:ieOptions") != null;
  }

  @Override
  public boolean isAvailable() {
    try {
      InternetExplorerDriverService.createDefaultService();
      return true;
    } catch (IllegalStateException | WebDriverException e) {
      return false;
    }
  }

  @Override
  public int getMaximumSimultaneousSessions() {
    return 1;
  }

  @Override
  public Optional<WebDriver> createDriver(Capabilities capabilities)
      throws SessionNotCreatedException {
    if (!isAvailable()) {
      return Optional.empty();
    }

    return Optional.of(new InternetExplorerDriver(capabilities));
  }
}
