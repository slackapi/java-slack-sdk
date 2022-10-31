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
is_travis_jdk_8=`echo $TRAVIS_JDK | grep openjdk8`
if [[ "${is_jdk_8}" != "" && "${is_travis_jdk_8}" != "" ]];
then
  ./mvnw ${MAVEN_OPTS} \
    -pl !bolt-google-cloud-functions \
    -pl !bolt-helidon \
    -pl !bolt-quarkus-examples \
    -pl !bolt-jakarta-servlet \
    -pl !bolt-jakarta-jetty \
    clean \
    test-compile \
    '-Dtest=test_locally.**.*Test' test ${CI_OPTIONS}\
    -DfailIfNoTests=false \
    -Dhttps.protocols=TLSv1.2 \
    --no-transfer-progress && \
    if git status --porcelain | grep .; then git --no-pager diff; exit 1; fi
else
  ./mvnw ${MAVEN_OPTS} \
    clean \
    test-compile \
    '-Dtest=test_locally.**.*Test' test ${CI_OPTIONS} \
    -DfailIfNoTests=false \
    -Dhttps.protocols=TLSv1.2 \
    --no-transfer-progress && \
    if git status --porcelain | grep .; then git --no-pager diff; exit 1; fi
fi
