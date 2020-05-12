#!/bin/bash
./mvnw ${MAVEN_OPTS} \
  clean \
  test-compile \
  '-Dtest=test_locally.**.*Test' test \
  -DfailIfNoTests=false \
  --no-transfer-progress && \
  if git status --porcelain | grep .; then git diff --no-pager; exit 1; fi
