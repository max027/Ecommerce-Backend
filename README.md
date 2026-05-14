# 🛒 E-Commerce Backend

A robust and scalable e-commerce backend built with **Java + Spring Boot** and **MySQL**, featuring secure authentication using **JWT/OAuth**.

---

## 🚀 Tech Stack

| Layer        | Technology          |
|--------------|---------------------|
| Language     | Java 17+            |
| Framework    | Spring Boot 3.x     |
| Database     | MySQL               |
| Auth         | JWT / OAuth 2.0     |
| Build Tool   | Maven / Gradle      |
| API Style    | REST                |

---

## 📁 Project Structure

```
ecommerce-backend/
├── src/
│   ├── main/
│   │   ├── java/com/ecommerce/
│   │   │   ├── config/          # Security & app configuration
│   │   │   ├── controller/      # REST API controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── entity/          # JPA entities
│   │   │   ├── exception/       # Custom exception handling
│   │   │   ├── repository/      # JPA repositories
│   │   │   ├── security/        # JWT filters & auth logic
│   │   │   └── service/         # Business logic
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-dev.properties
│   └── test/
├── pom.xml
└── README.md
```

---

## ⚙️ Prerequisites

- Java 17 or higher
- MySQL 8.0+
- Maven 3.8+ or Gradle 7+
- An IDE like IntelliJ IDEA or VS Code

---

## 🛠️ Setup & Installation

### 1. Clone the repository

```bash
git clone https://github.com/max027/Ecommerce-Backend.git
cd ecommerce-backend
```

### 2. Configure the database

Create a MySQL database:

```sql
CREATE DATABASE ecommerce_db;
```

### 3. Update `application.properties`



### 4. Build and run

```bash
# Using Maven
mvn clean install
mvn spring-boot:run

# Using Gradle
gradle build
gradle bootRun
```

The server will start at `http://localhost:8080`.

---

## 🔐 Authentication

This project uses **JWT (JSON Web Tokens)** for stateless authentication.

### Auth Endpoints

| Method | Endpoint              | Description         | Access  |
|--------|-----------------------|---------------------|---------|
| POST   | `/api/auth/register`  | Register a new user | Public  |
| POST   | `/api/auth/login`     | Login & get token   | Public  |
| POST   | `/api/auth/refresh`   | Refresh JWT token   | Private |
| POST   | `/api/auth/logout`    | Logout user         | Private |

### How it works

1. User registers or logs in → receives a **JWT access token**
2. Token is sent in the `Authorization` header on subsequent requests:
   ```
   Authorization: Bearer <your_token>
   ```
3. Spring Security validates the token on every protected route

---

## 📬 API Overview

All endpoints are prefixed with `/api`.

> Protected routes require a valid `Authorization: Bearer <token>` header.

---

## 🌍 Environment Variables

| Variable              | Description                        |
|-----------------------|------------------------------------|
| `DB_URL`              | MySQL connection URL                |
| `DB_USERNAME`         | MySQL username                     |
| `DB_PASSWORD`         | MySQL password                     |
| `JWT_SECRET`          | Secret key for signing JWT tokens  |
| `JWT_EXPIRATION`      | Token expiry time in milliseconds  |

---

## 🧪 Running Tests

```bash
# Maven
mvn test

# Gradle
gradle test
```

---

## 📦 Building for Production

```bash
# Maven
mvn clean package -DskipTests
java -jar target/ecommerce-backend-0.0.1-SNAPSHOT.jar

# Gradle
gradle bootJar
java -jar build/libs/ecommerce-backend-0.0.1-SNAPSHOT.jar
```

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

> Built with ❤️ using Spring Boot