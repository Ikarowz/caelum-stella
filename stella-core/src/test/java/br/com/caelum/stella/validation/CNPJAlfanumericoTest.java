package br.com.caelum.stella.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Conformidade com o CNPJ alfanumerico (IN RFB 2.229/2024, em vigor desde 31/07/2026).
 * Exemplo oficial da Receita Federal: 12.ABC.345/01DE-35.
 */
public class CNPJAlfanumericoTest {

    @Test
    public void aceitaCnpjAlfanumericoOficialDaReceita() {
        assertTrue(new CNPJValidator(false).invalidMessagesFor("12ABC34501DE35").isEmpty());
        assertTrue(new CNPJValidator(true).invalidMessagesFor("12.ABC.345/01DE-35").isEmpty());
    }

    @Test
    public void rejeitaCnpjAlfanumericoComDigitoVerificadorErrado() {
        assertFalse(new CNPJValidator(false).invalidMessagesFor("12ABC34501DE36").isEmpty());
    }
}
