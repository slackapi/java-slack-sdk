#!/bin/bash
is_jdk_8=`echo $JAVA_HOME | grep openjdk-8.jdk`
if [[ "${is_jdk_8}" != "" ]];
then
  ./mvnw ${MAVEN_OPTS} -pl !bolt-google-cloud-functions \
    clean \
    test-compile \
    '-Dtest=test_locally.**.*Test' test \
    -DfailIfNoTests=false \
    --no-transfer-progress && \
    if git status --porcelain | grep .; then git --no-pager diff; exit 1; fi
else
  ./mvnw ${MAVEN_OPTS} \
    clean \
    test-compile \
    '-Dtest=test_locally.**.*Test' test \
    -DfailIfNoTests=false \
    --no-transfer-progress && \
    if git status --porcelain | grep .; then git --no-pager diff; exit 1; fi
fi
