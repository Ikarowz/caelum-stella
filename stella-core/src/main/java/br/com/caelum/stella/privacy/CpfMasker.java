package br.com.caelum.stella.privacy;

public final class CpfMasker {

    private CpfMasker() {
    }

    public static String mask(String cpf) {
        if (cpf == null) {
            throw new IllegalArgumentException("CPF nao pode ser nulo");
        }
        String digitos = cpf.replaceAll("\\D", "");
        if (digitos.length() != 11) {
            throw new IllegalArgumentException("CPF deve ter 11 digitos");
        }
        return "***." + digitos.substring(3, 6) + "." + digitos.substring(6, 9) + "-**";
    }
}
