package br.com.caelum.stella.privacy;

public final class CpfMasker {

    private CpfMasker() {
    }

    public static String mask(String cpf) {
        String digitos = cpf.replaceAll("\\D", "");
        return "***." + digitos.substring(3, 6) + "." + digitos.substring(6, 9) + "-**";
    }
}
