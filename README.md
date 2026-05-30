# Role-Based Authentication System

A secure backend application built using Spring Boot, Spring Security, Hibernate, and MySQL. The system implements Role-Based Access Control (RBAC) with three user roles: Admin, Teacher, and Student.

## Features

### Admin

* Create, view, update, and delete Teachers
* Create, view, update, and delete Students
* Create additional Admin accounts
* View all users and their roles

### Teacher

* View own profile
* Update own password
* Create, view, update, and delete Students

### Student

* View own profile
* Update own password

## Technologies Used

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA (Hibernate)
* MySQL
* Maven

## Security Features

* Password encryption using BCrypt
* Role-based authorization
* Protected REST APIs
* Secure password update functionality

## Project Structure

* Controller Layer – Handles API requests
* Service Layer – Contains business logic
* Repository Layer – Performs database operations
* Entity Layer – Represents database tables
* Security Layer – Handles authentication and authorization

## Database Design

### User Entity

* Username
* Password
* Role

### Teacher Entity

* Teacher details
* One-to-One relationship with User

### Student Entity

* Student details
* One-to-One relationship with User

## Authentication Flow

1. User enters username and password.
2. Spring Security authenticates the credentials.
3. Password is verified using BCrypt encryption.
4. Access is granted based on the assigned role.
5. Users can access only the resources permitted for their role.

---

## Initial Setup

### Create the First Admin User

The application does not create the first Admin account automatically. Insert an Admin user manually into the `users` table before using the application.

```sql
INSERT INTO users(username,password,role)
VALUES(
'admin',
'$2a$12$W0mMtf4S8l6F6Q0L8WQ6UuH0n3R6K4YxK6dR8QhR7K8mVwY9fP8sW',
'ADMIN'
);
```

Default Credentials:

```text
Username: admin
Password: admin@123
```

---

## Create Another Admin

1. Authenticate using an existing Admin account.
2. Use the Admin creation endpoint.
3. Send the following JSON request body:

```json
{
  "username": "admin2",
  "password": "admin@123"
}
```

The role is assigned automatically in the service layer.

---

## Create Teacher (Admin Only)

Authenticate using Admin credentials and send:

```json
{
  "username": "teacher1",
  "subject": "Java",
  "user": {
    "username": "teacher1",
    "password": "teacher@123"
  }
}
```

**Note:** Teacher username and User username should be the same.

---

## Teacher Password Change

Authenticate using Teacher credentials and call the Teacher password update endpoint.

```json
{
  "password": "newPassword"
}
```

---

## Create Student (Admin or Teacher)

Authenticate using Admin or Teacher credentials and send:

```json
{
  "username": "student1",
  "course": "Information Technology",
  "age": 21,
  "user": {
    "username": "student1",
    "password": "student@123"
  }
}
```

**Note:** Student username and User username should be the same.

---

## Student Password Change

Authenticate using Student credentials and call the Student password update endpoint.

```json
{
  "password": "newPassword"
}
```

---

## Learning Outcomes

* Spring Security Authentication and Authorization
* Role-Based Access Control (RBAC)
* Password Encryption with BCrypt
* REST API Development
* JPA/Hibernate Entity Relationships
* Secure Backend Development

## Author

**Himanshu Suresh Patil**

B.Sc. Information Technology (2024–25)

CGPA: 8.80
