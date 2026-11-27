# Prompt usado no experimento (Carta "IA a servico da qualidade")

Modelo: Claude (Anthropic) via claude.ai — registrar nome/versao exibidos na interface no dia da execucao.

> Voce e um engenheiro de testes. Gere testes JUnit 4 para as classes `CPFValidator` e
> `CNPJValidator` da biblioteca Caelum Stella (`br.com.caelum.stella.validation`).
> Cubra: entradas validas e invalidas, nulo, vazio, digitos repetidos, digito verificador
> alterado, formatado/nao formatado e o comportamento de `assertValid` e `invalidMessagesFor`.
> Responda apenas com o codigo Java.

Regra do experimento: a saida da IA foi salva SEM edicao nos arquivos
`*AiGeneratedTest.java`. Antes de corrigir qualquer coisa, rode e preencha `docs/registro-ia.csv`.
