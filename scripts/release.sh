#!/bin/bash

mvn deploy \
  -P release-sign-artifacts \
  -D maven.test.skip=true \
  -pl !lightning-kotlin-examples \
  -pl !lightning-quarkus-examples \
  -pl !lightning-spring-boot-examples
