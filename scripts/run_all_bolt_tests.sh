#!/bin/bash
./mvnw install -Dmaven.test.skip=true && \
./mvnw test -pl bolt -pl slack-app-backend -pl bolt-servlet -pl bolt-aws-lambda -pl bolt-micronaut -pl bolt-helidon