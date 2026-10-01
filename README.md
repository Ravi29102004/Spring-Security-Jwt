# Spring Security JWT

A Spring Boot project demonstrating authentication and authorization using Spring Security and JSON Web Tokens (JWT).

## 🚀 Overview

This project implements a secure backend authentication flow using Spring Security.

The application demonstrates:

- User authentication
- JWT-based authentication
- Role-based authorization
- Password encryption
- Security Filter Chain
- Custom JWT authentication filter
- SecurityContext
- Protected REST APIs
- Public authentication endpoints
- Admin-only endpoints
- Stateless security

## 🛠️ Tech Stack

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL
- JWT
- Maven

## 🔐 Security Architecture

```text
Client
   |
   | Login Request
   ↓
Authentication Controller
   |
   ↓
AuthenticationManager
   |
   ↓
UserDetailsService
   |
   ↓
Database
   |
   ↓
Password Verification
   |
   ↓
JWT Generation
   |
   ↓
Client
```

For subsequent requests:

```text
Client
   |
   | Authorization: Bearer <JWT>
   ↓
JWT Authentication Filter
   |
   ↓
Extract JWT
   |
   ↓
Validate JWT
   |
   ↓
Extract Username
   |
   ↓
Load UserDetails
   |
   ↓
Create Authentication
   |
   ↓
SecurityContext
   |
   ↓
Authorization
   |
   ↓
Controller
```

## 🔑 Authentication vs Authorization

### Authentication

Determines:

> Who is the user?

Example:

```text
Username + Password
        ↓
AuthenticationManager
        ↓
Authenticated User
```

### Authorization

Determines:

> What is the user allowed to access?

Example:

```text
ADMIN → /api/admin/**
USER  → User-protected endpoints
```

## 🛡️ Security Configuration

The application contains rules such as:

```java
.requestMatchers("/api/admin/**").hasRole("ADMIN")
.requestMatchers("/api/auth/**").permitAll()
.anyRequest().authenticated()
```

Meaning:

- `/api/auth/**` → Public
- `/api/admin/**` → ADMIN role required
- Other endpoints → Authentication required

## 🔐 JWT Flow

```text
Login
  ↓
Username + Password
  ↓
AuthenticationManager
  ↓
UserDetailsService
  ↓
PasswordEncoder
  ↓
Authentication Success
  ↓
JWT Generated
  ↓
Client stores JWT
```

For protected requests:

```text
Authorization: Bearer <JWT>
```

The JWT filter validates the token and establishes the authenticated user in the Spring Security context.

## 📚 Concepts Covered

- Spring Security
- AuthenticationManager
- UserDetailsService
- PasswordEncoder
- SecurityFilterChain
- OncePerRequestFilter
- JWT
- SecurityContextHolder
- Authentication
- Authorization
- Roles and Authorities
- Stateless Authentication
- CSRF
- CORS
- HTTP 401 and 403

## 📁 Project Structure

```text
src
└── main
    ├── java
    │   └── com.example.security
    │       ├── Config
    │       ├── Controller
    │       ├── Entity
    │       ├── Repository
    │       ├── Security
    │       └── Service
    │
    └── resources
        └── application.properties
```

> Package names may vary depending on the project structure.

## ⚙️ Setup

### 1. Clone the repository

```bash
git clone https://github.com/Ravi29102004/Spring-Security-JWT.git
```

### 2. Open the project

Open the project in IntelliJ IDEA.

### 3. Configure MySQL

Create a MySQL database and configure the database connection using environment variables or local configuration.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=${DB_PASSWORD}
```

### 4. Configure JWT Secret

Do not hardcode production secrets.

Example:

```properties
jwt.secret=${JWT_SECRET}
```

Set the environment variable before running the application.

### 5. Run the application

Using Maven:

```bash
./mvnw spring-boot:run
```

or run the main Spring Boot application from IntelliJ IDEA.

## 🧪 API Testing

The APIs can be tested using:

- Postman
- IntelliJ HTTP Client

Typical flow:

```text
1. Register
2. Login
3. Receive JWT
4. Add JWT to Authorization header
5. Access protected endpoint
```

Example:

```http
Authorization: Bearer <your-jwt-token>
```

## 📌 Learning Outcome

This project helped in understanding how Spring Security processes an HTTP request, how authentication is established, how JWT is validated, and how authorization is applied to protected endpoints.

## 👨‍💻 Author

**Ravi Ranjan**

B.Tech – Electronics & Communication Engineering

Interested in:

- Java Backend Development
- Spring Boot
- Spring Security
- DSA
- System Design
- IoT & ECE Integration

## ⚠️ Security Note

Never commit:

- Database passwords
- JWT secrets
- API keys
- Access tokens
- Private credentials

Use environment variables or secure secret management for sensitive configuration.
