#!/bin/bash
./mvnw versions:display-dependency-updates | grep -v checking
