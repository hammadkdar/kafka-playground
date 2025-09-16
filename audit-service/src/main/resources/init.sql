-- Create database
CREATE OR REPLACE DATABASE IF NOT EXISTS activity_log_db;
USE activity_log_db;

-- Create user
CREATE USER IF NOT EXISTS 'activity_log_service'@'%' IDENTIFIED BY 'password';
FLUSH PRIVILEGES;
