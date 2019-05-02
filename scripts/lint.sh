#!/bin/bash
set -euo pipefail

LINTABLE=(
    ./frontend
    ./services/auth-service
)

for svc in "${LINTABLE[@]}"; do
    echo " ***** Linting $svc *****"

    (cd $svc && make lint)
done

echo " ****** Success - all packages linted ******"
