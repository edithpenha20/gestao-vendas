
# API de Gestão de Vendas

API Rest para gestão de vendas onde é possível cadastrar produtos por categoria, categoria de produtos, clientes e vendas por cliente.

Esta API foi criada a partir do curso "API Rest com Java e Spring Boot".


## Tecnologias utilizadas

- Spring Boot 3.1
- Java 17
- Banco de dados MySQL
- Flyway
- Mapeamento de entidades com JPA
- Validações com Bean Validation
- Exception Handler
- DTOs
- Documentação de API com OpenAPI
- Actuator
- Rastreamento de aplicação com Jaeger
- Métricas com Prometheus
- Dashboard de métricas com Grafana
- Docker


## Melhorias

- Para os DTOs utilizei a nova funcionalidade do Java 17, os records;
- Utilizei o Open Telemetry juntamente com o Jaeger.


## Documentação da API

#### Retorna todos os itens de Categoria

```http
  GET /categorias
```

#### Retorna um item de Categoria

```http
  GET /categorias/{codigo}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `codigo`      | `long` | **Obrigatório**. O ID da categoria que você quer |

#### Cadastrar uma Categoria

```http
  POST /categorias
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `codigo`      | `long` | **Gerado automaticamente**. |
| `nome`      | `string` | **Obrigatório**. Nome da categoria |

#### Alterar uma Categoria

```http
  PUT /categorias/{codigo}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `codigo`      | `long` | **Gerado automaticamente**. |
| `nome`      | `string` | **Obrigatório**. Nome da categoria |

#### Excluir uma Categoria

```http
  DELETE /categorias/{codigo}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `codigo`      | `long` | **Obrigatório**. O ID da categoria que você quer excluir |
