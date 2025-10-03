# Selenium‑QT

A small, repackaged Selenium WebDriver library that relocates Selenium 3.141.0 classes so you can use multiple Selenium versions side-by-side.

Key points
- Relocated packages: org.openqa.selenium → io.github.qasecret.selenium-qt
- Library coordinates: io.github.qasecret:selenium-qt:1.0.0
- Purpose: avoid classpath/package conflicts between different Selenium versions

Quick start

Gradle (recommended)

```groovy
repositories {
  mavenCentral()
}

dependencies {
  implementation 'io.github.qasecret:selenium-qt:1.0.0'
}
```

Maven

```xml
<dependency>
  <groupId>io.github.qasecret</groupId>
  <artifactId>selenium-qt</artifactId>
  <version>1.0.0</version>
</dependency>
```

How to import the relocated classes

- Original Selenium: org.openqa.selenium.*
- Relocated Selenium: io.github.qasecret.selenium_qt.*

Kotlin example (no imports shown)

```kotlin
fun main() {
  val driver = io.github.qasecret.selenium_qt.chrome.ChromeDriver()
  driver.get("https://example.com")
  println(driver.title)
  driver.quit()
}
```

Java example (no imports shown)

```java
public class Example {
  public static void main(String[] args) {
    io.github.qasecret.selenium_qt.chrome.ChromeDriver driver = new io.github.qasecret.selenium_qt.chrome.ChromeDriver();
    driver.get("https://example.com");
    System.out.println(driver.getTitle());
    driver.quit();
  }
}
```


Notes

- This library is based on Selenium 3.141.0. We maintain our own library version (1.0.0) so we can change packaging without pretending it's an upstream Selenium release.
- Packages are relocated to `io.github.qasecret.selenium_qt` (underscores are used to keep valid Java package names).
- The Shadow plugin performs relocation; check `build.gradle.kts` for the exact relocation mapping.

License

This project includes Selenium (Apache License 2.0). See the `LICENSE` file for details.

Need help?

Open an issue in the repository or ask here with an example of how you're trying to use the library and what conflicts you're seeing.
