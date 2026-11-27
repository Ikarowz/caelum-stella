# language: pt
Funcionalidade: Validação de documentos brasileiros
  Como desenvolvedor de um sistema de cadastro
  Quero validar CPF e CNPJ informados pelo usuário
  Para impedir o cadastro de documentos inexistentes

  Cenário: CPF válido é aceito
    Dado o CPF "11144477735"
    Quando o sistema valida o documento
    Então o documento é considerado válido

  Cenário: CPF com dígito verificador errado é recusado
    Dado o CPF "11144477736"
    Quando o sistema valida o documento
    Então o documento é considerado inválido

  Cenário: CPF com quantidade incorreta de dígitos é recusado
    Dado o CPF "1114447773"
    Quando o sistema valida o documento
    Então o documento é considerado inválido

  Cenário: CPF com todos os dígitos iguais é recusado
    Dado o CPF "11111111111"
    Quando o sistema valida o documento
    Então o documento é considerado inválido

  Cenário: CNPJ válido é aceito
    Dado o CNPJ "11222333000181"
    Quando o sistema valida o documento
    Então o documento é considerado válido

  Cenário: CNPJ com dígito verificador errado é recusado
    Dado o CNPJ "11222333000182"
    Quando o sistema valida o documento
    Então o documento é considerado inválido
