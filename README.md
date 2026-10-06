💚 Time Management and Wellness Monitoring System

📌 Project Overview

Time Management and Wellness Monitoring System is a Java-based web application designed to help users manage their time and monitor their wellness.

The system connects time management with wellness monitoring and provides personalized recommendations based on work hours, break hours, and physical activity.

🎯 Objectives

* Track daily work and break hours
* Monitor wellness metrics
* Calculate a wellness score
* Provide personalized wellness recommendations
* Allow administrators to configure wellness parameters
* Provide an admin dashboard with system statistics
* Manage registered users

🚀 Main Features

👤 User Features

* User Registration
* User Login
* Time Entry
* Wellness Check
* Wellness Score Calculation
* Personalized Recommendations
* User Dashboard
* Logout

👨‍💼 Admin Features

* Admin Login
* User Management
* Wellness Parameter Configuration
* Total Users Statistics
* Total Time Entries
* Total Wellness Records
* Average Wellness Score
* Performance Overview
* Registered Users Table

🛠️ Technologies Used

* Java
* Core Java
* Object-Oriented Programming
* JDBC
* Java Servlets
* MySQL
* HTML
* CSS
* Apache Tomcat
* VS Code

🏗️ Project Architecture

The project follows a simple layered architecture:

User
  ↓
HTML Web Pages
  ↓
Servlets
  ↓
Service Layer
  ↓
DAO Layer
  ↓
MySQL Database

Main Packages

com.welltrack.model
com.welltrack.dao
com.welltrack.service
com.welltrack.servlet

📁 Project Structure

TimeWellnessMonitoringSystem
│
├── src
│   └── com
│       └── welltrack
│           ├── model
│           ├── dao
│           ├── service
│           └── servlet
│
├── web
│   ├── index.html
│   ├── login.html
│   ├── register.html
│   ├── time-entry.html
│   ├── wellness.html
│   ├── admin.html
│   └── WEB-INF
│       └── web.xml
│
├── database
│
├── lib
│   ├── mysql-connector-j-26.7.0.jar
│   └── servlet-api.jar
│
└── README.md

🗄️ Database

The project uses MySQL.

Database Name

wellness_system

Main Tables

users
time_entries
wellness_metrics
recommendations
wellness_parameters

Users Table

Stores registered user information such as:

* User ID
* Name
* Email
* Password
* Role

Time Entries Table

Stores:

* Work hours
* Break hours
* Description
* Entry date
* User ID

Wellness Metrics Table

Stores:

* Activity level
* Work hours
* Break hours
* Wellness score
* Recorded date
* User ID

Recommendations Table

Stores personalized wellness recommendations and their status.

Wellness Parameters Table

Stores administrator-defined wellness limits such as:

* Maximum work hours
* Minimum break hours
* Minimum activity level

💚 Wellness Score

The system calculates a wellness score based on three major parameters:

1. Work Hours
2. Break Hours
3. Physical Activity Level

The administrator can configure the required wellness parameters from the Admin Dashboard.

The scoring system uses:

Work Hours       → 40 Points
Break Hours      → 25 Points
Activity Level   → 35 Points
Maximum Score    → 100 Points

💡 Recommendation System

The system generates recommendations according to the user’s wellness data.

Examples:

* High work hours → Take regular breaks and maintain work-life balance.
* Low break time → Increase break duration.
* Low activity → Increase physical activity.
* Good balance → Shows a positive wellness message.

👨‍💼 Admin Dashboard

The Admin Dashboard provides:

* Total number of users
* Total time entries
* Total wellness records
* Average wellness score
* Performance overview
* Registered users
* Wellness parameter configuration

The dashboard helps the administrator monitor the overall performance of the system.

🔐 Security and Access Control

The application uses session-based access control.

User

Normal users can access:

* User Dashboard
* Time Entry
* Wellness Monitoring
* Recommendations

Admin

Administrators can access:

* Admin Dashboard
* User Management
* Wellness Parameters
* Performance Statistics

Unauthorized users are prevented from accessing the Admin Dashboard.

🔑 Default Admin Account

Email: admin@gmail.com
Password: admin123

⚙️ How to Run the Project

Step 1 — Start MySQL

Make sure MySQL Server is running.

Step 2 — Create Database

Create the database:

CREATE DATABASE wellness_system;

Then create the required tables using the SQL script provided in the database folder.

Step 3 — Configure Database Connection

Open:

src/com/welltrack/dao/DBConnection.java

Set the MySQL username and password according to your local MySQL installation.

Step 4 — Compile Java Files

Compile the Java source files using the required classpath containing the JDBC and Servlet libraries.

Step 5 — Deploy on Apache Tomcat

Deploy the project inside the Apache Tomcat webapps directory.

Step 6 — Start Tomcat

Start Apache Tomcat.

Step 7 — Open the Application

Open:

http://localhost:8080/TimeWellnessMonitoringSystem/login.html

🧪 System Testing

The following features have been tested:

* User registration
* User login
* Admin login
* User dashboard
* Time entry submission
* Wellness calculation
* Recommendation generation
* Database storage
* Admin access control
* Admin statistics
* Logout functionality

📚 Core Java Concepts Demonstrated

This project demonstrates:

* Classes and Objects
* Encapsulation
* Constructors
* Inheritance through Servlet classes
* Method implementation
* Exception Handling
* Collections
* JDBC
* SQL
* Session Management
* Database Connectivity

🔌 JDBC Implementation

The application uses JDBC to connect Java with MySQL.

The DAO layer handles database operations such as:

* Insert
* Select
* Count
* Average
* User management
* Wellness data management
* Recommendation management

Prepared statements are used for database queries.

🌐 Servlet Implementation

The application uses Java Servlets for web integration.

Main servlets include:

RegisterServlet
LoginServlet
TimeEntryServlet
WellnessServlet
AdminServlet
WellnessParameterServlet
LogoutServlet

🎨 User Interface

The application uses HTML and CSS to provide a colourful and responsive interface.

Main pages include:

* Home Page
* Login Page
* Registration Page
* Time Entry Page
* Wellness Page
* User Dashboard
* Admin Dashboard

🎓 Academic Purpose

This project demonstrates the integration of:

Core Java
+
Object-Oriented Programming
+
JDBC
+
MySQL
+
Servlets
+
HTML/CSS

The project is designed as an academic implementation of a Time Management and Wellness Monitoring System.

👨‍💻 Project Information

Project Title: Time Management and Wellness Monitoring System

Project Type: Java Web Application

Backend: Java Servlets

Database: MySQL

Server: Apache Tomcat

Frontend: HTML and CSS

Architecture: Model + DAO + Service + Servlet

📌 Conclusion

The Time Management and Wellness Monitoring System provides a simple platform for tracking time usage and monitoring wellness.

It combines work hours, break time, and physical activity to calculate a wellness score and generate useful recommendations.

The Admin Dashboard provides statistics and configuration options, while users can manage their time and monitor their wellness through a simple web interface.