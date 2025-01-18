# Desktop app "DeliveryService"
University course project on topic "Client-server desktop application to support the company's business processes"
## Overview

This project is a client-server Java desktop application. The system is designed to streamline the management and processing of data for a delivery company's business operations. Through an intuitive graphical user interface, users can interact with the application to perform CRUD operations on data stored on a remote server. The application enforces role-based access control to ensure data security and integrity.
## Technical Stack
**Programming Language:** Java <br />
**GUI Framework:** JavaFX <br />
**Database:** JDBC, MySQL <br />
**Architecture:** MVC <br />
**Version Control:** Git

## Physical Database Model
<img src="https://github.com/user-attachments/assets/1e082890-9176-44b7-ba31-715a09c10e4b" alt="image" width="600"/>

### SQL-script to create database
Below is the SQL script used to create the database schema for the application.

<details>
<summary>Show SQL Script</summary>

```sql
CREATE TABLE users(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    login VARCHAR(16) UNIQUE NOT NULL,
    password VARCHAR(256) NOT NULL,
    accesslevel INT UNSIGNED NOT NULL
);

CREATE TABLE delivery_centers(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(70) UNIQUE NOT NULL,
    address VARCHAR(90) NOT NULL
);

CREATE TABLE clients(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    number VARCHAR(11) NOT NULL,
    address VARCHAR(80) NOT NULL,
    nearest_dc_id INT UNSIGNED,
    user_id INT UNSIGNED,
    FOREIGN KEY (nearest_dc_id) REFERENCES delivery_centers(id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE packages(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    type_of_delivery BOOLEAN NOT NULL,
    weight VARCHAR(16) NOT NULL,
    status VARCHAR(30) NOT NULL,	
    date_start DATE,
    date_end DATE,
    courier_id INT UNSIGNED,
    sender_id INT UNSIGNED,
    recipient_id INT UNSIGNED,
    departcenter_id INT UNSIGNED,
    FOREIGN KEY (courier_id) REFERENCES couriers(id),
    FOREIGN KEY (sender_id) REFERENCES clients(id) ON DELETE CASCADE,
    FOREIGN KEY (recipient_id) REFERENCES clients(id) ON DELETE CASCADE,
    FOREIGN KEY (departcenter_id) REFERENCES delivery_centers(id),
    FOREIGN KEY (receivingcenter_id) REFERENCES delivery_centers(id)
);

CREATE TABLE couriers(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(40) NOT NULL,
    number VARCHAR(11) NOT NULL,
    delivery_center_id INT UNSIGNED,
    user_id INT UNSIGNED,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (delivery_center_id) REFERENCES delivery_centers(id)
);

```
</details>

## Getting started
1. **Clone/Download the Repository**  
```bash
   git clone https://github.com/dr1ble/DeliveryService_courseproject.git
```
2. **Set Up MySQL Database**
- Download and install MySQL if you haven't already.
- Create a new database and import the schema provided above 
3. **Configure Database Connection** <br />
Enter your database connection details (URL, username, and password) in the `Models/DBConnection.java` file.
4. **Build and Run the Application** <br />
Open the project in your IDE, then build and run the application.
 <br />For example, in IntelliJ IDEA, click on Run > Run 'Application'.
5. **Access the Application** <br />
Once the application is running create a new user from the registration screen, then login.

### Administrator Access

To log in as an administrator, you need to create a user in the database with the appropriate access level. 
Follow these steps:

**Create an Administrator User**:  
   Insert a new user record in the `users` table with an `accesslevel` set to a value that represents an administrator (accesslevel = 3).
   Example SQL query:
   ```sql
   INSERT INTO users (login, password, accesslevel) 
   VALUES ('admin', 'your_admin_password', 3);
   ```
After creating the administrator user, you will be able to log in as an admin and access the full functionality of the application. As an administrator, you can create new users with any access level, including regular users or other administrators.

