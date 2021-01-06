#!/bin/bash
is_jdk_8=`echo $JAVA_HOME | grep 8.`
is_travis_jdk_8=`echo $TRAVIS_JDK | grep openjdk8`
if [[ "${is_jdk_8}" != "" && "${is_travis_jdk_8}" != "" ]];
then
  ./mvnw ${MAVEN_OPTS} -pl !bolt-google-cloud-functions -pl !bolt-helidon \
    clean \
    test-compile \
    '-Dtest=test_locally.**.*Test' test \
    -DfailIfNoTests=false \
    -Dhttps.protocols=TLSv1.2 \
    --no-transfer-progress && \
    if git status --porcelain | grep .; then git --no-pager diff; exit 1; fi
else
  ./mvnw ${MAVEN_OPTS} \
    clean \
    test-compile \
    '-Dtest=test_locally.**.*Test' test \
    -DfailIfNoTests=false \
    -Dhttps.protocols=TLSv1.2 \
    --no-transfer-progress && \
    if git status --porcelain | grep .; then git --no-pager diff; exit 1; fi
fi
