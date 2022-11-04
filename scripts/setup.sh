#!/bin/bash
set -ex

BASEDIR=$(dirname $(dirname $(realpath $0)))
cd $BASEDIR

backend/scripts/setup.sh