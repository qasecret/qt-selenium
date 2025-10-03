import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("java")
    id("com.vanniktech.maven.publish") version "0.30.0"
}

group = "io.github.qasecret"
version = "1.0.3"

repositories {
    mavenCentral()
}

dependencies {
    implementation("junit:junit:4.13.1")
    implementation("org.hamcrest:hamcrest-core:1.3")
    implementation("org.hamcrest:hamcrest-library:1.3")
    implementation("com.google.guava:guava-parent:31.0.1-jre")
    implementation("net.sourceforge.htmlunit:htmlunit:2.30")
    implementation("net.sourceforge.htmlunit:neko-htmlunit:2.70.0")
    implementation("net.sourceforge.htmlunit:htmlunit-core-js:2.28")
    implementation("org.seleniumhq.selenium:htmlunit-driver:2.30.0")
    implementation("javax.servlet:javax.servlet-api:3.1.0")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("org.apache.httpcomponents:httpcore:4.4.9")
    implementation("commons-logging:commons-logging:1.2")
    implementation("commons-io:commons-io:2.7")
    implementation("commons-codec:commons-codec:1.13")
    implementation("org.apache.commons:commons-lang3:3.7")
    implementation("org.apache.httpcomponents:httpmime:4.5.5")
    implementation("xerces:xercesImpl:2.12.2")
    implementation("xalan:xalan:2.7.3")
    implementation("org.eclipse.jetty.websocket:websocket-api:9.4.7.v20170914")
    implementation("org.eclipse.jetty.websocket:websocket-client:9.4.7.v20170914")
    implementation("org.eclipse.jetty.websocket:websocket-common:9.4.7.v20170914")
    implementation("io.netty:netty-all:4.1.86.Final")
    implementation("xml-apis:xml-apis:1.4.01")
    implementation("org.mockito:mockito-core:2.18.0")
    implementation("org.objenesis:objenesis:2.6")
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("org.slf4j:slf4j-jdk14:1.7.25")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("org.testng:testng:7.7.0")
    implementation("net.bytebuddy:byte-buddy:1.8.15")
    implementation("com.github.javaparser:javaparser-core:3.6.14")
    implementation("com.squareup.okhttp3:okhttp:3.10.0")
    implementation("com.squareup.okio:okio:1.14.1")
    implementation("com.google.auto:auto-common:1.2")
    implementation("com.google.auto.service:auto-service:1.0-rc4")
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
    coordinates(group.toString(), "qt-selenium", version.toString()) // Fixed artifact ID

    pom {
        name = "qt-selenium"
        description =
            "A repackaged version of Selenium WebDriver 3.141.0 with relocated packages to avoid version conflicts"
        inceptionYear = "2025"
        url = "https://github.com/qasecret/qt-selenium"
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
            url = "https://github.com/qasecret/qt-selenium"
            connection = "scm:git:git://github.com/qasecret/qt-selenium.git"
            developerConnection = "scm:git:ssh://git@github.com/qasecret/qt-selenium.git"
        }
    }
}


tasks.test {
    useJUnitPlatform()
}