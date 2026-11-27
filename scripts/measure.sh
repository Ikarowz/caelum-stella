#!/usr/bin/env bash
# Uso (na raiz do repositorio):  scripts/measure.sh M0-baseline
# Roda testes + JaCoCo + PIT (sem gate) e grava uma linha em metrics/measurements.csv
set -euo pipefail
LABEL="${1:?informe um rotulo, ex: M0-baseline}"
OPTS="-B -q -Dmaven.javadoc.skip=true -pl stella-core"
mvn $OPTS clean test jacoco:report
mvn $OPTS org.pitest:pitest-maven:mutationCoverage -DmutationThreshold=0
python3 scripts/summarize.py "$LABEL"
