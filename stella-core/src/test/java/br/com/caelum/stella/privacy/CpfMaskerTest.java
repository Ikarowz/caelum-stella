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
