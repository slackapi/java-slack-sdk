---
layout: default
title: "Installation"
lang: en
---

# API Client Installation

The first step to using the Slack API client is installing the **slack-api-client** module. This guide shows you how to set up using [Maven](https://maven.apache.org/), [Gradle](https://gradle.org/), and by building from source on your own. 

## Prerequisites

[Installing OpenJDK 8 or higher LTS version](https://openjdk.java.net/install/) beforehand is required. As long as your using a supported JDK version, this SDK should be working with any OpenJDK distributions.

---

## Maven

Let's start with how to installation with Maven. As **slack-api-client** is a library dependency, there is no requirement of Maven versions.

### pom.xml

Save `pom.xml` with the following XML definition in the root directory of your Java project. As you see, this is a commonplace Maven project. No specific settings would be needed to load the project on your favorite IDE.


```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.example</groupId>
  <artifactId>awesome-slack-app</artifactId>
  <version>0.1-SNAPSHOT</version>
  <packaging>jar</packaging>
  <dependencies>
    <dependency>
      <groupId>com.slack.api</groupId>
      <artifactId>slack-api-client</artifactId>
      <version>{{ site.sdkLatestVersion }}</version>
    </dependency>
  </dependencies>
</project>
```

### src/main/java/Example.java

Create a new Java class named **Example** that has a main method to run. I know the following code is not actually useful. For now,  place it just for verifying the build settings are valid.

```java
import com.slack.api.Slack;
import com.slack.api.methods.response.api.ApiTestResponse;

public class Example {
  public static void main(String[] args) throws Exception {
    Slack slack = Slack.getInstance();
    ApiTestResponse response = slack.methods().apiTest(r -> r.foo("bar"));
    System.out.println(response);
  }
}
```

Run the **Example.main(String[])** from your IDE or by running the following command.

```bash
mvn compile exec:java \
  -Dexec.cleanupDaemonThreads=false \
  -Dexec.mainClass="Example"
```

If you see the following stdout, your installation has succeeded!

```
ApiTestResponse(ok=true, args=ApiTestResponse.Args(foo=bar, error=null), warning=null, error=null, needed=null, provided=null)
```

In summary, the things you've done here are:

* ✅ JDK 8 or higher installed (if not, run `brew install openjdk@11` for macOS / visit [OpenJDK website](https://openjdk.java.net/install/) for others)
* ✅ Maven installed (if not, run `brew install maven` for macOS / visit [their website](https://maven.apache.org/) for others)
* ✅ `pom.xml` having **slack-api-client** as a dependency
* ✅ `src/main/java/Example.java` with the main method

---

## Gradle

Do you prefer using Gradle? Let's try installing the library with Gradle. The steps are quite similar to Maven, but there are some differences.

### build.gradle

Place `build.gradle` in the root directory of your Java project. We don't have any requirements for Gradle versions.

```groovy
plugins {
  id("application")
}
repositories {
  mavenCentral()
}
dependencies {
  implementation("com.slack.api:slack-api-client:{{ site.sdkLatestVersion }}")
}
application {
  mainClassName = "Example"
}
```

As with the Maven section, let's create a class named **Example** with the main method. Then, run it from your IDE or hit `gradle run` on your terminal. You'll see the same stdout.

In summary, the things you've done here are:

* ✅ JDK 8 or higher installed (if not, run `brew install openjdk@11` for macOS / visit [OpenJDK website](https://openjdk.java.net/install/) for others)
* ✅ Gradle installed (if not, run `brew install gradle` for macOS / visit [their website](https://gradle.org/) for others)
* ✅ `build.gradle` having **slack-api-client** as a dependency
* ✅ `src/main/java/Example.java` with the main method

---

## Gradle for Kotlin

In this guide, we sometimes use Kotlin code examples for simplicity. To try those examples, set up a Kotlin project with Gradle as below.

### build.gradle

The build settings are almost the same as above except for some Kotlin-specific parts.

```groovy
plugins {
  id("org.jetbrains.kotlin.jvm") version "{{ site.kotlinVersion }}" // use the latest Kotlin version
  id("application")
}
repositories {
  mavenCentral()
}
dependencies {
  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("com.slack.api:slack-api-client:{{ site.sdkLatestVersion }}")
}
application {
  mainClassName = "ExampleKt" // add "Kt" suffix for main function source file
}
```

### src/main/kotlin/Example.kt

As you see, the code using **slack-api-client** in Kotlin is much more concise than Java. Run the following code from your IDE or by `gradle run`.

```kotlin
import com.slack.api.Slack

fun main() {
  val slack = Slack.getInstance()
  val response = slack.methods().apiTest { it.foo("bar") }
  println(response)
}
```

**NOTE**: A name of a source file in Kotlin needs to end with **.kt**, not **.java**.

In summary, the things you've done here are:

* ✅ JDK 8 or higher installed (if not, run `brew install openjdk@11` for macOS / visit [OpenJDK website](https://openjdk.java.net/install/) for others)
* ✅ Gradle installed (if not, run `brew install gradle` for macOS / visit [their website](https://gradle.org/) for others)
* ✅ `build.gradle` having valid Kotlin language settings and **slack-api-client** as a dependency
* ✅ `src/main/kotlin/Example.kt` with the main method

---

## Build from Source

You may want to build the latest revision on your own. In the case of building from source, go with the following steps.

```bash
git clone git@github.com:slackapi/java-slack-sdk.git
cd java-slack-sdk
mvn install -Dmaven.test.skip=true
```

Just by doing above, all the SDK modules will be available under `$HOME/.m2/repository`, so that now you can use them on the machine. If you use Gradle, make sure to add `mavenLocal()` to `repositories` in your `build.gradle`.

```groovy
repositories {
  mavenLocal()
}
```

That's all set! If you get stuck, we’re here to help. The following are the best ways to get assistance working through your issue:

* Use our [GitHub Issue Tracker](http://github.com/slackapi/java-slack-sdk/issues) for reporting bugs or requesting features
* Visit the [Slack Developer Community](https://slackcommunity.com/) for getting help using **Slack SDK for Java** or just generally bond with your fellow Slack developers.