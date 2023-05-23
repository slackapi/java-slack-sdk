#!/bin/bash
./mvnw -pl !bolt-quarkus-examples versions:display-dependency-updates | \
  grep -v checking | \
  grep -v org.mockito:mockito-core | \
  grep -v org.eclipse.jetty | \
  grep -v javax.servlet:javax.servlet-api | \
  grep -v jakarta.servlet:jakarta.servlet-api | \
  grep -v ch.qos.logback:logback-classic | \
  grep -v org.glassfish.tyrus.bundles:tyrus-standalone-client | \
  grep -v org.slf4j: | \
  grep -v io.helidon.
