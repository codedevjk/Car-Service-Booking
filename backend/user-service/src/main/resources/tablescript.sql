CREATE DATABASE IF NOT EXISTS user_db;
USE user_db;
CREATE TABLE IF NOT EXISTS customer_profile (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) UNIQUE NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    contact_number VARCHAR(20),
    address VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS vehicle (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    registration_number VARCHAR(50) UNIQUE NOT NULL,
    manufacturer VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    fuel_type VARCHAR(50),
    manufacturing_year INT
);

INSERT IGNORE INTO customer_profile (id, user_id, full_name, email, contact_number, address) VALUES (1, 'U1', 'Rahul Sharma', 'rahul@gmail.com', '+919876543210', 'Andheri, Mumbai, India');
INSERT IGNORE INTO customer_profile (id, user_id, full_name, email, contact_number, address) VALUES (2, 'U2', 'Priya Patel', 'priya@gmail.com', '+918765432109', 'Koramangala, Bengaluru, India');
INSERT IGNORE INTO customer_profile (id, user_id, full_name, email, contact_number, address) VALUES (3, 'U3', 'Amit Singh', 'amit@gmail.com', '+917654321098', 'Connaught Place, New Delhi, India');

INSERT IGNORE INTO vehicle (id, user_id, registration_number, manufacturer, model, fuel_type, manufacturing_year) VALUES (1, 'U1', 'MH-02-AB-1234', 'Honda', 'City', 'PETROL', 2020);
INSERT IGNORE INTO vehicle (id, user_id, registration_number, manufacturer, model, fuel_type, manufacturing_year) VALUES (2, 'U1', 'MH-04-XY-9876', 'Maruti Suzuki', 'Swift', 'PETROL', 2022);
INSERT IGNORE INTO vehicle (id, user_id, registration_number, manufacturer, model, fuel_type, manufacturing_year) VALUES (3, 'U1', 'MH-01-CD-4567', 'Hyundai', 'Creta', 'DIESEL', 2021);

INSERT IGNORE INTO vehicle (id, user_id, registration_number, manufacturer, model, fuel_type, manufacturing_year) VALUES (4, 'U2', 'KA-01-MN-1122', 'Tata', 'Nexon', 'ELECTRIC', 2023);
INSERT IGNORE INTO vehicle (id, user_id, registration_number, manufacturer, model, fuel_type, manufacturing_year) VALUES (5, 'U2', 'KA-03-PQ-3344', 'Mahindra', 'Thar', 'DIESEL', 2021);
INSERT IGNORE INTO vehicle (id, user_id, registration_number, manufacturer, model, fuel_type, manufacturing_year) VALUES (6, 'U2', 'KA-05-RS-5566', 'Toyota', 'Innova Crysta', 'DIESEL', 2019);

INSERT IGNORE INTO vehicle (id, user_id, registration_number, manufacturer, model, fuel_type, manufacturing_year) VALUES (7, 'U3', 'DL-01-AB-1111', 'Kia', 'Seltos', 'PETROL', 2022);
INSERT IGNORE INTO vehicle (id, user_id, registration_number, manufacturer, model, fuel_type, manufacturing_year) VALUES (8, 'U3', 'DL-04-CD-2222', 'Honda', 'Amaze', 'PETROL', 2018);
INSERT IGNORE INTO vehicle (id, user_id, registration_number, manufacturer, model, fuel_type, manufacturing_year) VALUES (9, 'U3', 'DL-08-EF-3333', 'MG', 'Hector', 'PETROL', 2021);
