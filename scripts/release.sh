#!/bin/bash

mvn deploy \
  -P release-sign-artifacts \
  -D maven.test.skip=true \
  -pl !jslack-lightning-kotlin-examples \
  -pl !jslack-lightning-quarkus-examples \
  -pl !jslack-lightning-spring-boot-examples
