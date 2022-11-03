#!/bin/bash
set -ex

# https://www.oneai.com/ 에서 발급받은 API KEY를 입력하세요
API_KEY="YOUR_API_KEY_HERE"

npm i -g @nestjs/cli
cd backend

yarn install

echo "DATABASE_URL=\"postgresql://postgres:5432@localhost:5432/cabstone?schema=public\"" > .env
echo "ONE_AI_API_KEY=\"$API_KEY\"" >> .env