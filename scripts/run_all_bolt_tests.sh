#!/bin/bash
is_jdk_8=`echo $JAVA_HOME | grep 8.`
is_travis_jdk_8=`echo $TRAVIS_JDK | grep openjdk8`
if [[ "${is_jdk_8}" != "" && "${is_travis_jdk_8}" != "" ]];
then
  echo "Please use OpenJDK 11 for running these tests."
  exit 1
fi

./mvnw install -Dmaven.test.skip=true && \
./mvnw test -pl bolt \
  -pl slack-app-backend \
  -pl bolt-servlet \
  -pl bolt-jakarta-servlet \
  -pl bolt-socket-mode \
  -pl bolt-jakarta-socket-mode \
  -pl bolt-aws-lambda \
  -pl bolt-micronaut \
  -pl bolt-helidon \
  -pl bolt-google-cloud-functions \
  -pl !bolt-quarkus-examples
