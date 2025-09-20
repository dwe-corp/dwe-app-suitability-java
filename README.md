API de Suitability - DWE One

Esta API realiza a avaliação de perfil de risco de investidores com base em respostas a um questionário. Os perfis possíveis são: CONSERVADOR, MODERADO e AGRESSIVO. O sistema também retorna sugestões de alocação de ativos de acordo com o perfil.

⸻

🔧 Tecnologias
	•	Java 21
	•	Spring Boot 3+
	•	Spring Web
	•	Spring Security (JWT)
	•	Spring Data JPA
	•	H2 Database
	•	Lombok
	•	Swagger (OpenAPI)
	•	Maven
	•	SLF4J (Logs)
	•	GitHub Actions
	•	Semgrep (SAST)
	•	OWASP ZAP (DAST)
	•	Dependency Review + OSV-Scanner (SCA)
	•	Pipeline CI/CD unificado com gates de segurança

⸻

🚀 Funcionalidades
	•	Cadastro de avaliações de suitability (POST /suitability)
	•	Retorno do perfil de risco (CONSERVADOR, MODERADO, AGRESSIVO)
	•	Regras de recomendação de carteira por perfil
	•	Validação de campos obrigatórios e formatos
	•	Tratamento global de exceções com mensagens amigáveis e logs
	•	Geração de logs em todos os pontos críticos (controller, service, exceptions)
	•	Token JWT obrigatório (exceto Swagger)
	•	Listagem de avaliações realizadas (GET /suitability)
	•	Consulta individual (GET /suitability/{id})
	•	Busca por e-mail (GET /suitability?email=...)
	•	Proteção de rotas via filtro JWT
	•	Análises de segurança automatizadas no CI/CD (SAST, SCA e DAST) ✅

⸻

🔒 Segurança no CI/CD

Integração de segurança contínua usando GitHub Actions.

1) SAST – Semgrep
	•	Regras: p/owasp-top-ten, p/security-audit, p/secrets.
	•	Gera SARIF publicado em Security ▸ Code scanning (severidade + recomendações).
	•	No pipeline unificado há gate: se houver findings level==error o deploy é bloqueado.

Arquivos:
	•	./github/workflows/ci-cd-security.yml (job sast_semgrep)
	•	Artefato: semgrep-report/semgrep.sarif

2) SCA – Dependências de Terceiros
	•	Dependency Review: roda em Pull Requests, mostra CVEs e verifica licenças; comenta resumo no PR.
	•	OSV-Scanner (via Docker): gera SARIF e publica em Code scanning.

Arquivos:
	•	./github/workflows/sca-dependency-review.yml (rápido em PR)
	•	./github/workflows/ci-cd-security.yml (job sca_osv)
	•	Artefato: osv-report/osv.sarif

3) DAST – OWASP ZAP (Baseline)
	•	Executa contra o ambiente de staging (ou fallback https://example.com).
	•	Gera zap-report.html e zap-report.json com evidências.
	•	Gate fora do container: falha o job quando existem alertas HIGH.

Como definir o alvo (TARGET):
	1.	Preferencial: crie STAGING_URL em Settings ▸ Secrets and variables (secret ou repo variable).
	2.	Alternativa: informe a URL manualmente ao rodar o workflow via workflow_dispatch.
	3.	Fallback automático: https://example.com (apenas para demonstração acadêmica).

Arquivos:
	•	./github/workflows/ci-cd-security.yml (jobs deploy_staging e dast_zap)
	•	Artefatos: zap-report/ (HTML + JSON)

4) Pipeline CI/CD Unificado
	•	Workflow: ./github/workflows/ci-cd-security.yml
	•	Gatilhos: push, pull_request, workflow_dispatch.
	•	Políticas:
	•	Bloqueio de deploy de produção se: SAST tiver error ou DAST encontrar HIGH.
	•	Comentário automático de status no PR (job notify).
	•	Artefatos & Dashboards: SARIF (Semgrep/OSV) + HTML/JSON (ZAP) e visibilidade em Security ▸ Code scanning.

⸻

🔐 Autenticação

A API utiliza autenticação com JWT Token. Envie no header:

Authorization: Bearer <seu-token>


⸻

📄 Exemplo de requisição POST /suitability

{
  "nome": "João Prudente",
  "email": "joao@email.com",
  "respostasConservadoras": 6,
  "respostasModeradas": 2,
  "respostasAgressivas": 1
}

✅ Resposta esperada

{
  "cliente": {
    "id": 1,
    "nome": "João Prudente",
    "email": "joao@email.com",
    "risco": "CONSERVADOR"
  },
  "recomendacao": [
    { "tipo": "Renda Fixa", "percentual": 80 },
    { "tipo": "Fundos Multimercado", "percentual": 15 },
    { "tipo": "Renda Variável", "percentual": 5 }
  ]
}


⸻

🧠 Regras de Perfil

A lógica compara as quantidades de respostas e define o perfil conforme o número mais alto. Em caso de empate, prioriza o perfil mais agressivo.
	•	CONSERVADOR: maior número de respostas conservadoras.
	•	MODERADO: maior número de moderadas.
	•	AGRESSIVO: maior número de agressivas.

⸻

🛠️ Como rodar o projeto
	1.	Clone o repositório
	2.	Importe no IntelliJ/Eclipse como Maven
	3.	Rode SuitabilityApplication.java
	4.	Acesse http://localhost:8081/swagger-ui.html

⸻

📂 Rotas disponíveis

Método	Rota	Protegida	Descrição
POST	/suitability	✅	Avaliar e salvar perfil de risco
GET	/suitability	✅	Listar todos os perfis avaliados
GET	/suitability/{id}	✅	Buscar perfil por ID
GET	/suitability?email=...	✅	Buscar perfil por e-mail


⸻

🧪 Testes no Insomnia/Postman

Use o token JWT gerado e envie no header:

Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...


⸻

🗂️ Estrutura do projeto

src/
├── config/         → Handler de exceções globais
├── controller/     → Endpoints da API
├── dto/            → DTOs de entrada
├── model/          → Entidades JPA (PerfilSuitability)
├── repository/     → Interface JPA
├── security/       → Filtro JWT
├── service/        → Lógica de avaliação com logs
├── util/           → Regras de recomendação de carteira
└── SuitabilityApplication.java


⸻

📋 Segurança e Observabilidade
	•	✅ Validação de entradas com Bean Validation (@Valid)
	•	✅ Tratamento de erros com mensagens claras
	•	✅ Logs com SLF4J em controller, service e exception handler
	•	✅ Proteção com JWT
	•	✅ SAST (Semgrep) automático com SARIF
	•	✅ SCA (Dependency Review + OSV) com verificação de licenças e CVEs
	•	✅ DAST (ZAP Baseline) com evidências em HTML/JSON e gate por severidade

⸻

🛡️ Workflows de Segurança
	•	Unificado: ./github/workflows/ci-cd-security.yml
	•	DAST isolado (opcional): ./github/workflows/dast-zap.yml
	•	SCA OSV isolado (opcional): ./github/workflows/sca-osv.yml
	•	Dependency Review (PR): ./github/workflows/sca-dependency-review.yml

Para DAST, configure STAGING_URL como Secret ou Repository variable (ou informe manualmente no workflow_dispatch).

⸻

🧑‍💻 Desenvolvido por

Projeto acadêmico para avaliação de microserviços com autenticação JWT, seguindo arquitetura modular, segurança integrada ao CI/CD e boas práticas de observabilidade.
