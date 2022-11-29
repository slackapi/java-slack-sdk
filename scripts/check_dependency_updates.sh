#!/bin/bash
./mvnw -pl !bolt-quarkus-examples versions:display-dependency-updates | grep -v checking
