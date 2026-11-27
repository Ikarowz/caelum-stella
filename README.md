# Plano de qualidade sobre o Caelum Stella (stella-core)

Fork do [caelum-stella](https://github.com/caelum/caelum-stella) — biblioteca Java de validadores e formatadores
de documentos brasileiros. Projeto da disciplina Gestão e Qualidade de Software 2026/2 (A3).

**Squad:** Ikaro Camargo · Vitor Enzo Rocha · Eric Próspero · Mauricio Coutinho

## Escopo
Módulo `stella-core`, classes `CPFValidator` e `CNPJValidator` (módulo crítico: um falso positivo deixa passar
cadastro com documento inexistente). Módulo novo `CpfMasker` (máscara de CPF, LGPD), feito com TDD.

## Como rodar
```bash
mvn -B -Dmaven.javadoc.skip=true -pl stella-core -am verify          # build + testes + gate JaCoCo
mvn -B -Dmaven.javadoc.skip=true -pl stella-core spotbugs:check      # análise estática
mvn -B -Dmaven.javadoc.skip=true -pl stella-core org.pitest:pitest-maven:mutationCoverage
scripts/measure.sh <rotulo>                                          # registra métricas em metrics/measurements.csv
```

## Estratégia de branches: trunk-based
Time de 4 pessoas, módulo pequeno e entregas curtas: branches de vida curta (`feat/...`, `test/...`) saem da `main`,
voltam por Pull Request em no máximo 1 dia, e só entram com o check `build-e-quality-gate` verde.
Git Flow foi descartado: não há várias versões em manutenção paralela que justifiquem `develop`/`release`.
Releases são tags (`baseline-M0`, `v1.0-A3`).

## Quality gate (`.github/workflows/ci.yml`)
| Gate | Critério |
|---|---|
| Testes | Todos passam (JUnit + Cucumber) |
| JaCoCo | ≥ 85% de linhas em `CPFValidator` e `CNPJValidator` |
| PIT | Mutation score ≥ 70% nessas classes |
| SpotBugs | Nenhum alerta de severidade alta |

## Mapa de evidências
| Entregável | Onde está |
|---|---|
| Plano de testes, caixa preta | `*BlackBoxTest.java` |
| TDD | `git log` (commits `test(red)`, `feat(green)`, `refactor`) |
| BDD | `stella-core/src/test/resources/features/` |
| Pipeline | `.github/workflows/ci.yml` |
| Métricas | `metrics/measurements.csv` |
| Experimento de IA | `docs/prompt-ia.md`, `docs/registro-ia.csv`, `*AiGeneratedTest.java` |
