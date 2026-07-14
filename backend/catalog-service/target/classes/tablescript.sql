CREATE DATABASE IF NOT EXISTS catalog_db;
USE catalog_db;
CREATE TABLE IF NOT EXISTS service_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    active BOOLEAN DEFAULT TRUE
);
CREATE TABLE IF NOT EXISTS service_package (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2),
    availability_status VARCHAR(50) DEFAULT 'ACTIVE'
);

INSERT IGNORE INTO service_category (id, name, description, active) VALUES (1, 'General Maintenance', 'Oil changes, tire rotation, basic checkup', TRUE);
INSERT IGNORE INTO service_category (id, name, description, active) VALUES (2, 'Engine Repair', 'Advanced engine diagnostics and fixes', TRUE);
INSERT IGNORE INTO service_category (id, name, description, active) VALUES (3, 'Body Work', 'Painting and denting', FALSE);

INSERT IGNORE INTO service_package (id, category_id, name, description, price, availability_status) VALUES (1, 1, 'Full Synthetic Oil Change', 'Up to 5 quarts of full synthetic oil and filter change', 79.99, 'ACTIVE');
INSERT IGNORE INTO service_package (id, category_id, name, description, price, availability_status) VALUES (2, 1, 'Tire Rotation', 'Rotate all 4 tires and check pressure', 24.99, 'ACTIVE');
INSERT IGNORE INTO service_package (id, category_id, name, description, price, availability_status) VALUES (3, 2, 'Check Engine Light Diagnostic', 'Scan computer codes and perform basic engine check', 99.99, 'ACTIVE');
INSERT IGNORE INTO service_package (id, category_id, name, description, price, availability_status) VALUES (4, 2, 'Spark Plug Replacement', 'Replace up to 4 spark plugs', 149.99, 'ACTIVE');
INSERT IGNORE INTO service_package (id, category_id, name, description, price, availability_status) VALUES (5, 3, 'Bumper Repair', 'Fix dents and repaint front or rear bumper', 299.99, 'INACTIVE');
