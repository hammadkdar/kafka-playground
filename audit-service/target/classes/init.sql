-- Create schema (H2 uses schemas instead of databases)
CREATE SCHEMA IF NOT EXISTS ACTIVITY_LOG_DB;
SET SCHEMA ACTIVITY_LOG_DB;

-- You can add table creation statements below
-- Example:
-- CREATE TABLE IF NOT EXISTS ACTIVITY_LOG (
--     ID INT AUTO_INCREMENT PRIMARY KEY,
--     ACTION VARCHAR(255),
--     CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP
-- );

-- No user creation or privilege grants needed for H2
