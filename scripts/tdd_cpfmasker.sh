#!/usr/bin/env bash
# Desenvolve o modulo CpfMasker em ciclos TDD reais (red -> green -> refactor), um commit por etapa.
# Antes de cada commit "red" o script executa os testes e EXIGE que eles falhem; antes de cada
# "green"/"refactor" exige que passem. Rode na raiz do repositorio, com a arvore git limpa.
set -uo pipefail

MAIN=stella-core/src/main/java/br/com/caelum/stella/privacy
TEST=stella-core/src/test/java/br/com/caelum/stella/privacy
mkdir -p "$MAIN" "$TEST"
MVN="mvn -B -q -Dmaven.javadoc.skip=true -pl stella-core -Dtest=CpfMaskerTest -Dsurefire.failIfNoSpecifiedTests=false test"

red()   { if $MVN >/dev/null 2>&1; then echo "ERRO: o teste deveria FALHAR (red) em: $1"; exit 1; fi
          git add -A; git commit -q -m "test(red): $1"; echo "RED   ok - $1"; }
green() { if ! $MVN; then echo "ERRO: o teste deveria PASSAR (green) em: $1"; exit 1; fi
          git add -A; git commit -q -m "feat(green): $1"; echo "GREEN ok - $1"; }
refac() { if ! $MVN; then echo "ERRO: refatoracao quebrou os testes: $1"; exit 1; fi
          git add -A; git commit -q -m "refactor: $1"; echo "REFAC ok - $1"; }

# ---------- Ciclo 1: mascarar um CPF sem formatacao ----------
cat > "$TEST/CpfMaskerTest.java" <<'EOF'
package br.com.caelum.stella.privacy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CpfMaskerTest {

    @Test
    public void mascaraCpfSemFormatacao() {
        assertEquals("***.444.777-**", CpfMasker.mask("11144477735"));
    }
}
EOF
red "CpfMasker mascara CPF sem formatacao"

cat > "$MAIN/CpfMasker.java" <<'EOF'
package br.com.caelum.stella.privacy;

public final class CpfMasker {

    private CpfMasker() {
    }

    public static String mask(String cpf) {
        return "***.444.777-**";
    }
}
EOF
green "implementacao minima (fake it) para o primeiro caso"

# ---------- Ciclo 2: generalizar ----------
cat > "$TEST/CpfMaskerTest.java" <<'EOF'
package br.com.caelum.stella.privacy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CpfMaskerTest {

    @Test
    public void mascaraCpfSemFormatacao() {
        assertEquals("***.444.777-**", CpfMasker.mask("11144477735"));
    }

    @Test
    public void mascaraOutroCpfSemFormatacao() {
        assertEquals("***.982.247-**", CpfMasker.mask("52998224725"));
    }
}
EOF
red "CpfMasker deve mascarar qualquer CPF, nao so o primeiro"

cat > "$MAIN/CpfMasker.java" <<'EOF'
package br.com.caelum.stella.privacy;

public final class CpfMasker {

    private CpfMasker() {
    }

    public static String mask(String cpf) {
        return "***." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-**";
    }
}
EOF
green "generaliza mascara usando substring"

# ---------- Ciclo 3: aceitar CPF formatado ----------
cat > "$TEST/CpfMaskerTest.java" <<'EOF'
package br.com.caelum.stella.privacy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CpfMaskerTest {

    @Test
    public void mascaraCpfSemFormatacao() {
        assertEquals("***.444.777-**", CpfMasker.mask("11144477735"));
    }

    @Test
    public void mascaraOutroCpfSemFormatacao() {
        assertEquals("***.982.247-**", CpfMasker.mask("52998224725"));
    }

    @Test
    public void mascaraCpfJaFormatado() {
        assertEquals("***.444.777-**", CpfMasker.mask("111.444.777-35"));
    }
}
EOF
red "CpfMasker aceita CPF ja formatado"

cat > "$MAIN/CpfMasker.java" <<'EOF'
package br.com.caelum.stella.privacy;

public final class CpfMasker {

    private CpfMasker() {
    }

    public static String mask(String cpf) {
        String digitos = cpf.replaceAll("\\D", "");
        return "***." + digitos.substring(3, 6) + "." + digitos.substring(6, 9) + "-**";
    }
}
EOF
green "remove formatacao antes de mascarar"

# ---------- Ciclo 4: entradas invalidas ----------
cat > "$TEST/CpfMaskerTest.java" <<'EOF'
package br.com.caelum.stella.privacy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CpfMaskerTest {

    @Test
    public void mascaraCpfSemFormatacao() {
        assertEquals("***.444.777-**", CpfMasker.mask("11144477735"));
    }

    @Test
    public void mascaraOutroCpfSemFormatacao() {
        assertEquals("***.982.247-**", CpfMasker.mask("52998224725"));
    }

    @Test
    public void mascaraCpfJaFormatado() {
        assertEquals("***.444.777-**", CpfMasker.mask("111.444.777-35"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejeitaNulo() {
        CpfMasker.mask(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejeitaMenosDeOnzeDigitos() {
        CpfMasker.mask("1114447773");
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejeitaMaisDeOnzeDigitos() {
        CpfMasker.mask("111444777355");
    }
}
EOF
red "CpfMasker rejeita nulo e tamanho invalido"

cat > "$MAIN/CpfMasker.java" <<'EOF'
package br.com.caelum.stella.privacy;

public final class CpfMasker {

    private CpfMasker() {
    }

    public static String mask(String cpf) {
        if (cpf == null) {
            throw new IllegalArgumentException("CPF nao pode ser nulo");
        }
        String digitos = cpf.replaceAll("\\D", "");
        if (digitos.length() != 11) {
            throw new IllegalArgumentException("CPF deve ter 11 digitos");
        }
        return "***." + digitos.substring(3, 6) + "." + digitos.substring(6, 9) + "-**";
    }
}
EOF
green "valida entrada nula e tamanho"

# ---------- Refactor ----------
cat > "$MAIN/CpfMasker.java" <<'EOF'
package br.com.caelum.stella.privacy;

/** Oculta os 3 primeiros e os 2 ultimos digitos de um CPF (LGPD), ex.: ***.444.777-**. */
public final class CpfMasker {

    private static final int TAMANHO_CPF = 11;

    private CpfMasker() {
    }

    public static String mask(String cpf) {
        String digitos = somenteDigitos(cpf);
        return "***." + digitos.substring(3, 6) + "." + digitos.substring(6, 9) + "-**";
    }

    private static String somenteDigitos(String cpf) {
        if (cpf == null) {
            throw new IllegalArgumentException("CPF nao pode ser nulo");
        }
        String digitos = cpf.replaceAll("\\D", "");
        if (digitos.length() != TAMANHO_CPF) {
            throw new IllegalArgumentException("CPF deve ter " + TAMANHO_CPF + " digitos");
        }
        return digitos;
    }
}
EOF
refac "extrai somenteDigitos e constante TAMANHO_CPF"
echo "Ciclo TDD concluido. Veja: git log --oneline"
