# Passo a passo (siga a ordem — cerca de 1h30 no total)

Pre-requisitos: Java 17, Maven 3.9+, Git, conta no GitHub. Todos os comandos na raiz do repositorio.
Copie o conteudo desta pasta `kit/` para a raiz do seu fork **sem sobrescrever o pom.xml**.

## 0. Fork e sanidade
1. Fork de `github.com/caelum/caelum-stella` → clone → `git checkout -b main` (ou use a branch padrao).
2. `mvn -B -Dmaven.javadoc.skip=true -pl stella-core -am test`
   Se falhar por causa do Java, mande o erro ao Claude. Nao siga adiante sem isso passando.

## 1. Commit A — infraestrutura (sem testes novos)
1. Cole `scripts/pom-snippet.xml` no `stella-core/pom.xml` (instrucoes dentro do arquivo).
2. Copie `.github/`, `scripts/`, `docs/`, `metrics/`, `README.md`.
3. `git add -A && git commit -m "build: pipeline, JaCoCo, PIT e SpotBugs"`
4. `scripts/measure.sh M0-baseline`   ← **linha de base (testes originais do Stella)**
5. `git tag baseline-M0`
   Se o ajuste de pom estourar o gate (cobertura < 85% ou mutation < 70%), NAO mude os numeros:
   isso e resultado. Registre-o e, no workflow, use a meta justificada no relatorio (cap. 4).

## 2. Commit B — testes humanos (caixa preta + BDD)
1. Os arquivos `*BlackBoxTest.java`, `bdd/` e a `.feature` ja estao em `stella-core/src/test/...` — copie-os.
2. `mvn -B -Dmaven.javadoc.skip=true -pl stella-core test` (corrija so se for erro de compilacao da API).
3. Evidencia da lacuna do CNPJ alfanumerico: abra `CNPJAlfanumericoGapTest.java`, remova a linha `@Ignore(...)`,
   rode `mvn -B -Dmaven.javadoc.skip=true -pl stella-core -Dtest=CNPJAlfanumericoGapTest test`,
   salve print/saida em `docs/evidencias/` (anote se FALHOU ou PASSOU — o relatorio cap. 9 depende disso)
   e **restaure o @Ignore** (senao o gate quebra).
4. `git add -A && git commit -m "test: caixa preta (CPF/CNPJ), BDD em Gherkin e teste da lacuna do CNPJ alfanumerico"`
5. `scripts/measure.sh M1-humano`

## 3. TDD do CpfMasker (cria ~9 commits sozinho)
`scripts/tdd_cpfmasker.sh`   (a arvore git precisa estar limpa). Depois: `git log --oneline`.

## 4. Experimento com IA
1. Copie `ia-pendente/*.java` para `stella-core/src/test/java/br/com/caelum/stella/validation/`.
2. SEM editar nada: `mvn -B -Dmaven.javadoc.skip=true -pl stella-core test`
3. Preencha `docs/registro-ia.csv` (compilou? passou? tipo do erro). Falhas de compilacao ou de
   asserção sao os "erros/alucinacoes" medidos. Tire print do terminal e salve em `docs/evidencias/`.
4. `git add -A && git commit -m "test(ia): testes gerados por IA, versao bruta"`
5. Corrija o minimo necessario para a suite ficar verde (anote cada correcao no CSV).
   `git commit -am "test(ia): correcoes apos revisao humana"`
6. `scripts/measure.sh M2-ia`
7. Compare M0 (original), M1 (humano) e M2 (IA corrigida) no capitulo 10 do relatorio.

## 5. Proteger a branch main (gate de merge)
GitHub → Settings → Branches → Add rule em `main`: marcar "Require status checks to pass"
e selecionar `build-e-quality-gate`; marcar "Require a pull request before merging".
Faca ao menos 1 Pull Request real (ex.: o commit B) para ter o historico como evidencia.
Tire print do PR mostrando o check verde (e de um vermelho, se tiver).

## 6. Fechar
1. Releia `metrics/measurements.csv` — esses sao os numeros do relatorio.
2. `git tag v1.0-A3 && git push --follow-tags`
3. Abra `Relatorio_A3_Squad.docx` e troque cada `[PREENCHER]` (use Ctrl+F).
4. Envie na plataforma ate 30/11: relatorio + link do repositorio (+ slides, se ainda exigidos).
