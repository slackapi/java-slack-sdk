#!/bin/bash

cd `dirname $0`/..
cp -a json-logs/samples/api/. typescript-types/source/web-api/
echo "Done!"
