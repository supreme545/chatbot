CREATE DATABASE chatbot_db;

USE chatbot_db;

CREATE TABLE Users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    role ENUM('ADMIN', 'USER') NOT NULL
);

CREATE TABLE Configurations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    config_key VARCHAR(50) NOT NULL,
    config_value TEXT
);

CREATE TABLE Interactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    query TEXT NOT NULL,
    response TEXT,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE SET NULL
);

CREATE TABLE Feedback (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    comments TEXT,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE SET NULL
);