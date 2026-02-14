# 🔐 Authentication API

Uma API robusta de autenticação e autorização desenvolvida com **Spring Boot** e **JWT**.

## 📋 Características

- ✅ Autenticação com **JWT (JSON Web Tokens)**
- ✅ Sistema de **Refresh Token** com revogação automática
- ✅ Criptografia de senhas com **BCrypt**
- ✅ Validação de dados com **Bean Validation**
- ✅ Tratamento customizado de exceções
- ✅ Segurança com **Spring Security**
- ✅ Banco de dados **H2** em memória (desenvolvimento)
- ✅ Documentação de endpoints com comentários

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Descrição |
|-----------|--------|-----------|
| Java | 21 | Linguagem de programação |
| Spring Boot | 3.2.5 | Framework Java para aplicações web |
| Spring Security | 6.2.4 | Autenticação e autorização |
| Spring Data JPA | 3.2.5 | Persistência de dados |
| JWT (JJWT) | 0.12.5 | Tokens seguros |
| H2 Database | Latest | Banco de dados em memória |
| Maven | Latest | Gerenciador de dependências |

---

## 📦 Dependências Principais

```xml
<!-- Spring Boot Starter Web: Framework para criar APIs RESTful com Tomcat embutido -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Spring Boot Starter Data JPA: ORM para persistência de dados com Hibernate -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Spring Boot Starter Security: Autenticação, autorização e proteção contra ataques -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- Spring Boot Starter Validation: Validação de dados em DTOs e entidades -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- Spring Security Crypto: Utilitários para criptografia de senhas (BCrypt) -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-crypto</artifactId>
</dependency>

<!-- JJWT: Biblioteca para criação e validação de JSON Web Tokens -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.5</version>
</dependency>

<!-- JJWT Implementation: Implementação runtime do JJWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.5</version>
    <scope>runtime</scope>
</dependency>

<!-- JJWT Jackson: Suporte para serialização JSON de tokens JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.5</version>
    <scope>runtime</scope>
</dependency>

<!-- H2 Database: Banco de dados relacional em memória para desenvolvimento -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Spring Boot DevTools: Reinicialização automática durante desenvolvimento -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

---

## 🚀 Como Usar

### 1️⃣ Pré-requisitos
- Java 21+
- Maven 3.6+

### 2️⃣ Configuração

Edite o arquivo `src/main/resources/application.properties`:

```properties
# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:authdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# H2 Console (Desenvolvimento)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JWT Access Token: 15 minutos (900000 ms)
api.security.token.secret=seu-secret-aqui
api.security.token.expiration=900000

# JWT Refresh Token: 7 dias (604800000 ms)
api.security.refresh-token.expiration=604800000
```

---

## 📡 Endpoints

### 🔓 Autenticação (Público)

#### **POST** `/v1/auth/login`
Realiza login e retorna access token + refresh token

**Request:**
```json
{
  "email": "usuario@example.com",
  "password": "senha123"
}
```

**Response (200):**
```json
{
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwidXNlcklkIjoxLCJyb2xlIjoiVVNFUiIsImlzQWN0aXZlIjp0cnVlLCJpYXQiOjE3NzExMTA0MjAsImV4cCI6MTc3MTExMTMyMH0...",
    "refreshToken": "275da570-aff2-4366-b58d-fa28ea81ffc6"
  },
  "message": {
    "severity": "success",
    "message": "Sucesso",
    "details": [
      "Autenticação realizada com sucesso"
    ]
  },
  "statusCode": 200
}
```

---

#### **POST** `/v1/auth/refresh`
Renova o access token usando o refresh token

**Request:**
```json
{
  "refreshToken": "275da570-aff2-4366-b58d-fa28ea81ffc6"
}
```

**Response (200):**
```json
{
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwidXNlcklkIjoxLCJyb2xlIjoiVVNFUiIsImlzQWN0aXZlIjp0cnVlLCJpYXQiOjE3NzExMTA0MjAsImV4cCI6MTc3MTExMTMyMH0...",
    "refreshToken": "x9y8z7w6-v5u4t3s2-r1q0p9o8-n7m6l5k4"
  },
  "message": {
    "severity": "success",
    "message": "Sucesso",
    "details": [
      "Token renovado com sucesso"
    ]
  },
  "statusCode": 200
}
```

---

### 🔐 Registro (Público)

#### **POST** `/v1/user-register`
Registra um novo usuário com role **USER**

**Request:**
```json
{
  "email": "novousuario@example.com",
  "password": "senha123",
  "name": "João Silva"
}
```

**Response (201):**
```json
{
  "data": {
    "id": 1,
    "email": "novousuario@example.com",
    "name": "João Silva",
    "role": "USER",
    "active": false,
    "createdAt": "2026-02-14T19:30:00"
  },
  "message": {
    "severity": "success",
    "message": "Sucesso",
    "details": [
      "Usuário cadastrado com sucesso"
    ]
  },
  "statusCode": 201
}
```

---

#### **POST** `/v1/user-register/admin`
Registra um novo usuário com role **ADMIN**

**Request:**
```json
{
  "email": "admin@example.com",
  "password": "senha123",
  "name": "Administrador"
}
```

**Response (201):**
```json
{
  "data": {
    "id": 2,
    "email": "admin@example.com",
    "name": "Administrador",
    "role": "ADMIN",
    "active": false,
    "createdAt": "2026-02-14T19:30:00"
  },
  "message": {
    "severity": "success",
    "message": "Sucesso",
    "details": [
      "Usuário cadastrado com sucesso"
    ]
  },
  "statusCode": 201
}
```

---

## 🔐 Fluxo de Autenticação

### 1. Login
```
POST /v1/auth/login
├─ Email e senha são validados
├─ Senha é verificada contra hash BCrypt
├─ Access Token (15min) é gerado
├─ Refresh Tokens antigos são revogados
└─ Novo Refresh Token (7 dias) é emitido
```

### 2. Refresh Token
```
POST /v1/auth/refresh
├─ Refresh token é validado
├─ Token antigo é revogado
├─ Novo Access Token é gerado
└─ Novo Refresh Token é emitido
```

### 3. Logout
```
POST /v1/user/logout
├─ Usuário autenticado é obtido do token
├─ Todos os refresh tokens são revogados
└─ Usuário fica desconectado
```

---

## 🛡️ Segurança

### ✅ Implementado
- **JWT**: Tokens com assinatura HMAC-SHA256
- **BCrypt**: Hashing seguro de senhas
- **Refresh Token Rotation**: Revogação automática de tokens antigos
- **Token Expiration**: Access token (15min), Refresh token (7 dias)
- **CORS**: Configurado para ambiente
- **Spring Security**: Proteção contra ataques comuns (CSRF, XSS, etc)

---

## 📚 Referências

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [JJWT Library](https://github.com/jwtk/jjwt)
- [JWT.io](https://jwt.io/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)

---

## 👨‍💻 Autor

Desenvolvido por Wesley Viricimo

---

**Última atualização**: Fevereiro 2026

