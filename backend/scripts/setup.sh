#!/bin/bash
set -ex

BASEDIR=$(dirname $(dirname $(realpath $0)))
cd $BASEDIR

# https://www.oneai.com/ 에서 발급받은 API KEY를 입력하세요
API_KEY="YOUR_API_KEY_HERE"

DB_USER=""
DB_PASSWD=""

npm i -g @nestjs/cli
yarn install
yarn prisma generate

rm -f .env
echo "DATABASE_URL=\"mongodb+srv://$DB_USER:$DB_PASSWD@2022-cabstone.sqajriy.mongodb.net/2022-cabstone?retryWrites=true&w=majority\"" > .env
echo "ONE_AI_API_KEY=\"$API_KEY\"" >> .env