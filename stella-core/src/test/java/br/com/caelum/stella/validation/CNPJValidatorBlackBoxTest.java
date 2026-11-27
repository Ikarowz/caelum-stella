package br.com.caelum.stella.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/** Casos de caixa preta para CNPJ (escritos por humanos). */
public class CNPJValidatorBlackBoxTest {

    private final CNPJValidator semFormato = new CNPJValidator(false);
    private final CNPJValidator comFormato = new CNPJValidator(true);

    @Test
    public void cnpjValidoSemFormatacao() {
        assertTrue(semFormato.invalidMessagesFor("11222333000181").isEmpty());
        assertTrue(semFormato.invalidMessagesFor("11444777000161").isEmpty());
    }

    @Test
    public void cnpjValidoComFormatacao() {
        assertTrue(comFormato.invalidMessagesFor("11.222.333/0001-81").isEmpty());
    }

    @Test
    public void limiteDeTamanho_treze_e_quinzeDigitos() {
        assertFalse(semFormato.invalidMessagesFor("1122233300018").isEmpty());
        assertFalse(semFormato.invalidMessagesFor("112223330001811").isEmpty());
    }

    @Test
    public void primeiroDigitoVerificadorErrado() {
        assertFalse(semFormato.invalidMessagesFor("11222333000171").isEmpty());
    }

    @Test
    public void segundoDigitoVerificadorErrado() {
        assertFalse(semFormato.invalidMessagesFor("11222333000182").isEmpty());
    }

    @Test
    public void caractereNaoNumerico() {
        assertFalse(semFormato.invalidMessagesFor("1122233300018x").isEmpty());
    }

    @Test
    public void formatoIncompativelComOValidador() {
        assertFalse(comFormato.invalidMessagesFor("11222333000181").isEmpty());
        assertFalse(semFormato.invalidMessagesFor("11.222.333/0001-81").isEmpty());
    }
}
