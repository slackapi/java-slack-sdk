#!/bin/bash

mvn deploy \
  -P release-sign-artifacts \
  -D maven.test.skip=true \
  -pl !jslack-lightning-kotlin-examples !jslack-lightning-quarkus-examples !jslack-lightning-spring-boot-examples
