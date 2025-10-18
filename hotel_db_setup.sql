-- --------------------------------------------------------
-- Database Setup Script for Hotel Reservation System
-- --------------------------------------------------------

-- 1. Create the Database if it doesn't exist
-- The Java application is configured to use 'hotel_db'
CREATE DATABASE IF NOT EXISTS hotel_db;

-- 2. Use the newly created or existing database
USE hotel_db;

-- 3. Drop the 'reservations' table if it exists
-- This is useful for development to easily reset the table structure
DROP TABLE IF EXISTS reservations;

-- 4. Create the 'reservations' table
-- This table stores all reservation details.
CREATE TABLE reservations (
    -- Primary Key: Unique identifier for each reservation
    reservation_id INT PRIMARY KEY AUTO_INCREMENT,

    -- Guest Name: Cannot be empty (NOT NULL)
    guest_name VARCHAR(100) NOT NULL,

    -- Room Number: Stores the room assigned to the guest
    room_number INT NOT NULL,

    -- Contact Number: Optional contact information for the guest
    contact_number VARCHAR(20),

    -- Reservation Date: Automatically set to the time the record is inserted
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. Optional: Insert a few sample reservations for testing

INSERT INTO reservations (guest_name, room_number, contact_number) VALUES
('Alice Smith', 101, '555-1234'),
('Bob Johnson', 205, '555-5678'),
('Charlie Brown', 310, '555-9012');

-- --------------------------------------------------------
-- Setup Complete
-- --------------------------------------------------------