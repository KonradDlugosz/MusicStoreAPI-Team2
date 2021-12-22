use chinook;
SET GLOBAL event_scheduler = ON;

drop table if exists tokens;

create table `tokens`
(
    `TokenID` INT PRIMARY KEY AUTO_INCREMENT,
    `Token` VARCHAR(160) NOT NULL,
    `Email` VARCHAR(35) NOT NULL,
    `PermissionLevel` INT NOT NULL,
    ts_expiration TIMESTAMP DEFAULT (CURRENT_TIMESTAMP + INTERVAL 1 WEEK)
);

drop event if exists deleteTokens;

create event deleteTokens
    on schedule EVERY 6 HOUR
    DO
        Delete from tokens
        Where tokens.ts_expiration <= CURRENT_TIMESTAMP;