Food Delivery Backend API


Comprehensive RESTful backend for a multi-role food delivery platform built with Spring Boot. Features JWT authentication, role-based access control, Razorpay payments, and complete order lifecycle management across customers, restaurant owners, and delivery partners.​

    🚀 Project Overview


This backend powers a full-featured food delivery system supporting three user roles with secure APIs for order placement, restaurant management, delivery tracking, and payment processing. Designed for production-ready scalability with clean Spring Boot architecture, JPA entities, and comprehensive exception handling.​

    ✨ Core Features
Multi-Role Authentication: Secure JWT login for Customer, Restaurant Owner, Delivery Partner roles with Spring Security RBAC​

Complete Order Lifecycle: Browse restaurants → Place order → Payment → Assign delivery → Track → Delivered​

Razorpay Payment Integration: Secure online payments with webhook verification​

Real-time Restaurant Management: Menu CRUD, availability toggle, order acceptance​

Delivery Partner Dashboard: Order assignment, pickup confirmation, status updates​

Advanced Features: Order history, ratings, search/filter, email notifications


   | Category       | Technologies                                              |
| -------------- | --------------------------------------------------------- |
| Framework      | Spring Boot 3.x, Spring Security, Spring Data JPA github​ |
| Database       | MySQL 8.0 with JPA/Hibernate ORM github​                  |
| Authentication | JWT Tokens, BCrypt Password Encoder github​               |
| Payments       | Razorpay API + Webhooks youtube​                          |
| Validation     | Bean Validation (Hibernate Validator) github​             |
| Testing        | Postman API Collection, JUnit [user-information]          |
| Documentation  | Swagger/OpenAPI (planned) codewithmurad​                  |


 Business Features Demonstrated
Customer Features
text
1. Browse restaurants & menus (filter by cuisine, rating, distance)
2. Add items to cart → Checkout with Razorpay
3. Track live order status & ETA
4. Order history & reordering
5. Rate/review restaurants
Restaurant Owner Features
text
1. Manage menu items (CRUD with images)
2. Toggle restaurant availability
3. View/accept pending orders
4. Update order preparation status
5. Sales analytics dashboard
Delivery Partner Features
text
1. View available delivery jobs
2. Accept pickup → Update en-route status
3. Mark delivery complete
4. Earnings dashboard

       📋 API Endpoints Overview
text
/auth:
  POST /register - Role-based registration
  POST /login - JWT token generation

/restaurants:
  GET / - List restaurants (filter, search)
  GET /{id} - Restaurant details + menu
  POST /menu - Add menu items (owner only)

/orders:
  POST / - Place order + payment
  GET /my - User order history
  PUT /{id}/status - Update status (role-specific)

/payments:
  POST /razorpay/order - Create payment order
  POST /razorpay/verify - Payment verification webhook

/delivery:
  GET /available - Available jobs (delivery partner)
  PUT /{orderId}/pickup - Confirm pickup
  PUT /{orderId}/delivered - Mark complete

  
    🚀 Quick Start
    # 1. Clone & Setup
    git clone https://github.com/yaseenpatelsd/food-delivery-backend-springboot.git
    cd food-delivery-backend-springboot

    # 2. Database Setup
    # Create MySQL database: food_delivery_db
    # Update application.properties with DB credentials & Razorpay keys

    # 3. Run Application
    ./mvnw clean spring-boot:run

    # 4. Test APIs
    # Base URL: http://localhost:8080
    # Import Postman collection (coming soon)
    🗄 Database Schema

    
sql
  
    Core Entities:
    ├── users (id, email, password, role: CUSTOMER/RESTAURANT/DELIVERY)
    ├── restaurants (id, owner_id, name, address, is_active)
    ├── menu_items (id, restaurant_id, name, price, image_url)
    ├── orders (id, customer_id, restaurant_id, status, total_amount)
    ├── order_items (order_id, menu_item_id, quantity, price)
    ├── payments (id, order_id, razorpay_order_id, status)
    └── deliveries (id, order_id, delivery_partner_id, status)

    
    🔒 Security Implementation


JWT Authentication: Stateless token-based auth with refresh tokens​

Role-Based Access: @PreAuthorize annotations on controllers

Input Validation: @Valid + Custom validators

SQL Injection Protection: JPA parameterized queries

📊 Production Features
text
✅ Global Exception Handler
✅ Custom Response DTOs  
✅ Pagination & Filtering
✅ Email Service Integration (planned)
✅ File Upload (restaurant images)
✅ Swagger API Documentation (planned)
✅ Docker Support (planned)
✅ CI/CD Pipeline (planned)


    🧪 Testing Strategy
API Testing: Postman collection 


    📈 Scalability Considerations
Database: MySQL Master-Slave replication ready

Caching: Redis integration planned for menus

Microservices: Modular design for future split

Load Balancing: Spring Cloud LoadBalancer ready

    🎯 Interview Highlights
What this project demonstrates:

Full-stack Backend Expertise: Complete CRUD + Business Logic

Security Mastery: JWT + RBAC + OWASP Top 10 protections

Payment Integration: Real-world Razorpay implementation

Database Design: Normalized schema with relationships

Clean Architecture: Service → Repository → Entity pattern

Production Ready: Exception handling, validation, DTOs

Key Spring Boot Skills Shown:

text
• Spring Security + JWT Configuration
• JPA/Hibernate Advanced Mapping  
• REST Controller Best Practices
• Custom Annotations & Validators
• Global Exception Handling
• Async Processing (Order Notifications)
