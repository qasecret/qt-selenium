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

package io.github.qasecret.selenium.qt.remote.html5;

import io.github.qasecret.selenium.qt.html5.AppCacheStatus;
import io.github.qasecret.selenium.qt.html5.ApplicationCache;
import io.github.qasecret.selenium.qt.remote.DriverCommand;
import io.github.qasecret.selenium.qt.remote.ExecuteMethod;

/**
 * Provides remote access to the {@link ApplicationCache} API.
 */
public class RemoteApplicationCache implements ApplicationCache {

  private final ExecuteMethod executeMethod;

  public RemoteApplicationCache(ExecuteMethod executeMethod) {
    this.executeMethod = executeMethod;
  }

  @Override
  public AppCacheStatus getStatus() {
    String result = (String) executeMethod.execute(DriverCommand.GET_APP_CACHE_STATUS, null);
    return AppCacheStatus.valueOf(result);
  }
}
