# Employee Management System – JavaFX + Spring Boot (Hybrid Desktop Application)

---

## 🎯 Objective

The **Employee Management System** is a desktop-based application developed using **JavaFX** integrated with a **Spring Boot backend**.

The system allows administrators to manage employee records efficiently including adding, viewing, updating, and deleting employee information while maintaining a clean layered architecture.

---

## 🛠 Technologies Used

- **Language:** Java
- **Frontedn:** JavaFX
- **Backend:** Spring Boot
- **Database:** MySQL
- **ORM:** Spring Data JPA / Hibernate
- **Build Tool:** Maven
- **UI Styling:** JAvaFx CSS 
- **Architecture:** Controller -> Service -> Repository

---

## User Roles

## Admin

- Login authentication
- Add employee records
- View employee details
- Update employee information
- Delete employee records
- Print employee data

---

## ✨ Features

### Authentication

- Admin login validation
- Database-based credential verification

### Employee Management

- Add Employee
- View Employees
- Search Employee by ID
- Update Employee Details
- Delete Employee Records
- Auto-refresh Employee IDs

### User Interface

- Splash Screen
- Animated Screen Transitions
- CSS Styled UI
- Confirmation Dialogs
- Printable Employee Table

---

## 📡 Backend API Endpoints

### Admin APIS

- **POST** `/api/admin/login` - Admin Login 

### Employee APIs

- **POST** `/api/employees/add` - Add employee
- **GET** `/api/employees/all` - Get all employees
- **GET** `/api/employees/ids` - Get employee IDs
- **GET** `/api/employees/get/{empId}` - Get employee by ID
- **PUT** `/api/employees/update` - Update employee
- **DELETE** `/api/employees/delete/{empId}` - Delete employee

---

## 🗄 Data Model

### Employee
empId | name | phone | email | designation | salary | education

### Admin
username | password

---

### ✅ Prerequisites

Make sure the following software is installed:

- Java JDK 17+
- MySQL Server
- Maven
- IntelliJ IDEA / Eclipse IDE
- JavaFX SDK (Configured)

---

### ✅ Prerequisites

Make sure the following software is installed:

- Java JDK 17 or above  
- MySQL Server  
- IntelliJ IDEA / Eclipse IDE  
- Maven  
- JavaFX SDK  

---

## 🚀 How to Run the Project

Follow the steps below to run the Employee Management System on your local machine.

---

### 📥 Step 1: Clone the Repository

```bash
git clone https://github.com/your-username/Employee-Management-System.git
```

---

### 📂 Step 2: Import Project into IDE

1. Open IntelliJ IDEA / Eclipse IDE  
2. Click on **File → Open / Import**   
3. Choose the cloned project folder  

---

### 🗄 Step 3: Setup MySQL Database

```sql
CREATE DATABASE managementsystemt;
```

---

### ⚙ Step 4: Configure Database Connection

```properties
spring.datasource.url = "jdbc:mysql://localhost:3306/managementsystemt"
spring.datasource.username= "root"
spring.datasource.password = "your_password"
```

---

### 🔌 Step 5: Load Maven Dependencies

- Right Click Project → Maven → Update Project  

---

### ▶ Step 6: Run the Application

`Splash.java`

---

### 🔐 Default Admin Login

```
Username: "username"
Password: "password"
```

---

### ✅ Application Workflow

1. Login using admin credentials  
2. Add Employee details  
3. View employee records  
4. Update employee information  
5. Delete employee records  