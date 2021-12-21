# MusicStoreAPI-Team1


### Setup the IsDiscontinued SQL Query:

```
USE `Chinook`;

CREATE TABLE `Discontinued`
(
	`TrackId` int,
	FOREIGN KEY (`TrackId`) REFERENCES `Track`(`TrackId`),
    `IsDiscontinued` BOOLEAN
);
```
