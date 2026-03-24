#!/bin/bash
is_jdk_8=`echo $JAVA_HOME | grep 8.`
if [[ "${is_jdk_8}" != "" ]];
then
  echo "Please use OpenJDK 11."
  exit 1
fi

./mvnw install -Dmaven.test.skip=true
