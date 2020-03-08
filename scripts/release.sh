#!/bin/bash

exclusion="-pl !bolt-kotlin-examples -pl !bolt-quarkus-examples -pl !bolt-spring-boot-examples"

dir=`dirname $0`/..
release_version=`sed -n 's/<version>\([^\$]\..*\)<\/version>$/\1/p' < ${dir}/pom.xml`

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
