# 🛒 DSCommerce API

API RESTful para um sistema de E-commerce desenvolvida com Java e Spring Boot, aplicando boas práticas de arquitetura, modelagem de dados e persistência.

> Projeto em evolução contínua...

---

## 🚀 Tecnologias

- Java 21 (LTS)
- Spring Boot 3
- Spring Data JPA
- Hibernate
- H2 Database (ambiente de desenvolvimento)
- Maven

---

## 📐 Arquitetura

O projeto segue padrão em camadas:

- Controller
- Service
- Repository
- DTO
- Entities

Separação clara de responsabilidades e uso de DTOs para desacoplamento da camada de domínio.

---

## 🧠 Principais Conceitos Aplicados

- Modelagem de entidade com chave composta (`@Embeddable`)
- Uso de `@MapsId` em relacionamento One-to-One
- Persistência de preço no momento da compra (integridade histórica)
- Uso de `Set` para evitar duplicidade em relacionamentos
- Paginação com `PageRequest`
- Banco H2 para ambiente isolado de desenvolvimento

---

## ✅ Funcionalidades Implementadas

- Estrutura base da aplicação
- Camadas organizadas
- Criação de DTOs
- Busca paginada de produtos
- Testes via Postman

---

## ▶ Como Executar

Clone o repositório:

```bash
https://github.com/RaulTheFato7/Commerce_Spring_Boot.git
