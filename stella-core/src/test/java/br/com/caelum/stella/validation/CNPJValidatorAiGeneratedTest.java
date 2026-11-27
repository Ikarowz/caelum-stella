package br.com.caelum.stella.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/** TESTES GERADOS POR IA (Claude) - ver docs/prompt-ia.md. */
public class CNPJValidatorAiGeneratedTest {

    private final CNPJValidator semFormato = new CNPJValidator(false);
    private final CNPJValidator comFormato = new CNPJValidator(true);

    @Test
    public void aceitaCnpjValidoFormatado() {
        assertTrue(comFormato.isEligible("11.222.333/0001-81"));
    }

    @Test
    public void aceitaCnpjValidoNaoFormatado() {
        assertTrue(semFormato.isEligible("11444777000161"));
    }

    @Test
    public void rejeitaSequenciasDeDigitosIguais() {
        for (int d = 0; d <= 9; d++) {
            String cnpj = String.valueOf(d).repeat(14);
            // CORRECAO: isEligible so confere o formato; a regra de repetidos esta em invalidMessagesFor.
            assertFalse("Deveria rejeitar " + cnpj, semFormato.invalidMessagesFor(cnpj).isEmpty());
        }
    }

    @Test
    public void rejeitaCnpjComUmDigitoAlterado() {
        String valido = "11222333000181";
        for (int i = 0; i < valido.length(); i++) {
            char original = valido.charAt(i);
            char trocado = original == '9' ? '0' : (char) (original + 1);
            String alterado = valido.substring(0, i) + trocado + valido.substring(i + 1);
            // CORRECAO: o digito verificador so e conferido por invalidMessagesFor/assertValid.
            assertFalse("Deveria rejeitar " + alterado, semFormato.invalidMessagesFor(alterado).isEmpty());
        }
    }

    @Test
    public void rejeitaStringVazia() {
        assertFalse(semFormato.isEligible(""));
    }

    @Test
    public void rejeitaCnpjNulo() {
        assertFalse(semFormato.isEligible(null));
    }

    @Test
    public void rejeitaLetrasNoMeioDoNumero() {
        // CORRECAO: desde 31/07/2026 letras sao validas no CNPJ (formato alfanumerico). O que
        // invalida este numero e o digito verificador, nao a letra; isEligible aceita letras.
        assertFalse(semFormato.invalidMessagesFor("11222A33000181").isEmpty());
    }
}
