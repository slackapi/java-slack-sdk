#!/bin/bash

cd `dirname $0`/..
cp -a json-logs/samples/api/. typescript-types/source/web-api/
cp -a json-logs/samples/events/. typescript-types/source/events-api/
cp -a json-logs/samples/rtm/. typescript-types/source/rtm-api/
echo "Done!"
