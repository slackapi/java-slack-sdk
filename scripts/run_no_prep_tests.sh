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
  ./mvnw \
    -pl !bolt-google-cloud-functions \
    -pl !bolt-helidon \
    -pl !bolt-quarkus-examples \
    -pl !bolt-jakarta-servlet \
    -pl !bolt-jakarta-jetty \
    -pl !bolt-jakarta-socket-mode \
    -pl !bolt-http4k \
    -pl !bolt-micronaut \
    -pl !slack-jakarta-socket-mode-client \
    clean test \
    '-Dtest=test_locally.**.*Test' -Dsurefire.failIfNoSpecifiedTests=false ${CI_OPTIONS} \
    -DfailIfNoTests=false \
    -Dhttps.protocols=TLSv1.2 \
    --no-transfer-progress && \
    if git status --porcelain | grep .; then git --no-pager diff; exit 1; fi
elif [[ "${is_jdk_14}" != "" ]];
then
  ./mvnw \
    -pl !bolt-micronaut \
    clean test \
    '-Dtest=test_locally.**.*Test' -Dsurefire.failIfNoSpecifiedTests=false ${CI_OPTIONS} \
    -DfailIfNoTests=false \
    -Dhttps.protocols=TLSv1.2 \
    --no-transfer-progress && \
    if git status --porcelain | grep .; then git --no-pager diff; exit 1; fi
else
  ./mvnw \
    clean test \
    '-Dtest=test_locally.**.*Test' -Dsurefire.failIfNoSpecifiedTests=false ${CI_OPTIONS} \
    -DfailIfNoTests=false \
    -Dhttps.protocols=TLSv1.2 \
    --no-transfer-progress && \
    if git status --porcelain | grep .; then git --no-pager diff; exit 1; fi
fi
