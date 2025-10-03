plugins {
    kotlin("jvm") version "2.2.10"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "io.github.qasecret"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()
    google()
}

dependencies {
    implementation("org.seleniumhq.selenium:selenium-java:3.141.0")
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
                "Implementation-Title" to "Selenium QT",
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

        relocate("org.openqa.selenium", "io.github.qasecret.selenium")
        relocate("com.google.common", "io.github.qasecret.selenium.libs.guava")
        relocate("org.apache.commons", "io.github.qasecret.selenium.libs.commons")
        relocate("net.bytebuddy", "io.github.qasecret.selenium.libs.bytebuddy")
        relocate("com.google.gson", "io.github.qasecret.selenium.libs.gson")
        relocate("com.squareup.okhttp3", "io.github.qasecret.selenium.libs.okhttp3")
        relocate("com.squareup.okio", "io.github.qasecret.selenium.libs.okio")

        minimize()
    }

    build {
        dependsOn(shadowJar)
    }
}
