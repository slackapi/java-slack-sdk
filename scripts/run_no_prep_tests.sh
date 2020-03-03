#!/bin/bash
./mvnw clean \
  test-compile \
  '-Dtest=test_locally.**.*Test' test \
  -DfailIfNoTests=false \
  --no-transfer-progress
