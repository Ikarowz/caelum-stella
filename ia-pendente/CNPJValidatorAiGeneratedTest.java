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
            assertFalse("Deveria rejeitar " + cnpj, semFormato.isEligible(cnpj));
        }
    }

    @Test
    public void rejeitaCnpjComUmDigitoAlterado() {
        String valido = "11222333000181";
        for (int i = 0; i < valido.length(); i++) {
            char original = valido.charAt(i);
            char trocado = original == '9' ? '0' : (char) (original + 1);
            String alterado = valido.substring(0, i) + trocado + valido.substring(i + 1);
            assertFalse("Deveria rejeitar " + alterado, semFormato.isEligible(alterado));
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
        assertFalse(semFormato.isEligible("11222A33000181"));
    }
}
