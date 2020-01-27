#!/bin/bash

new_version=$1
if [[ "$new_version" == "" ]]; then
  echo "Give a version to set (e.g., 1.0.0)"
  exit 1
fi
# TODO: run as a single command
find . -name pom.xml | xargs gsed -i "s/<version>[0-9]\+\.[0-9]\+\.[0-9]\+<\/version>/<version>${new_version}<\/version>/g"
find . -name pom.xml | xargs gsed -i "s/<version>[0-9]\+\.[0-9]\+\.[0-9]\+-SNAPSHOT<\/version>/<version>${new_version}<\/version>/g"

gsed -i "s/\"com.slack:lightning-jetty:[0-9]\+\.[0-9]\+\.[0-9]\+\"/\"com.slack:lightning-jetty:${new_version}\"/g" lightning-docker-examples/echo-command-app/build.gradle
gsed -i "s/\"com.slack:lightning-jetty:[0-9]\+\.[0-9]\+\.[0-9]\+-SNAPSHOT\"/\"com.slack:lightning-jetty:${new_version}\"/g" lightning-docker-examples/echo-command-app/build.gradle

gsed -i "s/sdkLatestVersion: [0-9]\+\.[0-9]\+\.[0-9]\+/sdkLatestVersion: ${new_version}/g" docs/_config.yml
gsed -i "s/sdkLatestVersion: [0-9]\+\.[0-9]\+\.[0-9]\+-SNAPSHOT/sdkLatestVersion: ${new_version}/g" docs/_config.yml

git diff
