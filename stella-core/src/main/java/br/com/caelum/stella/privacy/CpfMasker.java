package br.com.caelum.stella.privacy;

public final class CpfMasker {

    private CpfMasker() {
    }

    public static String mask(String cpf) {
        return "***." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-**";
    }
}
