CREATE DATABASE IF NOT EXISTS user_db;
USE user_db;
CREATE TABLE IF NOT EXISTS customer_profile (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20)
);
CREATE TABLE IF NOT EXISTS vehicle (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    registration_number VARCHAR(50) UNIQUE NOT NULL,
    manufacturer VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    fuel_type VARCHAR(50),
    manufacturing_year INT,
    color VARCHAR(50)
);

INSERT IGNORE INTO customer_profile (id, email, first_name, last_name, phone) VALUES (1, 'user@gmail.com', 'Regular', 'User', '1234567890');
INSERT IGNORE INTO customer_profile (id, email, first_name, last_name, phone) VALUES (2, 'admin@gmail.com', 'Admin', 'User', '0987654321');

INSERT IGNORE INTO vehicle (customer_id, registration_number, manufacturer, model, fuel_type, manufacturing_year, color) VALUES (1, 'MH-01-AB-1234', 'Honda', 'Civic', 'PETROL', 2020, 'White');
INSERT IGNORE INTO vehicle (customer_id, registration_number, manufacturer, model, fuel_type, manufacturing_year, color) VALUES (1, 'KA-05-XY-9876', 'Toyota', 'Camry', 'HYBRID', 2022, 'Black');
INSERT IGNORE INTO vehicle (customer_id, registration_number, manufacturer, model, fuel_type, manufacturing_year, color) VALUES (1, 'DL-02-CD-5678', 'Ford', 'Mustang', 'PETROL', 2019, 'Red');
INSERT IGNORE INTO vehicle (customer_id, registration_number, manufacturer, model, fuel_type, manufacturing_year, color) VALUES (1, 'UP-16-EF-4321', 'Tesla', 'Model 3', 'ELECTRIC', 2023, 'Blue');
INSERT IGNORE INTO vehicle (customer_id, registration_number, manufacturer, model, fuel_type, manufacturing_year, color) VALUES (1, 'TN-07-GH-8765', 'Hyundai', 'i20', 'DIESEL', 2018, 'Silver');
