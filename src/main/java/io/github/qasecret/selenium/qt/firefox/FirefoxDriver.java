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

package io.github.qasecret.selenium.qt.firefox;

import static java.util.Collections.singletonMap;
import static io.github.qasecret.selenium.qt.remote.CapabilityType.PROXY;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import io.github.qasecret.selenium.qt.Capabilities;
import io.github.qasecret.selenium.qt.ImmutableCapabilities;
import io.github.qasecret.selenium.qt.MutableCapabilities;
import io.github.qasecret.selenium.qt.Proxy;
import io.github.qasecret.selenium.qt.WebDriverException;
import io.github.qasecret.selenium.qt.html5.LocalStorage;
import io.github.qasecret.selenium.qt.html5.SessionStorage;
import io.github.qasecret.selenium.qt.html5.WebStorage;
import io.github.qasecret.selenium.qt.remote.CommandExecutor;
import io.github.qasecret.selenium.qt.remote.CommandInfo;
import io.github.qasecret.selenium.qt.remote.FileDetector;
import io.github.qasecret.selenium.qt.remote.RemoteWebDriver;
import io.github.qasecret.selenium.qt.remote.html5.RemoteWebStorage;
import io.github.qasecret.selenium.qt.remote.http.HttpMethod;
import io.github.qasecret.selenium.qt.remote.service.DriverCommandExecutor;
import io.github.qasecret.selenium.qt.remote.service.DriverService;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;

/**
 * An implementation of the {#link WebDriver} interface that drives Firefox.
 * <p>
 * The best way to construct a {@code FirefoxDriver} with various options is to make use of the
 * {@link FirefoxOptions}, like so:
 *
 * <pre>
 *FirefoxOptions options = new FirefoxOptions()
 *    .setProfile(new FirefoxProfile());
 *WebDriver driver = new FirefoxDriver(options);
 * </pre>
 */
public class FirefoxDriver extends RemoteWebDriver implements WebStorage, HasExtensions {

  public static final class SystemProperty {

    /**
     * System property that defines the location of the Firefox executable file.
     */
    public static final String BROWSER_BINARY = "webdriver.firefox.bin";

    /**
     * System property that defines the location of the file where Firefox log should be stored.
     */
    public static final String BROWSER_LOGFILE = "webdriver.firefox.logfile";

    /**
     * System property that defines the additional library path (Linux only).
     */
    public static final String BROWSER_LIBRARY_PATH = "webdriver.firefox.library.path";

    /**
     * System property that defines the profile that should be used as a template.
     * When the driver starts, it will make a copy of the profile it is using,
     * rather than using that profile directly.
     */
    public static final String BROWSER_PROFILE = "webdriver.firefox.profile";

    /**
     * System property that defines the location of the webdriver.xpi browser extension to install
     * in the browser. If not set, the prebuilt extension bundled with this class will be used.
     */
    public static final String DRIVER_XPI_PROPERTY = "webdriver.firefox.driver";

    /**
     * Boolean system property that instructs FirefoxDriver to use Marionette backend,
     * overrides any capabilities specified by the user
     */
    public static final String DRIVER_USE_MARIONETTE = "webdriver.firefox.marionette";
  }

  public static final String BINARY = "firefox_binary";
  public static final String PROFILE = "firefox_profile";
  public static final String MARIONETTE = "marionette";

  private static class ExtraCommands {
    static String INSTALL_EXTENSION = "installExtension";
    static String UNINSTALL_EXTENSION = "uninstallExtension";
  }

  private static final ImmutableMap<String, CommandInfo> EXTRA_COMMANDS = ImmutableMap.of(
      ExtraCommands.INSTALL_EXTENSION,
      new CommandInfo("/session/:sessionId/moz/addon/install", HttpMethod.POST),
      ExtraCommands.UNINSTALL_EXTENSION,
      new CommandInfo("/session/:sessionId/moz/addon/uninstall", HttpMethod.POST)
  );

  private static class FirefoxDriverCommandExecutor extends DriverCommandExecutor {
    public FirefoxDriverCommandExecutor(DriverService service) {
      super(service, EXTRA_COMMANDS);
    }
  }

  protected FirefoxBinary binary;
  private RemoteWebStorage webStorage;

  public FirefoxDriver() {
    this(new FirefoxOptions());
  }

  /**
   * @deprecated Use {@link #FirefoxDriver(FirefoxOptions)}.
   */
  @Deprecated
  public FirefoxDriver(Capabilities desiredCapabilities) {
    this(new FirefoxOptions(Objects.requireNonNull(desiredCapabilities, "No capabilities seen")));
  }

