#!/usr/bin/env bash
set -euo pipefail

cd -- "$(dirname -- "${BASH_SOURCE[0]}")"

printf 'Compiling...\n'
javac Main.java
printf 'Starting Memory Game...\n'
exec java -Dsun.java2d.xrender=false Main "$@"
