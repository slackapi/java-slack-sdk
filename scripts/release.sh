#!/bin/bash

is_jdk_8=$(echo "$JAVA_HOME" | grep 8.)
is_travis_jdk_8=$(echo "$TRAVIS_JDK" | grep openjdk8)
if [[ "${is_jdk_8}" != "" && "${is_travis_jdk_8}" != "" ]];
then
  echo "Please use OpenJDK 11 for releasing these libraries."
  exit 1
fi

exclusion="-pl !bolt-kotlin-examples -pl !bolt-quarkus-examples"

dir=$(dirname "$0")/..
release_version=$(sed -n 's/^[[:space:]]*<version>\([^\$]\..*\)<\/version>[[:space:]]*$/\1/p' < "${dir}"/pom.xml)

export MAVEN_OPTS="--add-opens=java.base/java.util=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED --add-opens=java.base/java.text=ALL-UNNAMED --add-opens=java.desktop/java.awt.font=ALL-UNNAMED"

if [[ "${release_version}" =~ ^.+-SNAPSHOT$ ]]; then
  echo "🚀 Releasing a SNAPSHOT version ($release_version) of the project."
  profile=production-releases
  mvn clean \
    deploy \
    -P production-releases \
    -D maven.test.skip=true \
    ${exclusion}
else
  echo "You are releasing a stable version ($release_version) of the project."
  read -r -p "This will be available publicly. Is this correct? (y/N): " confirmation

  if [[ ! "$confirmation" =~ ^[Yy]$ ]]; then
    echo "Release cancelled by user!"
    exit 0
  fi
  echo "Confirmed. Proceeding with the stable release."

  profile=production-releases
  mvn clean \
    deploy \
    -P production-releases \
    -D maven.test.skip=true \
    ${exclusion}
fi
