#!/bin/bash

cd `dirname $0`/../typescript-types
npm install
webpack
npm publish
