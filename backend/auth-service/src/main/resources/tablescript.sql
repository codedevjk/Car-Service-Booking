CREATE DATABASE IF NOT EXISTS auth_db;
USE auth_db;
CREATE TABLE IF NOT EXISTS user_credentials (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);
INSERT IGNORE INTO user_credentials (id, email, password, role) VALUES (1, 'user@gmail.com', 'user1234', 'USER');
INSERT IGNORE INTO user_credentials (id, email, password, role) VALUES (2, 'admin@gmail.com', 'admin1234', 'ADMIN');
