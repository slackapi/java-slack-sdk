#!/bin/bash

mvn clean \
  deploy \
  -P release-sign-artifacts \
  -D maven.test.skip=true \
  -pl !bolt-kotlin-examples \
  -pl !bolt-quarkus-examples \
  -pl !bolt-spring-boot-examples
