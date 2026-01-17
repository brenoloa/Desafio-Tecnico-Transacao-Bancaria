# API de Transações

API REST para gerenciamento de transações financeiras com cálculo de estatísticas em tempo real, desenvolvida como solução para o desafio técnico do Itaú.

## 📋 Sobre o Projeto

Este projeto foi desenvolvido como resposta ao [Desafio Itaú - Vaga 99 Junior](https://github.com/rafaellins-itau/desafio-itau-vaga-99-junior).

A API permite registrar transações financeiras e calcular estatísticas em tempo real (últimos 60 segundos), utilizando armazenamento em memória para alta performance.

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.2.7**
- **Gradle**
- **Lombok**
- **Bean Validation**

## 📦 Funcionalidades

- ✅ Registro de transações com validação de data/hora
- ✅ Cálculo de estatísticas dos últimos 60 segundos (count, sum, avg, min, max)
- ✅ Exclusão de todas as transações
- ✅ Armazenamento em memória (InMemoryTransactionRepository)
- ✅ Validação de transações futuras
- ✅ Tratamento de erros com status HTTP apropriados

## 🛠️ Como Executar

### Pré-requisitos

- Java 21 ou superior
- Gradle 8.x (ou use o wrapper incluído)

### Instalação

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
cd api-transacao
```

2. Compile o projeto:
```bash
./gradlew build
```

3. Execute a aplicação:
```bash
./gradlew bootRun
```

A aplicação estará disponível em `http://localhost:8080`

## 📚 Endpoints da API

### Registrar Transação
```http
POST /transacao
Content-Type: application/json

{
  "valor": 123.45,
  "dataHora": "2000-08-07T12:34:56.789-03:00"
}
```

**Respostas:**
- `201 Created` - Transação registrada com sucesso
- `400 Bad Request` - JSON inválido
- `422 Unprocessable Entity` - Valor negativo ou transação no futuro

### Obter Estatísticas
```http
GET /estatistica
Accept: application/json
```

**Resposta:**
```json
{
  "count": 10,
  "sum": 1234.56,
  "avg": 123.45,
  "min": 50.00,
  "max": 200.00
}
```

Retorna estatísticas das transações dos últimos 60 segundos.

### Deletar Todas as Transações
```http
DELETE /transacao
```

**Resposta:**
- `200 OK` - Todas as transações foram removidas

## 📝 Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/example/api_transacao/
│   │       ├── rest/
│   │       │   └── TransactionalController.java
│   │       ├── service/
│   │       │   ├── TransactionalService.java
│   │       │   └── UnprocessableTransactionException.java
│   │       ├── repository/
│   │       │   └── InMemoryTransactionRepository.java
│   │       ├── dto/
│   │       │   ├── TransactionRequestDTO.java
│   │       │   └── StatisticsResponseDTO.java
│   │       └── ApiTransacaoApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/example/api_transacao/
            ├── rest/
            │   └── TransactionalControllerTest.java
            └── service/
                └── TransactionalServiceTest.java
```

## 🧪 Testes

Execute os testes com:
```bash
./gradlew test
```

O projeto inclui testes de integração e unitários que validam:
- Registro de transações válidas e inválidas
- Cálculo de estatísticas
- Validação de transações futuras
- Remoção de transações

## 📄 Regras de Negócio

- Transações com `valor` negativo são rejeitadas (422)
- Transações com `dataHora` no futuro são rejeitadas (422)
- Estatísticas consideram apenas transações dos últimos 60 segundos
- Quando não há transações válidas, todas as estatísticas retornam 0

## 🔗 Links

- [Desafio Original](https://github.com/rafaellins-itau/desafio-itau-vaga-99-junior)
