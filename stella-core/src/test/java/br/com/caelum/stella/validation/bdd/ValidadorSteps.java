package br.com.caelum.stella.validation.bdd;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

public class ValidadorSteps {

    private String documento;
    private boolean ehCpf;
    private boolean resultado;

    @Dado("o CPF {string}")
    public void oCpf(String valor) {
        this.documento = valor;
        this.ehCpf = true;
    }

    @Dado("o CNPJ {string}")
    public void oCnpj(String valor) {
        this.documento = valor;
        this.ehCpf = false;
    }

    @Quando("o sistema valida o documento")
    public void validaODocumento() {
        resultado = ehCpf
                ? new CPFValidator(false).invalidMessagesFor(documento).isEmpty()
                : new CNPJValidator(false).invalidMessagesFor(documento).isEmpty();
    }

    @Entao("o documento é considerado válido")
    public void consideradoValido() {
        assertTrue(resultado);
    }

    @Entao("o documento é considerado inválido")
    public void consideradoInvalido() {
        assertFalse(resultado);
    }
}
