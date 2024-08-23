#!/bin/bash
./mvnw test -pl slack-api-model \
  -pl slack-api-client \
  -pl slack-jakarta-socket-mode-client \
  -pl slack-api-model-kotlin-extension \
  -pl slack-api-client-kotlin-extension
