-- Create database
CREATE DATABASE IF NOT EXISTS activity_log_db;
USE activity_log_db;

-- Create user
CREATE USER IF NOT EXISTS 'activity_log_service'@'%' IDENTIFIED BY 'password';

-- Grant privileges
GRANT ALL PRIVILEGES ON activity_log_db.* TO 'activity_log_service'@'%';

-- Apply changes
FLUSH PRIVILEGES;