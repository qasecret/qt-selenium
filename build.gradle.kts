import com.vanniktech.maven.publish.SonatypeHost

plugins {
    kotlin("jvm") version "2.2.10"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("com.vanniktech.maven.publish") version "0.30.0"
}

group = "io.github.qasecret"
version = "1.0.0" // Our library version (based on Selenium 3.141.0)

repositories {
    mavenCentral()
    gradlePluginPortal()
    google()
}

dependencies {
    implementation("org.seleniumhq.selenium:selenium-java:3.141.0")
}


mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
    coordinates(group.toString(), "selenium-qt", version.toString()) // Fixed artifact ID

    pom {
        name = "Selenium-QT"
        description = "A repackaged version of Selenium WebDriver 3.141.0 with relocated packages to avoid version conflicts"
        inceptionYear = "2025"
        url = "https://github.com/qasecret/selenium-qt"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "qasecret"
                name = "Rabindra Biswal"
                url = "https://github.com/qasecret"
            }
        }
        scm {
            url = "https://github.com/qasecret/selenium-qt"
            connection = "scm:git:git://github.com/qasecret/selenium-qt.git"
            developerConnection = "scm:git:ssh://git@github.com/qasecret/selenium-qt.git"
        }
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}

tasks {
    test {
        useJUnitPlatform()
    }

    shadowJar {
        manifest {
            attributes(
                "Implementation-Title" to "Selenium-QT",
                "Implementation-Version" to project.version,
                "Built-By" to System.getProperty("user.name"),
                "Built-Date" to System.currentTimeMillis(),
                "Created-By" to "Gradle ${gradle.gradleVersion}",
                "Build-Jdk" to System.getProperty("java.version")
            )
        }

        archiveBaseName.set("selenium-qt")
        archiveClassifier.set("")
        mergeServiceFiles()
        relocate("org.openqa.selenium", "io.github.qasecret.selenium_qt")
        relocate("com.google.common", "io.github.qasecret.selenium_qt.libs.guava")
        relocate("org.apache.commons", "io.github.qasecret.selenium_qt.libs.commons")
        relocate("net.bytebuddy", "io.github.qasecret.selenium_qt.libs.bytebuddy")
        relocate("com.google.gson", "io.github.qasecret.selenium_qt.libs.gson")
        relocate("com.squareup.okhttp3", "io.github.qasecret.selenium_qt.libs.okhttp3")
        relocate("com.squareup.okio", "io.github.qasecret.selenium_qt.libs.okio")

        minimize()
    }

    build {
        dependsOn(shadowJar)
    }
}
