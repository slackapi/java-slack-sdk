#!/bin/bash

flags()
{
    while test $# -gt 0
    do
        case "$1" in
        -ci)
            CI_OPTIONS="--batch-mode -T 1C"
            ;;
        *) usage;;
        esac
        shift
    done
}
flags "$@"

is_jdk_8=`echo $JAVA_HOME | grep 8.`
is_jdk_14=`echo $JAVA_HOME | grep 14.`

is_travis_jdk_8=`echo $TRAVIS_JDK | grep openjdk8`
if [[ "${is_jdk_8}" != "" && "${is_travis_jdk_8}" != "" ]];
then
  ./mvnw ${MAVEN_OPTS} \
    -pl !bolt-google-cloud-functions \
    -pl !bolt-helidon \
    -pl !bolt-quarkus-examples \
    -pl !bolt-jakarta-servlet \
    -pl !bolt-jakarta-jetty \
    -pl !bolt-micronaut \
    clean \
    test-compile \
    '-Dtest=test_locally.**.*Test' -Dsurefire.failIfNoSpecifiedTests=false test ${CI_OPTIONS} \
    -DfailIfNoTests=false \
    -Dhttps.protocols=TLSv1.2 \
    --no-transfer-progress && \
    if git status --porcelain | grep .; then git --no-pager diff; exit 1; fi
elif [[ "${is_jdk_14}" != "" ]];
then
  ./mvnw ${MAVEN_OPTS} \
    -pl !bolt-micronaut \
    clean \
    test-compile \
    '-Dtest=test_locally.**.*Test' -Dsurefire.failIfNoSpecifiedTests=false test ${CI_OPTIONS} \
    -DfailIfNoTests=false \
    -Dhttps.protocols=TLSv1.2 \
    --no-transfer-progress && \
    if git status --porcelain | grep .; then git --no-pager diff; exit 1; fi
else
    # Until we resolve https://github.com/slackapi/java-slack-sdk/issues/892, most of the tests can fail with JDK 17+
    # That's why we install jar files locally and run only the tests that work with JDK 17+
    ./mvnw ${MAVEN_OPTS} install \
      '-Dmaven.test.skip=true' \
      -pl !bolt-quarkus-examples \
    && \
    ./mvnw ${MAVEN_OPTS} \
      test -pl bolt-micronaut \
      '-Dtest=test_locally.**.*Test' -Dsurefire.failIfNoSpecifiedTests=false test ${CI_OPTIONS} \
      -DfailIfNoTests=false \
      -Dhttps.protocols=TLSv1.2 \
      --no-transfer-progress && \
      if git status --porcelain | grep .; then git --no-pager diff; exit 1; fi
fi