#### Access Levels
The accesslevel field determines the role and privileges of a user in the system. The following access levels are defined:
- **accesslevel = 0:** Regular User (basic access)
- **accesslevel = 1:** Courier (can manage delivery-related tasks)
- **accesslevel = 2:** Manager (can manage deliveries and users)
- **accesslevel = 3:** Administrator (full access to the system)

## App walkhrough examples
### User's journey
<details>
<summary>Show user journey</summary>
    
#### Login & Register
<div style="display: flex; justify-content: center;">
    <img src="https://github.com/user-attachments/assets/5be99594-7487-454c-b1cb-896554dfda64" alt="Login Screen" width="400" height="300"/>
    <img src="https://github.com/user-attachments/assets/d2be4a12-7647-43fb-aa0d-22a84d8d9d1e" alt="Register Screen" width="400" height="300" style="margin-left: 50px;"/>
</div>

#### User dashboard & Profile 
<div style="display: flex; justify-content: center;">
    <img src="https://github.com/user-attachments/assets/ffa13e15-2ba0-470f-ad56-919980675006" alt="User home Screen" width="400" height="300"/>
    <img src="https://github.com/user-attachments/assets/3eb1a3a3-1df6-4e7b-b133-6ea4d668ac57" alt="Register Screen" width="400" height="300" style="margin-left: 50px;"/>
</div>

#### Change User's data & Send package 
<div style="display: flex; justify-content: center;">
    <img src="https://github.com/user-attachments/assets/19011532-959d-4355-b0d0-e75e74b0354d" alt="Change User's data screen" width="400" height="300"/>
    <img src="https://github.com/user-attachments/assets/4ca66271-e47c-473b-88a2-9c0a0dab0abf" alt="Send package Screen" width="400" height="300" style="margin-left: 50px;"/>
</div>

#### Result of creating a dispatch request & My shipments 
<div style="display: flex; justify-content: center;">
    <img src="https://github.com/user-attachments/assets/228ad790-9ecb-4538-a892-3709ce8794f5" alt="Result screen" width="400" height="300"/>
    <img src="https://github.com/user-attachments/assets/64ef8c27-91ba-498d-83b7-d1a860ee389b" alt="Send package Screen" width="400" height="300" style="margin-left: 50px;"/>
</div>
</details>


### Admin's journey
<details>
<summary>Show admin journey</summary>
    
#### Admin dashboard & Control Users table 
<div style="display: flex; justify-content: center;">
    <img src="https://github.com/user-attachments/assets/7a8f2f97-4ad5-4ceb-a076-1adda4fe4a4b" alt="Admin dashboard Screen" width="400" height="300"/>
    <img src="https://github.com/user-attachments/assets/733cdcc2-cce5-49da-80fe-a2f6b35a1c18" alt="Control Users table Screen" width="400" height="300" style="margin-left: 50px;"/>
</div>

#### Add specific user & Result of adding specific user 
<div style="display: flex; justify-content: center;">
    <img src="https://github.com/user-attachments/assets/71a393eb-7cb8-4fca-b56f-89482d77916f" alt="Add specific user Screen" width="400" height="300"/>
    <img src="https://github.com/user-attachments/assets/73726ffc-c501-4f52-96c0-48d6c366f85e" alt="Result of adding specific user Screen" width="400" height="300" style="margin-left: 50px;"/>
</div>

#### Result of adding user in Users table & Result of adding user in Couriers table
<div style="display: flex; justify-content: center;">
    <img src="https://github.com/user-attachments/assets/f0c52eb9-7c53-4a51-aa5a-4c0957462b97" alt="Result of adding user in Users screen" width="400" height="300"/>
    <img src="https://github.com/user-attachments/assets/b373943d-0547-4900-bb03-d3853fdde3d5" alt="Send package Screen" width="400" height="300" style="margin-left: 50px;"/>
</div>
</details>
