# Selenium-QT (Selenium Relocation Library)

A specialized library that provides a relocated version of Selenium WebDriver (version 3.141.0) to avoid version conflicts in your test automation projects. All Selenium packages are relocated to `io.github.qasecret.selenium-qt` namespace.

## Purpose

This library solves the common problem of using multiple Selenium versions in a single project by relocating all Selenium 3.141.0 classes to a different package namespace. This allows you to:

- Use multiple Selenium versions in the same project without conflicts
- Maintain legacy tests with older Selenium versions while writing new tests with newer versions
- Avoid dependency hell with Selenium-dependent libraries

## Features

- Complete relocation of Selenium 3.141.0 packages to `io.github.qasecret.selenium-qt`
- Maintains all original Selenium functionality
- Compatible with both Java and Kotlin projects
- Zero conflict guarantee with other Selenium versions

## Installation

### Gradle

```groovy
repositories {
    mavenCentral()
    // Add your repository where the relocated jar is hosted
}

dependencies {
    implementation 'io.github.qasecret:selenium-qt:1.0.0'  // Based on Selenium 3.141.0
}
```

### Maven

```xml
<dependency>
    <groupId>io.github.qasecret</groupId>
    <artifactId>selenium-qt</artifactId>
    <version>1.0.0</version>  <!-- Based on Selenium 3.141.0 -->
</dependency>
```

## Usage

### Basic Example

Instead of using the original Selenium packages, use the relocated ones:

```kotlin
import io.github.qasecret.selenium_qt.WebDriver
import io.github.qasecret.selenium_qt.chrome.ChromeDriver
import io.github.qasecret.selenium_qt.support.ui.WebDriverWait

fun main() {
    val driver: WebDriver = ChromeDriver()
    driver.get("https://www.example.com")
    
    // Use other Selenium features as normal, but with the relocated package
    val wait = WebDriverWait(driver, 10)
    // ... rest of your code
}
```

### Using Multiple Selenium Versions

You can now use both the relocated and standard Selenium versions in the same project:

```kotlin
// Using relocated Selenium 3.141.0
import io.github.qasecret.selenium_qt.WebDriver as RelocatedWebDriver
import io.github.qasecret.selenium_qt.chrome.ChromeDriver as RelocatedChromeDriver

// Using standard Selenium (different version)
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

fun main() {
    // Using relocated version
    val driver1: RelocatedWebDriver = RelocatedChromeDriver()
    
    // Using standard version
    val driver2: WebDriver = ChromeDriver()
}
```

## Building from Source

1. Clone the repository:
```bash
git clone https://github.com/your-username/selenium-qt.git
```

2. Build using Gradle:
```bash
./gradlew shadowJar
```

The relocated jar will be available in `build/libs/`.

## Technical Details

### Relocated Packages

All packages under `org.openqa.selenium` are relocated to `io.github.qasecret.selenium-qt`, including:
- Core Selenium packages
- WebDriver implementations (Chrome, Firefox, etc.)
- Support packages
- Grid components

### Implementation

The relocation is implemented using the Gradle Shadow plugin, which handles the complete package renaming while maintaining all functionality.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Support

For issues and questions, please create an issue in the GitHub repository.

---

**Note:** This is a specialized tool for handling Selenium version conflicts. Make sure you understand the implications of using relocated packages in your project before implementing.
