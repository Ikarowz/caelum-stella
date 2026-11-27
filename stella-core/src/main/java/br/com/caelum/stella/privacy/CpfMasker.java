package br.com.caelum.stella.privacy;

/** Oculta os 3 primeiros e os 2 ultimos digitos de um CPF (LGPD), ex.: ***.444.777-**. */
public final class CpfMasker {

    private static final int TAMANHO_CPF = 11;

    private CpfMasker() {
    }

    public static String mask(String cpf) {
        String digitos = somenteDigitos(cpf);
        return "***." + digitos.substring(3, 6) + "." + digitos.substring(6, 9) + "-**";
    }

    private static String somenteDigitos(String cpf) {
        if (cpf == null) {
            throw new IllegalArgumentException("CPF nao pode ser nulo");
        }
        String digitos = cpf.replaceAll("\\D", "");
        if (digitos.length() != TAMANHO_CPF) {
            throw new IllegalArgumentException("CPF deve ter " + TAMANHO_CPF + " digitos");
        }
        return digitos;
    }
}
