CREATE DATABASE IF NOT EXISTS catalog_db;
USE catalog_db;
CREATE TABLE IF NOT EXISTS service_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    active BOOLEAN DEFAULT TRUE
);
CREATE TABLE IF NOT EXISTS car_service (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    estimated_duration INT,
    price DECIMAL(10,2),
    availability_status BOOLEAN DEFAULT TRUE
);

INSERT IGNORE INTO service_category (id, name, description, active) VALUES (1, 'General Maintenance', 'Oil changes, tire rotation, basic checkup', TRUE);
INSERT IGNORE INTO service_category (id, name, description, active) VALUES (2, 'Engine Repair', 'Advanced engine diagnostics and fixes', TRUE);
INSERT IGNORE INTO service_category (id, name, description, active) VALUES (3, 'Body Work', 'Painting and denting', FALSE);

INSERT IGNORE INTO car_service (id, category_id, name, description, estimated_duration, price, availability_status) VALUES (1, 1, 'Full Synthetic Oil Change', 'Up to 5 quarts of full synthetic oil and filter change', 45, 79.99, TRUE);
INSERT IGNORE INTO car_service (id, category_id, name, description, estimated_duration, price, availability_status) VALUES (2, 1, 'Tire Rotation', 'Rotate all 4 tires and check pressure', 30, 24.99, TRUE);
INSERT IGNORE INTO car_service (id, category_id, name, description, estimated_duration, price, availability_status) VALUES (3, 2, 'Check Engine Light Diagnostic', 'Scan computer codes and perform basic engine check', 60, 99.99, TRUE);
INSERT IGNORE INTO car_service (id, category_id, name, description, estimated_duration, price, availability_status) VALUES (4, 2, 'Spark Plug Replacement', 'Replace up to 4 spark plugs', 90, 149.99, TRUE);
INSERT IGNORE INTO car_service (id, category_id, name, description, estimated_duration, price, availability_status) VALUES (5, 3, 'Bumper Repair', 'Fix dents and repaint front or rear bumper', 180, 299.99, FALSE);
