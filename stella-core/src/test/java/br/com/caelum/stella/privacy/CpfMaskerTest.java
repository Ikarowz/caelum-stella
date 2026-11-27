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
