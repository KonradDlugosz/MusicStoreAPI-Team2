USE chinook;
DROP TABLE IF EXISTS albumDiscount;
DROP TABLE IF EXISTS trackDiscount;
DROP TABLE IF EXISTS playlistDiscount;

DROP TABLE IF EXISTS Discontinued;

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
