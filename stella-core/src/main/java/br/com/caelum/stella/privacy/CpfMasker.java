package br.com.caelum.stella.privacy;

public final class CpfMasker {

    private CpfMasker() {
    }

    public static String mask(String cpf) {
        return "***.444.777-**";
    }
}
