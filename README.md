# API de Suitability - DWE One

Esta API realiza a **avaliação de perfil de risco de investidores** com base em respostas a um questionário. Os perfis possíveis são: `CONSERVADOR`, `MODERADO` e `AGRESSIVO`. O sistema também retorna sugestões de alocação de ativos de acordo com o perfil.

---

## 🔧 Tecnologias

- Java 21
- Spring Boot 3+
- Spring Web
- Spring Security (JWT)
- Spring Data JPA
- H2 Database
- Lombok
- Swagger (OpenAPI)
- Maven

---

## 🚀 Funcionalidades

- Cadastro de avaliações de suitability (`POST /suitability`)
- Retorno do perfil de risco (`CONSERVADOR`, `MODERADO`, `AGRESSIVO`)
- Regras de recomendação de carteira por perfil
- Validação de campos obrigatórios e formatos
- Token JWT obrigatório (exceto Swagger e login)
- Listagem de avaliações realizadas (`GET /suitability`)
- Consulta individual (`GET /suitability/{id}`)
- Proteção de rotas via filtro JWT

---

## 🔐 Autenticação

A API utiliza autenticação com **JWT Token**.

Você deve enviar o token no header:

```
Authorization: Bearer <seu-token>
```

---

## 📄 Exemplo de requisição `POST /suitability`

```json
{
  "respostasConservadoras": 6,
  "respostasModeradas": 2,
  "respostasAgressivas": 1
}
```

### ✅ Resposta esperada

```json
{
  "risco": "CONSERVADOR",
  "recomendacoes": [
    { "tipo": "Renda Fixa", "percentual": 80 },
    { "tipo": "Fundos Multimercado", "percentual": 15 },
    { "tipo": "Renda Variável", "percentual": 5 }
  ]
}
```

---

## 🧠 Regras de Perfil

A lógica compara as quantidades de respostas e define o perfil conforme o número mais alto. Em caso de empate, prioriza o perfil mais agressivo.

- **CONSERVADOR**: maior número de respostas conservadoras.
- **MODERADO**: maior número de moderadas.
- **AGRESSIVO**: maior número de agressivas.

---

## 🛠️ Como rodar o projeto

1. Clone o repositório
2. Importe no IntelliJ ou Eclipse como projeto Maven
3. Rode a classe `SuitabilityApplication.java`
4. Acesse: `http://localhost:8081/swagger-ui.html`

---

## 📂 Rotas disponíveis

| Método | Rota               | Protegida | Descrição                          |
|--------|--------------------|-----------|------------------------------------|
| POST   | `/suitability`     | ✅        | Avaliar e salvar perfil de risco   |
| GET    | `/suitability`     | ✅        | Listar todos os perfis avaliados   |
| GET    | `/suitability/{id}`| ✅        | Buscar perfil por ID               |

---

## 🧪 Testes no Insomnia

Use o token JWT gerado pela API de `auth` e envie no header.

Exemplo de header:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## 🗂️ Estrutura do projeto

```
src/
├── config/
├── controller/
├── dto/
├── model/
├── repository/
├── security/
├── service/
├── util/
└── SuitabilityApplication.java
```

---

## 🧑‍💻 Desenvolvido por

Projeto acadêmico para avaliação de microserviços com autenticação JWT, seguindo arquitetura modular e documentação Swagger.