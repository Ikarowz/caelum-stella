package br.com.caelum.stella.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Casos de caixa preta (escritos por humanos).
 * Particoes: tamanho (<11, =11, >11), digitos verificadores (DV1 ok/errado, DV2 ok/errado),
 * formato (formatado/nao formatado), caracteres (numericos/nao numericos).
 * Valor limite: 10, 11 e 12 digitos.
 */
public class CPFValidatorBlackBoxTest {

    private final CPFValidator semFormato = new CPFValidator(false);
    private final CPFValidator comFormato = new CPFValidator(true);

    @Test
    public void cpfValidoSemFormatacao() {
        assertTrue(semFormato.invalidMessagesFor("11144477735").isEmpty());
        assertTrue(semFormato.invalidMessagesFor("52998224725").isEmpty());
    }

    @Test
    public void cpfValidoComFormatacao() {
        assertTrue(comFormato.invalidMessagesFor("111.444.777-35").isEmpty());
    }

    @Test
    public void limiteInferiorDeTamanho_dezDigitos() {
        assertFalse(semFormato.invalidMessagesFor("1114447773").isEmpty());
    }

    @Test
    public void limiteSuperiorDeTamanho_dozeDigitos() {
        assertFalse(semFormato.invalidMessagesFor("111444777355").isEmpty());
    }

    @Test
    public void primeiroDigitoVerificadorErrado() {
        assertFalse(semFormato.invalidMessagesFor("11144477745").isEmpty());
    }

    @Test
    public void segundoDigitoVerificadorErrado() {
        assertFalse(semFormato.invalidMessagesFor("11144477736").isEmpty());
    }

    @Test
    public void caractereNaoNumerico() {
        assertFalse(semFormato.invalidMessagesFor("1114447773a").isEmpty());
    }

    @Test
    public void formatoIncompativelComOValidador() {
        assertFalse(comFormato.invalidMessagesFor("11144477735").isEmpty());
        assertFalse(semFormato.invalidMessagesFor("111.444.777-35").isEmpty());
    }

    @Test
    public void todosOsDigitosIguais() {
        assertFalse(semFormato.invalidMessagesFor("11111111111").isEmpty());
    }
}
