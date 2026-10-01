# 🔐 Spring Security Authentication & Authorization

<p align="center">
  <b>A complete Spring Boot project demonstrating modern Spring Security concepts with JDBC authentication, password encoding, role-based authorization, and secure REST APIs.</b>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk" />
  <img src="https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen?style=for-the-badge&logo=springboot" />
  <img src="https://img.shields.io/badge/Spring%20Security-7.x-green?style=for-the-badge&logo=springsecurity" />
  <img src="https://img.shields.io/badge/MySQL-Database-blue?style=for-the-badge&logo=mysql" />
</p>

---

## 📌 About The Project

This project is built to understand and implement **Spring Security from the ground up**.

It demonstrates how a Spring Boot application can:

* 🔑 Authenticate users
* 🔒 Secure REST endpoints
* 👤 Manage users using JDBC
* 🛡️ Apply role-based authorization
* 🔐 Encode passwords securely
* 🚫 Restrict unauthorized requests
* ⚙️ Configure Spring Security using `SecurityFilterChain`
* 🗄️ Connect authentication with MySQL
* 🎯 Apply method-level security

The main objective is to understand **what actually happens when a user sends a request to a secured Spring Boot application**.

---

# 🏗️ Architecture

```text
                    ┌──────────────────┐
                    │      Client      │
                    │ Postman / Browser│
                    └────────┬─────────┘
                             │
                             ▼
                 ┌──────────────────────┐
                 │   Spring Security    │
                 │      Filters         │
                 └──────────┬───────────┘
                            │
                  Authentication
                            │
                            ▼
                 ┌──────────────────────┐
                 │ JdbcUserDetailsManager│
                 └──────────┬───────────┘
                            │
                            ▼
                    ┌──────────────┐
                    │    MySQL     │
                    │    users     │
                    └──────────────┘
                            │
                     User Details
                            │
                            ▼
                 ┌──────────────────────┐
                 │ Authorization Rules  │
                 │   Roles / Methods    │
                 └──────────┬───────────┘
                            │
                            ▼
                 ┌──────────────────────┐
                 │    Controller        │
                 │   REST Endpoints     │
                 └──────────────────────┘
```

---

# 🚀 Key Features

### 🔐 Authentication

User credentials are verified using Spring Security.

```text
Username + Password
        ↓
Spring Security
        ↓
JdbcUserDetailsManager
        ↓
MySQL
        ↓
Authentication Result
```

### 🛡️ Authorization

Different endpoints can be protected based on user roles.

Example:

```text
USER  → /user/**
ADMIN → /admin/**
```

---

### 🔑 Password Security

Passwords should never be stored as plain text.

The application uses a password encoder:

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

Password flow:

```text
Plain Password
      ↓
BCryptPasswordEncoder
      ↓
Encrypted Password
      ↓
Database
```

---

# ⚙️ Security Configuration

The application's security rules are configured using `SecurityFilterChain`.

