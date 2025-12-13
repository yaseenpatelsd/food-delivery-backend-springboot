# 🍔 Food Delivery Backend API

Comprehensive RESTful backend for a **multi-role food delivery platform** built with **Spring Boot**. The project demonstrates real-world backend engineering concepts including **JWT authentication**, **role-based access control**, **payment integration**, and **complete order lifecycle management** across customers, restaurant owners, and delivery partners.

This repository focuses on **clean architecture, security, and business logic**, making it suitable for internship-level backend roles.

---

## 🚀 Project Overview

This backend powers a food delivery system supporting **three distinct user roles** with secure APIs for:

* order placement and tracking
* restaurant and menu management
* delivery partner assignment
* online payment processing

The application is structured using standard **Spring Boot layered architecture (Controller → Service → Repository)** with JPA/Hibernate ORM and centralized exception handling.

---

## ✨ Core Features

* **Multi-Role Authentication**
  JWT-based login with **Spring Security** for:

  * Customer
  * Restaurant Owner
  * Delivery Partner

* **Complete Order Lifecycle**
  Browse restaurants → Add to cart → Place order → Pay → Assign delivery → Delivered

* **Razorpay Payment Integration**
  Payment order creation and signature verification using Razorpay APIs

* **Restaurant Management**
  Menu CRUD, availability toggle, order acceptance

* **Delivery Partner Flow**
  Order assignment, pickup confirmation, delivery completion

* **Production-Ready Practices**
  DTO-based responses, validation, pagination, global exception handling

---

## 🛠 Tech Stack

| Category       | Technologies                                      |
| -------------- | ------------------------------------------------- |
| Framework      | Spring Boot 3.x, Spring Security, Spring Data JPA |
| Database       | MySQL 8.0, Hibernate ORM                          |
| Authentication | JWT, BCrypt Password Encoder                      |
| Payments       | Razorpay API                                      |
| Validation     | Bean Validation (Hibernate Validator)             |
| Testing        | Postman API testing                               |

---

## 👥 Business Features by Role

### Customer

1. Browse restaurants & menus
2. Place orders and pay online
3. Track order status
4. View order history

### Restaurant Owner

1. Manage menu items (CRUD)
2. Toggle restaurant availability
3. View and accept orders
4. Update preparation status

### Delivery Partner

1. View available delivery jobs
2. Accept pickup requests
3. Update delivery status

---

## 📋 API Endpoints Overview

### Authentication

```
POST /auth/register   → Role-based registration
POST /auth/login      → JWT token generation
```

### Restaurants

```
GET  /restaurants           → List restaurants
GET  /restaurants/{id}      → Restaurant details & menu
POST /restaurants/menu      → Add menu items (Owner only)
```

### Orders

```
POST /orders                → Place order & initiate payment
GET  /orders/my             → User order history
PUT  /orders/{id}/status    → Update order status (role-specific)
```

### Payments

```
POST /payments/razorpay/order   → Create Razorpay order
POST /payments/razorpay/verify  → Verify payment signature
```

### Delivery

```
GET  /delivery/available            → Available delivery jobs
PUT  /delivery/{orderId}/pickup     → Confirm pickup
PUT  /delivery/{orderId}/delivered  → Mark delivered
```

---

## 🚀 Quick Start

### 1. Clone Repository

```bash
git clone https://github.com/yaseenpatelsd/food-delivery-backend-springboot.git
cd food-delivery-backend-springboot
```

### 2. Database Setup

* Create MySQL database: `food_delivery_db`
* Update `application.properties` with:

  * DB credentials
  * Razorpay key & secret

### 3. Run Application

```bash
./mvnw clean spring-boot:run
```

Base URL: `http://localhost:8080`

---

## 🗄 Database Schema (Simplified)

Core entities:

* users
* restaurants
* menu_items
* orders
* order_items
* payments
* deliveries

(Refer to `/docs` for ER diagram and API testing resources.)

---

## 🔒 Security Implementation

* JWT-based stateless authentication
* Role-based access control using `@PreAuthorize`
* Input validation using `@Valid`
* JPA parameterized queries to prevent SQL injection

---

## 🧪 Testing

* API testing performed using **Postman**
* Postman collection available in the `/docs` directory

---

## 🎯 What This Project Demonstrates

* Real-world backend development with Spring Boot
* Secure authentication & authorization
* Payment gateway integration
* Database design with entity relationships
* Clean separation of concerns using DTOs and services
* Error handling and validation suitable for production systems

---

## 🚧 Future Enhancements (Not Yet Implemented)

* Swagger / OpenAPI documentation
* Dockerization
* CI/CD pipeline
* Redis caching
* Email notifications

---

## 👨‍💻 Author

**Yaseen Patel**
Backend Developer (Spring Boot)

---

⭐ If you find this project useful, feel free to star the repository!