  /**
   * @deprecated Use {@link #FirefoxDriver(GeckoDriverService, FirefoxOptions)}.
   */
  @Deprecated
  public FirefoxDriver(GeckoDriverService service, Capabilities desiredCapabilities) {
    this(
        Objects.requireNonNull(service, "No geckodriver service provided"),
        new FirefoxOptions(desiredCapabilities));
  }

  public FirefoxDriver(FirefoxOptions options) {
    super(toExecutor(options), dropCapabilities(options));
    webStorage = new RemoteWebStorage(getExecuteMethod());
  }

  public FirefoxDriver(GeckoDriverService service) {
    super(new FirefoxDriverCommandExecutor(service), new FirefoxOptions());
    webStorage = new RemoteWebStorage(getExecuteMethod());
  }

  public FirefoxDriver(XpiDriverService service) {
    super(new FirefoxDriverCommandExecutor(service), new FirefoxOptions());
    webStorage = new RemoteWebStorage(getExecuteMethod());
  }

  public FirefoxDriver(GeckoDriverService service, FirefoxOptions options) {
    super(new FirefoxDriverCommandExecutor(service), dropCapabilities(options));
    webStorage = new RemoteWebStorage(getExecuteMethod());
  }

  public FirefoxDriver(XpiDriverService service, FirefoxOptions options) {
    super(new FirefoxDriverCommandExecutor(service), dropCapabilities(options));
    webStorage = new RemoteWebStorage(getExecuteMethod());
  }

  private static CommandExecutor toExecutor(FirefoxOptions options) {
    Objects.requireNonNull(options, "No options to construct executor from");
    DriverService.Builder<?, ?> builder;

    if (! Boolean.parseBoolean(System.getProperty(SystemProperty.DRIVER_USE_MARIONETTE, "true"))
        || options.isLegacy()) {
      FirefoxProfile profile = options.getProfile();
      if (profile == null) {
        profile = new FirefoxProfile();
        options.setCapability(FirefoxDriver.PROFILE, profile);
      }
      builder = XpiDriverService.builder()
          .withBinary(options.getBinary())
          .withProfile(profile);
    } else {
      builder = new GeckoDriverService.Builder()
          .usingFirefoxBinary(options.getBinary());
    }

    return new FirefoxDriverCommandExecutor(builder.build());
  }

  @Override
  public void setFileDetector(FileDetector detector) {
    throw new WebDriverException(
        "Setting the file detector only works on remote webdriver instances obtained " +
        "via RemoteWebDriver");
  }

  @Override
  public LocalStorage getLocalStorage() {
    return webStorage.getLocalStorage();
  }

  @Override
  public SessionStorage getSessionStorage() {
    return webStorage.getSessionStorage();
  }

  private static boolean isLegacy(Capabilities desiredCapabilities) {
    Boolean forceMarionette = forceMarionetteFromSystemProperty();
    if (forceMarionette != null) {
      return !forceMarionette;
    }
    Object marionette = desiredCapabilities.getCapability(MARIONETTE);
    return marionette instanceof Boolean && ! (Boolean) marionette;
  }

  @Override
  public String installExtension(Path path) {
    return (String) execute(ExtraCommands.INSTALL_EXTENSION,
                            ImmutableMap.of("path", path.toAbsolutePath().toString(),
                                            "temporary", false)).getValue();
  }

  @Override
  public void uninstallExtension(String extensionId) {
    execute(ExtraCommands.UNINSTALL_EXTENSION, singletonMap("id", extensionId));
  }

  private static Boolean forceMarionetteFromSystemProperty() {
    String useMarionette = System.getProperty(SystemProperty.DRIVER_USE_MARIONETTE);
    if (useMarionette == null) {
      return null;
    }
    return Boolean.valueOf(useMarionette);
  }

  /**
   * Drops capabilities that we shouldn't send over the wire.
   *
   * Used for capabilities which aren't BeanToJson-convertable, and are only used by the local
   * launcher.
   */
  private static Capabilities dropCapabilities(Capabilities capabilities) {
    if (capabilities == null) {
      return new ImmutableCapabilities();
    }

    MutableCapabilities caps;

    if (isLegacy(capabilities)) {
      final Set<String> toRemove = Sets.newHashSet(BINARY, PROFILE);
      caps = new MutableCapabilities(
          Maps.filterKeys(capabilities.asMap(), key -> !toRemove.contains(key)));
    } else {
      caps = new MutableCapabilities(capabilities);
    }

    // Ensure that the proxy is in a state fit to be sent to the extension
    Proxy proxy = Proxy.extractFrom(capabilities);
    if (proxy != null) {
      caps.setCapability(PROXY, proxy);
    }

    return caps;
  }
}
