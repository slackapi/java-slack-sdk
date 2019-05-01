#!/bin/bash

cd `dirname $0`/..
cp -a json-logs/samples/api/. ../seratch-slack-types/json/web-api/
cp -a json-logs/samples/events/. ../seratch-slack-types/json/events-api/
cp -a json-logs/samples/rtm/. ../seratch-slack-types/json/rtm-api/
echo "Done!"
