#!/bin/bash
set -ex

BASEDIR=$(dirname $(dirname $(realpath $0)))
cd $BASEDIR

# https://www.oneai.com/ 에서 발급받은 API KEY를 입력하세요
API_KEY="YOUR_API_KEY_HERE"

DB_HOST=localhost
DB_PORT=27017

npm i -g @nestjs/cli
yarn install
yarn prisma generate

rm -f .env
echo "DATABASE_URL=\"mongodb://mongo:1234@$DB_HOST:$DB_PORT/cabstone\"" > .env
echo "ONE_AI_API_KEY=\"$API_KEY\"" > .env