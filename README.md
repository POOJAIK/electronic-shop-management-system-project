- ESMS - Electronic Shop management System

  A full-stack web application for managing an electronic shop - built with Spring Boot, MsSQL, and   Thymeleaf.

- Tech Stack
- Technology : Usage
1. Java 17 : Backend Language
2. Spring Boot 4.0 : Web Framework
3. MySQL 8 : Database
4. Hibernate JPA : ORM
5. Thymeleaf : Template Engine
6. Bootstrap 5 : Frontend Styling
7. Spring Security : Authentication
8. Maven : Build Tool

- Features:
1. Authentication:
- User Signup with name, email, password, address
- User Login with email and password
- Session-based authentication
- Secure Logout

2. Products
- View all products (accessible without login)
- Add new product (admin only)
- Edit existing product (admin only)
- Delete product (admin only)
- Product image URL support

3. Inventory Management
- Stock quantity tracking for each product
- Add stock to any product
- Reduce stock from any product
- Low stock alert when quantity is less than 5

4. Billing Module
- Create bill with customer name
- Select multiple products with quantity
- Auto total amount calculation
- Stock automatically reduces on bill creation
- View bill details with items and subtotal
- Delete bill (stock gets restored)
- Bill history with date and time

5. Admin Dashboard
- Total Products count card
- Total Bills count card
- Total Revenue card
- Low stock alert list
- Recent 5 bills table
- Full products management table

Project Structure:
src/main/java/com/ecommerce/esmsproject1/
│
├── controller/
│   ├── HomeController.java
│   ├── ProductsController.java
│   ├── AuthController.java
│   ├── AdminController.java
│   └── BillingController.java
│
├── entity/
│   ├── Product.java
│   ├── User.java
│   ├── Bill.java
│   └── BillItem.java
│
├── repository/
│   ├── ProductsRepository.java
│   ├── UsersRepository.java
│   ├── BillRepository.java
│   └── BillItemRepository.java
│
└── SecurityConfig.java
src/main/resources/
│
├── templates/
│   ├── fragments/
│   │   └── navbar.html
│   ├── admin/
│   │   ├── adminDashboard.html
│   │   ├── add-product.html
│   │   ├── edit-product.html
│   │   ├── bills-list.html
│   │   ├── create-bill.html
│   │   └── view-bill.html
│   ├── home.html
│   ├── products.html
│   ├── login.html
│   └── signup.html
│
└── static/
└── css/
└── style.css

How to run:
Prerequisites
- Java 17 or higher
- MySQL 8 or higher
- Maven
- IntelliJ IDEA (recommended)

Step 1: Clone the repository
git clone https://github.com/POOJAIK/electronic-shop-management-system-project

Step 2: Create Database
Open MySQL and run: CREATE DATABASE esmsproject1;

Step 3: Configure application.properties
Open `src/main/resources/application.properties` and update:
spring.datasource.url=jdbc:mysql://localhost:3306/esmsproject1
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

Step 4. Run the project
mvn spring-boot:run

Step 5. Open in browser
http://localhost:8080/

Database Tables:
Table : Description
users : Stores user login information
Products : Stores product details and stock
bills : Stores bill header information
bill_items : Stores individual items in each bill

Admin Access
URL: http://localhost:8080/admin
Note: Login required to access admin panel

Developer
Pooja
- GitHub: [@POOJAIK](https://github.com/POOJAIK)
- Project: [ESMS - Electronic Shop Management System](https://github.com/POOJAIK/electronic-shop-management-system-project)

If you found this project helpful, please give it a star!
