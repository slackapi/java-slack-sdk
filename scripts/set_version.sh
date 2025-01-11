#!/bin/bash

new_version=$1
# ------------------------------------------
if [[ "$new_version" == "" ]]; then
  echo "Give a version to set (e.g., 1.0.0)"
  exit 1
fi

# ------------------------------------------
# TODO: run as a single command
# On macOS, `brew install gnu-sed` is required to properly run this:
find . -name pom.xml | xargs gsed -i "s/<version>[0-9]\+\.[0-9]\+\.[^S]\+<\/version>/<version>${new_version}<\/version>/g"
find . -name pom.xml | xargs gsed -i "s/<version>[0-9]\+\.[0-9]\+\.[^S]\+-SNAPSHOT<\/version>/<version>${new_version}<\/version>/g"

# ------------------------------------------
if [[ "$new_version" =~ ^.+-SNAPSHOT$ ]]; then
  echo "skipped samples and docs"
else
  gsed -i "s/\"com.slack.api:bolt-jetty:[0-9]\+\.[0-9]\+\.[^S]\+\"/\"com.slack.api:bolt-jetty:${new_version}\"/g" bolt-docker-examples/echo-command-app/build.gradle
  gsed -i "s/\"com.slack.api:bolt-jetty:[0-9]\+\.[0-9]\+\.[^S]\+-SNAPSHOT\"/\"com.slack.api:bolt-jetty:${new_version}\"/g" bolt-docker-examples/echo-command-app/build.gradle
  gsed -i "s/sdkLatestVersion: [0-9]\+\.[0-9]\+\.[^S]\+/sdkLatestVersion: ${new_version}/g" docs/version-config.yml
  gsed -i "s/sdkLatestVersion: [0-9]\+\.[0-9]\+\.[^S]\+-SNAPSHOT/sdkLatestVersion: ${new_version}/g" docs/version-config.yml
fi

# ------------------------------------------
# Library Versions in Source Code
# NOTE: Class#getPackage().getImplementationVersion() returns null on AWS Lambda

echo "package com.slack.api.meta;

public final class SlackApiModelLibraryVersion {
    private SlackApiModelLibraryVersion() {
    }

    public static final String get() {
        return \"$new_version\";
    }
}
" > slack-api-model/src/main/java/com/slack/api/meta/SlackApiModelLibraryVersion.java

echo "package com.slack.api.meta;

public final class SlackApiClientLibraryVersion {
    private SlackApiClientLibraryVersion() {
    }

    public static final String get() {
        return \"$new_version\";
    }
}
" > slack-api-client/src/main/java/com/slack/api/meta/SlackApiClientLibraryVersion.java

echo "package com.slack.api.bolt.meta;

public final class BoltLibraryVersion {
    private BoltLibraryVersion() {
    }

    public static final String get() {
        return \"$new_version\";
    }
}
" > bolt/src/main/java/com/slack/api/bolt/meta/BoltLibraryVersion.java
# ------------------------------------------

git diff
