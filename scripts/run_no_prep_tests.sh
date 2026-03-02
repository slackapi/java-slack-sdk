#!/bin/bash

# Usage: ./scripts/run_no_prep_tests.sh [goals...]
#
# Arguments: Maven phases/goals to run (default: clean test)
#
# Environment variables:
#   CI_ARGS  - Additional Maven CLI options (e.g., "--batch-mode -T 1C")
#   TRAVIS_JDK  - Set to "openjdk8" to use JDK 8 module exclusions

MVN_PHASES="${*:-clean test}"

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
    $MVN_PHASES \
    '-Dtest=test_locally.**.*Test' -Dsurefire.failIfNoSpecifiedTests=false ${CI_ARGS} \
    -DfailIfNoTests=false \
    -Dhttps.protocols=TLSv1.2 \
    --no-transfer-progress && \
    if git status --porcelain | grep .; then git --no-pager diff; exit 1; fi
elif [[ "${is_jdk_14}" != "" ]];
then
  ./mvnw \
    -pl !bolt-micronaut \
    $MVN_PHASES \
    '-Dtest=test_locally.**.*Test' -Dsurefire.failIfNoSpecifiedTests=false ${CI_ARGS} \
    -DfailIfNoTests=false \
    -Dhttps.protocols=TLSv1.2 \
    --no-transfer-progress && \
    if git status --porcelain | grep .; then git --no-pager diff; exit 1; fi
else
  ./mvnw \
    $MVN_PHASES \
    '-Dtest=test_locally.**.*Test' -Dsurefire.failIfNoSpecifiedTests=false ${CI_ARGS} \
    -DfailIfNoTests=false \
    -Dhttps.protocols=TLSv1.2 \
    --no-transfer-progress && \
    if git status --porcelain | grep .; then git --no-pager diff; exit 1; fi
fi
