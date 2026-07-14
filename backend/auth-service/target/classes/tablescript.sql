CREATE DATABASE IF NOT EXISTS auth_db;
USE auth_db;
CREATE TABLE IF NOT EXISTS user_credentials (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) UNIQUE,
    full_name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);
INSERT IGNORE INTO user_credentials (id, user_id, full_name, email, password, role) VALUES (1, 'A1', 'System Administrator', 'admin@gmail.com', 'admin1234', 'ADMIN');
INSERT IGNORE INTO user_credentials (id, user_id, full_name, email, password, role) VALUES (2, 'U1', 'Rahul Sharma', 'rahul@gmail.com', 'pwd123', 'CUSTOMER');
INSERT IGNORE INTO user_credentials (id, user_id, full_name, email, password, role) VALUES (3, 'U2', 'Priya Patel', 'priya@gmail.com', 'pwd123', 'CUSTOMER');
INSERT IGNORE INTO user_credentials (id, user_id, full_name, email, password, role) VALUES (4, 'U3', 'Amit Singh', 'amit@gmail.com', 'pwd123', 'CUSTOMER');
