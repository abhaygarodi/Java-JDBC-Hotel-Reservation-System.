# Java-JDBC-Hotel-Reservation-System.
This is a functional command-line Hotel Reservation System built using Core Java and JDBC (Java Database Connectivity) for persistent data storage in a MySQL database.

# Features
Reserve a Room: Insert a new reservation record (guest name, room number, contact number).

View Reservations: Display all current reservations from the database in a formatted table.

Get Room Number: Retrieve the room number using a combination of Reservation ID and Guest Name for verification.

Update Reservation: Modify an existing reservation's details (guest name, room number, contact number) using its Reservation ID.

Delete Reservation: Remove a reservation record from the database using its Reservation ID.

Robust Database Interaction: Utilizes PreparedStatement to securely execute parameterized SQL queries, preventing SQL injection.

# Technologies Used
 
Java	Core programming language for the application logic.
JDBC	API for connecting the Java application to the database.
MySQL	Relational database for storing reservation data.
Maven/Gradle (Implied)	Dependency management for the MySQL Connector/J driver (needed for JDBC).
Prerequisites
To run this project locally, you will need:

Java Development Kit (JDK) (version 8 or newer recommended).

A running MySQL Server instance.

The MySQL Connector/J driver (e.g., added as a dependency or in the classpath).
Database Setup
Before running the application, you must create the necessary database and table structure.
 
# Update Connection Details: The application uses the following default connection details. You must change these in HotelReservationSystem.java if your MySQL configuration is different.

#Java
private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
private static final String username = "root";
private static final String password = "Admin@123";

#Languages Used
Java (Core Application Logic)
SQL (Database Interactions)
