#!/bin/bash
set -ex

echo "DATABASE_URL=\"postgresql://postgres:5432@localhost:5432/cabstone?schema=public\"" > backend/.env