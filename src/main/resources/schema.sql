-- 1. Create Database/Schema parser
CREATE DATABASE parser;

-- 2. Create access log table if not exist
CREATE TABLE IF NOT EXISTS ACCESS_LOG
(
    LOG_ID     BIGINT       NOT NULL AUTO_INCREMENT,
    IP_ADDRESS VARCHAR(255) NOT NULL,
    REQUEST    VARCHAR(255),
    START_DATE DATETIME,
    STATUS     INTEGER,
    USER_AGENT VARCHAR(255),
    primary key (LOG_ID)
) ENGINE = InnoDB;

-- 3. Create blocked ip table if not exist
CREATE TABLE blocked_ip
(
    IP_ADDRESS_ID BIGINT       NOT NULL AUTO_INCREMENT,
    COMMENTS      VARCHAR(255) NOT NULL,
    IP_ADDRESS    VARCHAR(255) NOT NULL,
    PRIMARY KEY (IP_ADDRESS_ID)
) ENGINE = InnoDB;

-- 4. Clean access_log and blocked_ip datas
TRUNCATE TABLE access_log;
TRUNCATE TABLE blocked_ip;
