#!/bin/bash

is_jdk_8=`echo $JAVA_HOME | grep 8.`
is_travis_jdk_8=`echo $TRAVIS_JDK | grep openjdk8`
if [[ "${is_jdk_8}" != "" && "${is_travis_jdk_8}" != "" ]];
then
  echo "Please use OpenJDK 11 for releasing these libraries."
  exit 1
fi

exclusion="-pl !bolt-kotlin-examples -pl !bolt-quarkus-examples -pl !bolt-spring-boot-examples"

dir=`dirname $0`/..
release_version=`sed -n 's/<version>\([^\$]\..*\)<\/version>$/\1/p' < ${dir}/pom.xml`

export MAVEN_OPTS="--add-opens=java.base/java.util=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED --add-opens=java.base/java.text=ALL-UNNAMED --add-opens=java.desktop/java.awt.font=ALL-UNNAMED"

if [[ "${release_version}" =~ ^.+-SNAPSHOT$ ]]; then
  profile=snapshot-releases
  mvn clean \
    deploy \
    -P snapshot-releases \
    -D maven.test.skip=true \
    ${exclusion}
else
  profile=production-releases
  mvn clean \
    deploy \
    -P production-releases \
    -D maven.test.skip=true \
    ${exclusion} \
    nexus-staging:release
fi
