# BuildWithRani — E-commerce User Flow 

A Spring Boot backend and MySQL database that implements a **secure authentication system** for an e-commerce application.

---

## ⚙️ Tech Stack

- ☕ [**Java**](https://www.oracle.com/java/) – core programming language for backend development  
- 🌱 [**Spring Boot**](https://spring.io/projects/spring-boot) – framework for building production-ready REST APIs  
- 🗄️ [**MySQL**](https://www.mysql.com/) – relational database for persistent data storage  
- 🧬 [**JPA / Hibernate**](https://hibernate.org/) – ORM for database interaction and entity management  
- 🔐 [**BCrypt**](https://spring.io/projects/spring-security) – secure password hashing  
- 🔑 [**JWT (JSON Web Tokens)**](https://jwt.io/) – stateless authentication mechanism  
- 📦 [**Maven**](https://maven.apache.org/) – build and dependency management tool

---

## ✨ Features

- User signup and login APIs
- Secure password hashing using **BCrypt**
- JWT token generation on authentication
- Persistent user storage with MySQL

---

## 📁 Project Structure

```text
src/main/java/com/buildwithrani/ecommerce
├─ controller/        # REST API endpoints
├─ service/           # logic
├─ dto/               # Request/response objects
├─ model/             # JPA entities
├─ repository/        # Database access
├─ security/          # JWT utilities
├─ config/            # Application configuration
└─ EcommerceApplication.java
```

## 🔧 Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/rangari-rani/buildwithrani-springboot-ecommerce-api.git
```

### 2. Configure local environment
Create:

```text
src/main/resources/application-local.properties
```

Example:

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/buildwithrani_ecommerce
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

jwt.secret=YOUR_SECRET_KEY
jwt.expiration=86400000
```

### 3. Start the development server
 Navigate to: `src/main/java/com/buildwithrani/ecommerce/EcommerceApplication.java`  
 Click the **Run ▶️ button**  
> App runs at:
🌐 http://localhost:8080

---

## ✨ Part of BuildWithRani

This project is part of the **BuildWithRani** learning series.

📖 Implementation details:  [buildwithrani.com](https://buildwithrani.com)

---

## 📬 Contact

Connect with me on **[LinkedIn – Rani Rangari](https://linkedin.com/in/rani-rangari)**  

⭐ If you found this project helpful, consider giving it a star!
