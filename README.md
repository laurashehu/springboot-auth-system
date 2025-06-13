# Spring Boot Auth System 🔐

A simple user authentication system built with **Spring Boot**, **JWT**, and **Gradle**. This backend allows users to register and log in securely, enforcing password strength and ensuring unique emails and usernames.

---

## 🚀 Features

- ✅ Register with unique **username** and **email**
- ✅ Login with **JWT** token
- ✅ Password validation:
  - Minimum 8 characters
  - At least one special character (e.g. `@`, `#`, `.`, etc.)
- ✅ Stateless JWT authentication
- ✅ Built using **Gradle** and **MySQL**
- ❌ No roles or admin separation — just standard users

---

## 📬 API Endpoints

| Method | Endpoint         | Description         |
|--------|------------------|---------------------|
| POST   | `/auth/signup`   | Register a new user |
| POST   | `/auth/login`    | Authenticate user   |

### 🔧 Signup Request Example

```json
POST http://localhost:8081/auth/signup
Content-Type: application/json

{
  "username": "laura123",
  "email": "laura@example.com",
  "password": "StrongPass@1"
}

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Security**
- **JWT (JSON Web Tokens)**
- **Gradle** (build tool)
- **MySQL** (with MySQL Workbench)
- **Postman** (for API testing)

```

## 📸 Screenshots

Here are some example API tests using Postman:

### 📝 User Signup - Success
![Signup Success](https://github.com/user-attachments/assets/002d8f1d-5a02-46b8-a18a-b5ba0efaa7dd)

### 🔐 User Login - Token Returned
![Login Success](https://github.com/user-attachments/assets/32611b18-014b-42f6-9967-d76bda31a071)

### ❌ Invalid Password Format (Signup)
![Password error](https://github.com/user-attachments/assets/0e026f5a-a3f0-489d-afd2-713afbcb4f56)

### ❌ Duplicate Email or Username
![Duplicate username error](https://github.com/user-attachments/assets/72abcd77-6b7f-468b-b85f-c6f13062f76b)
![Duplicate email error](https://github.com/user-attachments/assets/6ac31dd0-1050-4767-b023-d9bd0c28c981)