Example structure:

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/public/**").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/user/**").hasRole("USER")
            .anyRequest().authenticated()
        )
        .formLogin(Customizer.withDefaults());

    return http.build();
}
```

---

# 🧩 Main Spring Security Components

| Component                | Purpose                               |
| ------------------------ | ------------------------------------- |
| `SecurityFilterChain`    | Defines security rules                |
| `JdbcUserDetailsManager` | Loads users from database             |
| `UserDetails`            | Represents authenticated user         |
| `PasswordEncoder`        | Securely encodes passwords            |
| `Authentication`         | Stores authenticated user information |
| `@EnableMethodSecurity`  | Enables method-level authorization    |
| `HttpSecurity`           | Configures web security               |

---

# 🗄️ Database

The project uses **MySQL** for storing authentication information.

### Users Table

```text
users
├── username
├── password
└── enabled
```

### Authorities Table

```text
authorities
├── username
└── authority
```

Example:

```text
username     password        enabled
-------------------------------------
ravi         ********        true
```

and:

```text
username     authority
---------------------
ravi         ROLE_USER
```

---

# 🔄 Authentication Flow

```text
        Client
          │
          ▼
   Login Request
          │
          ▼
 ┌─────────────────┐
 │ Spring Security │
 └────────┬────────┘
          │
          ▼
 JdbcUserDetailsManager
          │
          ▼
        MySQL
          │
          ▼
   User Credentials
          │
          ▼
 Password Verification
          │
      ┌───┴───┐
      │       │
    Valid   Invalid
      │       │
      ▼       ▼
  Access    401/403
```

---

# 🛡️ Authorization Flow

Authentication answers:

> **Who are you?**

Authorization answers:

> **What are you allowed to access?**

Example:

```java
.requestMatchers("/admin/**")
.hasRole("ADMIN")
```

A normal user:

```text
USER
 ↓
/admin/dashboard
 ↓
Access Denied
```

An admin:

```text
ADMIN
 ↓
/admin/dashboard
 ↓
Access Granted
```

---

# 🧠 Concepts Learned

This project helped in understanding:

### Spring Boot

* REST APIs
* Dependency Injection
* Bean Configuration
* Application Configuration
* Controller Layer

### Spring Security

* Authentication
* Authorization
* Security Filters
* SecurityFilterChain
* UserDetails
* UserDetailsService
* JDBC Authentication
* Password Encoding
* Roles
* Authorities
* Method-Level Security
* CSRF
* HTTP Security

### Database

* MySQL
* JDBC
* Users table
* Authorities table
* Database-backed authentication

---

# 📁 Project Structure

```text
spring-security/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── examp/
│   │   │           └── security/
│   │   │               │
│   │   │               ├── config/
│   │   │               │   └── SpringConfig.java
│   │   │               │
│   │   │               ├── controller/
│   │   │               │   └── ...
│   │   │               │
│   │   │               └── ...
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│
├── pom.xml
└── README.md
```

---

# 🛠️ Tech Stack

### Backend

* ☕ Java 21
* 🌱 Spring Boot
* 🔐 Spring Security
* 🌐 Spring Web

### Database

* 🐬 MySQL
* 🔌 JDBC

### Development Tools

* IntelliJ IDEA
* Maven
* Git
* GitHub
* Postman

---

# 💻 Requirements

Before running this project, make sure you have:

```text
Java 21+
Maven
MySQL
IntelliJ IDEA / VS Code
Git
Postman (optional)
```

Check Java:

```bash
java -version
```

Check Maven:

```bash
mvn -version
```

---

# ⚡ Getting Started

## 1️⃣ Clone Repository

```bash
git clone https://github.com/Ravi29102004/spring-security.git
```

```bash
cd spring-security
```

---

## 2️⃣ Configure MySQL

Create a database:

```sql
CREATE DATABASE spring_security;
```

Update:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/spring_security
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

> ⚠️ Never commit your real database password to GitHub.

---

## 3️⃣ Build Project

```bash
mvn clean install
```

---

## 4️⃣ Run Application

```bash
mvn spring-boot:run
```

Application will start on the configured port.

---

# 🧪 Testing

You can test secured endpoints using:

### Postman

```text
GET
POST
PUT
DELETE
```

Authentication can be tested using configured Spring Security credentials.

---

# 📊 Security Concept Map

```text
                    SPRING SECURITY
                           │
          ┌────────────────┼────────────────┐
          │                │                │
          ▼                ▼                ▼
   Authentication    Authorization      Password
          │                │             Security
          │                │                │
          ▼                ▼                ▼
    UserDetails       Roles           BCrypt
          │                │
          ▼                ▼
 JdbcUserDetailsManager  Authorities
          │
          ▼
        MySQL
```

---

# 🎯 Learning Objective

The purpose of this project is not just to create a login system.

The main goal is to understand the **internal security flow of a Spring Boot application** and build a strong foundation for advanced backend development.

Future improvements can include:

```text
Spring Security
      ↓
JWT Authentication
      ↓
Refresh Tokens
      ↓
Role-Based Access Control
      ↓
OAuth2
      ↓
Microservices Security
      ↓
API Gateway Security
```

---

# 🔮 Future Improvements

* [ ] JWT Authentication
* [ ] Refresh Token
* [ ] Role-Based Access Control
* [ ] Custom User Registration
* [ ] Email Verification
* [ ] Password Reset
* [ ] OAuth2 / Google Login
* [ ] Redis-based Session Management
* [ ] API Gateway Security
* [ ] Dockerization
* [ ] Spring Cloud Security

---

# 📚 Project Purpose

This repository is part of my **Spring Boot Backend Development learning journey**.

The project focuses on understanding how authentication and authorization work internally rather than treating Spring Security as a black box.

---

## 👨‍💻 Author

### Ravi Ranjan

**Backend Developer | Java | Spring Boot | Spring Security**

<p align="left">
  <a href="https://github.com/Ravi29102004">
    <img src="https://img.shields.io/badge/GitHub-Ravi29102004-black?style=for-the-badge&logo=github" />
  </a>
</p>

---

<p align="center">
  ⭐ If you found this project useful, consider giving it a star!
</p>

<p align="center">
  <b>Built with ☕ Java + 🌱 Spring Boot + 🔐 Spring Security</b>
</p>

Use environment variables or secure secret management for sensitive configuration.
