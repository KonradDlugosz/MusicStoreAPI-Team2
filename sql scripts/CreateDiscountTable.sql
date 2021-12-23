USE chinook;
DROP TABLE IF EXISTS albumDiscount;
DROP TABLE IF EXISTS trackDiscount;
DROP TABLE IF EXISTS playlistDiscount;
DROP TABLE IF EXISTS Discontinued;
DROP TABLE IF EXISTS tokens;

SET GLOBAL event_scheduler = ON;

 CREATE TABLE albumDiscount(
	Id INT NOT NULL auto_increment,
    AlbumId int NOT NULL,
    ExpiryDate DATE,
    Amount decimal(3,2),
    PRIMARY KEY (Id),
	FOREIGN KEY (AlbumId) REFERENCES chinook.album(AlbumId)
);

 CREATE TABLE trackDiscount(
	Id INT NOT NULL auto_increment,
    TrackId int NOT NULL,
    ExpiryDate DATE,
    Amount decimal(3,2),
    PRIMARY KEY (Id),
	FOREIGN KEY (TrackId) REFERENCES track(TrackId)
);

 CREATE TABLE playlistDiscount(
	Id INT NOT NULL auto_increment,
    PlayListId int NOT NULL,
    ExpiryDate DATE,
    Amount decimal(3,2),
    PRIMARY KEY (Id),
	FOREIGN KEY (PlayListId) REFERENCES playlist(PlaylistId)
);

CREATE TABLE `Discontinued`
(
	`TrackId` int,
	FOREIGN KEY (`TrackId`) REFERENCES `Track`(`TrackId`),
    `IsDiscontinued` BOOLEAN
);

CREATE TABLE `tokens`
(
    `TokenID` INT PRIMARY KEY AUTO_INCREMENT,
    `Token` VARCHAR(160) NOT NULL,
    `Email` VARCHAR(35) NOT NULL,
    `PermissionLevel` INT NOT NULL,
    ts_expiration TIMESTAMP DEFAULT (CURRENT_TIMESTAMP + INTERVAL 1 WEEK)
);

DROP event if exists deleteTokens;

CREATE event deleteTokens
    ON schedule EVERY 6 HOUR
    DO
        DELETE FROM tokens
        WHERE tokens.ts_expiration <= CURRENT_TIMESTAMP;
