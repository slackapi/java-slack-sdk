#!/bin/bash
is_jdk_8=`echo $JAVA_HOME | grep 8.`
is_travis_jdk_8=`echo $TRAVIS_JDK | grep openjdk8`
if [[ "${is_jdk_8}" != "" && "${is_travis_jdk_8}" != "" ]];
then
  echo "Please use OpenJDK 11."
  exit 1
fi

./mvnw install -Dmaven.test.skip=true
