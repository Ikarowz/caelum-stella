package br.com.caelum.stella.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import br.com.caelum.stella.ValidationMessage;

/**
 * TESTES GERADOS POR IA (Claude) para o experimento da Carta "IA a servico da qualidade".
 * Prompt usado: docs/prompt-ia.md. Nenhuma linha foi ajustada antes da primeira execucao;
 * os testes que falharem/nao compilarem devem ser registrados como erro/alucinacao no relatorio
 * (docs/registro-ia.csv) antes de qualquer correcao.
 */
public class CPFValidatorAiGeneratedTest {

    private final CPFValidator padrao = new CPFValidator();
    private final CPFValidator semFormato = new CPFValidator(false);
    private final CPFValidator comFormato = new CPFValidator(true);

    @Test
    public void aceitaCpfValidoFormatado() {
        assertTrue(comFormato.isEligible("111.444.777-35"));
    }

    @Test
    public void aceitaCpfValidoNaoFormatado() {
        assertTrue(semFormato.isEligible("52998224725"));
    }

    @Test
    public void validadorPadraoAceitaCpfFormatado() {
        assertTrue(padrao.isEligible("111.444.777-35"));
    }

    @Test
    public void rejeitaSequenciasDeDigitosIguais() {
        for (int d = 0; d <= 9; d++) {
            String cpf = String.valueOf(d).repeat(11);
            assertFalse("Deveria rejeitar " + cpf, semFormato.isEligible(cpf));
        }
    }

    @Test
    public void rejeitaCpfComUmDigitoAlterado() {
        String valido = "11144477735";
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
    public void rejeitaEspacosEmBranco() {
        assertFalse(semFormato.isEligible("           "));
    }

    @Test
    public void rejeitaCpfNulo() {
        assertFalse(semFormato.isEligible(null));
    }

    @Test
    public void assertValidNaoLancaExcecaoParaCpfValido() {
        semFormato.assertValid("11144477735");
    }

    @Test
    public void assertValidLancaExcecaoParaCpfInvalido() {
        try {
            semFormato.assertValid("11144477736");
            fail("Esperava InvalidStateException");
        } catch (InvalidStateException esperado) {
            assertFalse(esperado.getInvalidMessages().isEmpty());
        }
    }

    @Test
    public void mensagensDeErroVaziasParaCpfValido() {
        List<ValidationMessage> erros = semFormato.invalidMessagesFor("11144477735");
        assertTrue(erros.isEmpty());
    }

    @Test
    public void exatamenteUmaMensagemParaDigitoVerificadorInvalido() {
        List<ValidationMessage> erros = semFormato.invalidMessagesFor("11144477736");
        assertEquals(1, erros.size());
    }
}
