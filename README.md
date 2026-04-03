# DSCommerce

<div align="center">

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.3-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6-6DB33F?style=flat-square&logo=springsecurity&logoColor=white)
![OAuth2](https://img.shields.io/badge/OAuth2-JWT-black?style=flat-square&logo=jsonwebtokens&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-prod-4169E1?style=flat-square&logo=postgresql&logoColor=white)
![H2](https://img.shields.io/badge/H2-test-blue?style=flat-square)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white)

**API RESTful completa para um sistema de e-commerce.**
Do modelo de domínio à autenticação OAuth2 com JWT, controle de acesso por perfil e tratamento centralizado de exceções.

Projeto de referência do curso **[Java Spring Professional](https://devsuperior.com.br)** — DevSuperior.

</div>

---

## Sumário

- [Modelo de Domínio](#modelo-de-domínio)
- [Tecnologias](#tecnologias)
- [Endpoints](#endpoints)
- [Segurança](#segurança)
- [Como executar](#como-executar)
- [Padrões implementados](#padrões-implementados)

---

## Modelo de Domínio

```
User (1) ──────< Order (N)
User (N) >────< Role (N)
Order (1) ──── Payment (1)
Order (1) ──────< OrderItem (N)
Product (1) ──────< OrderItem (N)
Product (N) >────< Category (N)
```

| Entidade  | Tabela        | Descrição                                           |
|-----------|---------------|-----------------------------------------------------|
| User      | tb_user       | Usuário com perfis `ROLE_CLIENT` e `ROLE_ADMIN`     |
| Role      | tb_role       | Perfis de acesso                                    |
| Product   | tb_product    | Produto com nome, descrição, preço e imagem         |
| Category  | tb_category   | Categoria de produto                                |
| Order     | tb_order      | Pedido com status e associação ao cliente           |
| OrderItem | tb_order_item | Item do pedido com chave composta (order + product) |
| Payment   | tb_payment    | Pagamento com `@MapsId` compartilhando o ID do pedido |

### Ciclo de vida do pedido

```
WAITING_PAYMENT → PAID → SHIPPED → DELIVERED
                                 ↘ CANCELED
```

---

## Tecnologias

| Camada           | Tecnologia                                          |
|------------------|-----------------------------------------------------|
| Linguagem        | Java 21                                             |
| Framework        | Spring Boot 4.0.3                                   |
| Segurança        | Spring Security 6, OAuth2 Authorization Server, JWT |
| Criptografia JWT | RSA-2048 (Nimbus JOSE+JWT)                          |
| ORM              | Spring Data JPA / Hibernate                         |
| Banco de dados   | H2 (perfil `test`) / PostgreSQL (perfil `prod`)     |
| Validação        | Jakarta Bean Validation                             |
| Build            | Maven                                               |
| Testes           | JUnit 5, Spring Security Test                       |

---

## Endpoints

### 🔐 Autenticação

| Método | Rota          | Descrição                        | Acesso  |
|--------|---------------|----------------------------------|---------|
| POST   | /oauth2/token | Obtém token JWT (password grant) | Público |

### 📦 Produtos

| Método | Rota           | Descrição                            | Acesso     |
|--------|----------------|--------------------------------------|------------|
| GET    | /products      | Lista produtos com paginação e busca | Público    |
| GET    | /products/{id} | Busca produto por ID                 | Público    |
| POST   | /products      | Cria novo produto                    | ROLE_ADMIN |
| PUT    | /products/{id} | Atualiza produto                     | ROLE_ADMIN |
| DELETE | /products/{id} | Remove produto                       | ROLE_ADMIN |

### 🗂️ Categorias

| Método | Rota        | Descrição              | Acesso  |
|--------|-------------|------------------------|---------|
| GET    | /categories | Lista todas categorias | Público |

### 🛒 Pedidos

| Método | Rota         | Descrição         | Acesso                  |
|--------|--------------|-------------------|-------------------------|
| GET    | /orders/{id} | Busca pedido por ID | Dono do pedido ou Admin |
| POST   | /orders      | Cria novo pedido  | ROLE_CLIENT ou Admin    |

### 👤 Usuários

| Método | Rota      | Descrição                       | Acesso               |
|--------|-----------|---------------------------------|----------------------|
| GET    | /users/me | Retorna dados do usuário logado | ROLE_CLIENT ou Admin |

---

## Segurança

O projeto implementa um **Authorization Server customizado** com grant type `password`, gerando tokens JWT auto-contidos assinados com RSA-2048.

- **Token TTL:** 86.400 segundos (24h), configurável via `JWT_DURATION`
- **Claims customizados:** `authorities` e `username` embutidos no token
- **Controle de acesso por método:** `@PreAuthorize` nos controllers
- **Regra Self ou Admin:** usuários acessam apenas seus próprios recursos; admins têm acesso irrestrito
- **CORS configurado** para `localhost:3000` e `localhost:5173` (configurável)

### Usuários pré-carregados

| Usuário     | E-mail          | Senha    | Perfil                    |
|-------------|-----------------|----------|---------------------------|
| Maria Brown | maria@gmail.com | 12345678 | `ROLE_CLIENT`             |
| Alex Green  | alex@gmail.com  | 12345678 | `ROLE_CLIENT + ROLE_ADMIN`|

---

## Como executar

### Pré-requisitos

- Java 21+
- Maven 3.8+

### Perfil de teste (H2 em memória)

```bash
git clone https://github.com/RaulTheFato7/commerce.git
cd commerce
./mvnw spring-boot:run
```

- Aplicação: `http://localhost:8080`
- Console H2: `http://localhost:8080/h2-console`

### Obtendo um token JWT

```bash
curl -X POST http://localhost:8080/oauth2/token \
  -u "myclientid:myclientsecret" \
  -d "grant_type=password&username=alex@gmail.com&password=12345678"
```

### Variáveis de ambiente

| Variável      | Padrão         | Descrição                       |
|---------------|----------------|---------------------------------|
| CLIENT_ID     | myclientid     | ID do cliente OAuth2            |
| CLIENT_SECRET | myclientsecret | Secret do cliente OAuth2        |
| JWT_DURATION  | 86400          | Duração do token em segundos    |
| CORS_ORIGINS  | localhost:3000 | Origens permitidas (CORS)       |

---

## Estrutura do Projeto

```
src/main/java/com/devsuperior/commerce/
├── config/
│   ├── AuthorizationServerConfig.java     # OAuth2 Authorization Server
│   ├── ResourceServerConfig.java          # Resource Server + CORS
│   └── customgrant/                       # Grant type password customizado
│       ├── CustomPasswordAuthenticationConverter.java
│       ├── CustomPasswordAuthenticationProvider.java
│       ├── CustomPasswordAuthenticationToken.java
│       └── CustomUserAuthorities.java
├── controller/
│   ├── ProductController.java
│   ├── CategoryController.java
│   ├── OrderController.java
│   ├── UserController.java
│   └── handlers/
│       └── ControllerExceptionHandler.java
├── service/
│   ├── ProductService.java
│   ├── CategoryService.java
│   ├── OrderService.java
│   ├── UserService.java
│   ├── AuthService.java
│   └── exceptionals/
│       ├── ResourceNotFoundException.java
│       ├── DataBaseException.java
│       └── ForbiddenException.java
├── repositories/                          # 5 Spring Data JPA Repositories
├── entities/                              # 8 entidades JPA
├── dto/                                   # 10 DTOs de request/response
└── projections/
    └── UserDetailsProjection.java         # Projeção para carga eficiente de roles
```

---

## Padrões Implementados

| Padrão                     | Descrição                                                                 |
|----------------------------|---------------------------------------------------------------------------|
| Arquitetura em camadas     | Controller → Service → Repository → Entity                                |
| DTO Pattern                | Objetos separados para entrada e saída da API                             |
| Exception Handler          | `@ControllerAdvice` com respostas padronizadas                            |
| Projection Pattern         | Consulta otimizada de roles via interface JPA                             |
| Chave composta embeddable  | `OrderItemPK` com `@Embeddable`                                           |
| @MapsId                    | `Payment` compartilha o ID do `Order` (relação 1:1)                       |
| Preço capturado no pedido  | Preço do produto registrado em `OrderItem.price` no momento da compra     |

---

<div align="center">

Desenvolvido por **Raul Vitorio** — curso Java Spring Professional · [DevSuperior](https://devsuperior.com.br)

</div>