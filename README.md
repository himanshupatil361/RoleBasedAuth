Role-Based Authentication System

# A secure backend application built using Spring Boot, Spring Security, JWT Authentication, Hibernate, and MySQL. The system implements role-based access control (RBAC) with three user roles: Admin, Teacher, and Student.

# coding instructions from line 62
# Features :
 Admin-
    Create, view, update, and delete Teachers
    Create, view, update, and delete Students
    View all users and their roles
    
Teacher-
    View own profile
    Update own password
    Create, view, update, and delete Students
    
Student-
    View own profile
    Update own password
    
# Technologies Used :
    Java 17
    Spring Boot
    Spring Security
    JWT (JSON Web Token)
    Spring Data JPA (Hibernate)
    MySQL
    
# Security Features:
    Password encryption using BCrypt
    Role-based authorization
    Protected REST APIs
    Secure password update functionality

# Project Structure:
    Controller Layer – Handles API requests
    Service Layer – Business logic
    Repository Layer – Database operations
    Entity Layer – Database models
    Security Layer – Authentication and authorization

# Database Design :
User Entity-
    Username
    Password
    Role
Teacher Entity-
    Teacher Details
    One-to-One relationship with User
    
Student Entity-
    Student Details
    One-to-One relationship with User
    
# Authentication Flow :
   User enters username and password.
   Spring Security authenticates the credentials.
   Password is verified using BCrypt encryption.
   Access is granted based on the assigned role.
   Users can access only the resources permitted for their role.

   # Coding part :
  # Create the First Admin User = Since the application does not create First Admin account automatically, insert an Admin user manually into the users table.
  
INSERT INTO users(username,password,role)VALUES('admin','$2a$12$W0mMtf4S8l6F6Q0L8WQ6UuH0n3R6K4YxK6dR8QhR7K8mVwY9fP8sW','ADMIN');
 username = admin and password = admin@123
 
 # After creating first admin we can also create another admin :
  - Go to authorization: give the admin username and password
  - Go to bosy section and give json
  -  eg: {
           "username":"admin2",
           "password" : "admin@123"
         }
  - The role is set from in user service layer

# Teacher creation by admin
- same steps admin username n pass
- json body will be differnet , username should be same. nested json for user table (for authentication)
-  create eg : {  
         "username" :" teacher1",
         "subject":"subject",
         "user": {
                    "username":"teacher1",
                    "password" : "admin@123"
         }
       }
# Teacher change their own pass
- login username n pass of teacher, use teacher api patch
-  {  
         "password" : "give_password"
   }
- 
# Student creation by admin or teacher
- login username n pass of adminn / teacher
- -  create eg : {  
         "username" :" student1",
         "course":"subject",
          "age" :"21",
         "user": {
                    "username":"student1",
                    "password" : "student_pass"
         }
       }
# Student change their own pass
- login username n pass of student, use student api patch
-  {  
         "password" : "give_password"
   }
