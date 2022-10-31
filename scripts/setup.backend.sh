#!/bin/bash
set -ex

npm i -g @nestjs/cli

echo "DATABASE_URL=\"postgresql://postgres:5432@localhost:5432/cabstone?schema=public\"" > backend/.env