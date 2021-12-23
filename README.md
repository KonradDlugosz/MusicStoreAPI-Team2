<div id="top"></div>
<div align="center">
   <a href="https://github.com/rwenmax/MusicStoreAPI-Team1">
    <img src="images/api.png" alt="Logo" width="250" height="250">
  </a>
    <h1 align= "center">Music Store API</h1>
</div>
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li><a href="#checklist">Checklist</a></li>
    <li>
      <a href="#Entities">Entities</a>
      <ul>
        <li><a href="#track">Track</a></li>
      </ul>
	    <ul>
        <li><a href="#customer">Customer</a></li>
      </ul>
    </li>
      <li><a href="#tools-and-Frameworks">Tools and Frameworks</a></li>
	<li><a href="#credits-and-contact">Credits and Contact</a></li>
	  <li><a href="#license">License</a></li>
  </ol>
</details>

## About The Project

>Create an API that can manipulate a Music Store Database, with retrieving, updating and inserting data from and into the tables in the database.
>
>Keep in mind when coding of SOLID principles, design patterns, Java standard practices, git conflicts when merging, JDBC configuration and OOP. 
>
>Add addtional functionality such as Token Access, advanced search functions and a few extra tables such as the Discount Table.

## Checklist

- [x] SOLID principles
- [x] Java standard practices
- [x] Use of git and sorting merging conflicts
- [x] Use of JDBC
- [x] Entities, Repositories and Controllers
- [x] Access Token Implementation
- [ ] Advanced Search Functions
- [x] Junit Testing

## Functions

This section demonstrates how to use requests for given entity. 

### **Track**

#### *GET all tracks*

URL endpoint: chinook/tracks

Response: list of all tracks 

```json
[
    {
        "id": 1,
        "name": "For Those About To Rock (We Salute You)",
        "albumId": 1,
        "mediaTypeId": 1,
        "genreId": 1,
        "composer": "Angus Young, Malcolm Young, Brian Johnson",
        "milliseconds": 343719,
        "bytes": 11170334,
        "unitPrice": 0.99
    },
    {
        "id": 2,
        "name": "Balls to the Wall",
        "albumId": 2,
        "mediaTypeId": 2,
        "genreId": 1,
        "composer": null,
        "milliseconds": 342562,
        "bytes": 5510424,
        "unitPrice": 0.99
    },
...
```



#### *GET track by ID*

URL endpoint: /chinook/tracks/{trackId}

Response: track for given ID

Example for track with ID: **100**

```json
{
    "id": 100,
    "name": "Out Of Exile",
    "albumId": 11,
    "mediaTypeId": 1,
    "genreId": 4,
    "composer": "Cornell, Commerford, Morello, Wilk",
    "milliseconds": 291291,
    "bytes": 9506571,
    "unitPrice": 0.99
}
```



#### *GET track by name*

URL endpoint: /chinook/tracks-by-name?name=Balls

Response: tracks that match name 

Example for track with name: **"Out of exile"**

```json
[
    {
        "id": 100,
        "name": "Out Of Exile",
        "albumId": 11,
        "mediaTypeId": 1,
        "genreId": 4,
        "composer": "Cornell, Commerford, Morello, Wilk",
        "milliseconds": 291291,
        "bytes": 9506571,
        "unitPrice": 0.99
    }
]
```



#### *GET track by album ID*

URL endpoint: /chinook/tracks-by-album/{albumId}

Response: list of tracks in given album

Example for album with ID: **9**

```json
[
    {
        "id": 77,
        "name": "Enter Sandman",
        "albumId": 9,
        "mediaTypeId": 1,
        "genreId": 3,
        "composer": "Apocalyptica",
        "milliseconds": 221701,
        "bytes": 7286305,
        "unitPrice": 0.99
    },
    {
        "id": 78,
        "name": "Master Of Puppets",
        "albumId": 9,
        "mediaTypeId": 1,
        "genreId": 3,
        "composer": "Apocalyptica",
        "milliseconds": 436453,
        "bytes": 14375310,
        "unitPrice": 0.99
    },
...
```



#### *GET track by artist ID*

URL endpoint: /chinook/tracks-by-artist/{artistId}

Response: tracks by artist 

Example for artist **AC/DC** with ID : **1**

```json
[
    {
        "id": 1,
        "name": "For Those About To Rock (We Salute You)",
        "albumId": 1,
        "mediaTypeId": 1,
        "genreId": 1,
        "composer": "Angus Young, Malcolm Young, Brian Johnson",
        "milliseconds": 343719,
        "bytes": 11170334,
        "unitPrice": 0.99
    },
    {
        "id": 6,
        "name": "Put The Finger On You",
        "albumId": 1,
        "mediaTypeId": 1,
        "genreId": 1,
        "composer": "Angus Young, Malcolm Young, Brian Johnson",
        "milliseconds": 205662,
        "bytes": 6713451,
        "unitPrice": 0.99
    },
...
```



#### POST track

URL endpoint: /chinook/tracks/add - requires body to send with request.

Response: details of added tracks

Example of body for new track: 

```json
{
    "name": "Breaking The Rules",
    "albumId": 3,
    "mediaTypeId": 1,
    "genreId": 1,
    "composer": "Angus Young, Malcolm Young, Brian Johnson",
    "milliseconds": 263288,
    "bytes": 8596840,
    "unitPrice": 0.99
}
```



#### PUT track

URL endpoint: /chinook/tracks/update - requires body with details to update

Response: updated track

Example of body:

```json
{
    "id":10,
    "name": "Never gonna give you up",
    "albumId": 3,
    "mediaTypeId": 1,
    "genreId": 1,
    "composer": "Rick Astley",
    "milliseconds": 263288,
    "bytes": 8596840,
    "unitPrice": 0.99
}

```



<p align="right">(<a href="#top">back to top</a>)</p>

### **Customer**

#### *GET all customers*

URL endpoint: chinook/allcustomer

Response: list of all customers

```json
[
    {
        "id": 1,
        "firstName": "Luís",
        "lastName": "Gonçalves",
        "company": "Embraer - Empresa Brasileira de Aeronáutica S.A.",
        "address": "Av. Brigadeiro Faria Lima, 2170",
        "city": "São José dos Campos",
        "state": "SP",
        "country": "Brazil",
        "postalCode": "12227-000",
        "phone": "+55 (12) 3923-5555",
        "fax": "+55 (12) 3923-5566",
        "email": "luisg@embraer.com.br",
        "supportRepId": 4
    },
    {
        "id": 2,
        "firstName": "Leonie",
        "lastName": "Köhler",
        "company": null,
        "address": "Theodor-Heuss-Straße 34",
        "city": "Stuttgart",
        "state": null,
        "country": "Germany",
        "postalCode": "70174",
        "phone": "+49 0711 2842222",
        "fax": null,
        "email": "leonekohler@surfeu.de",
        "supportRepId": 6
    },
...
```



#### *GET customer by ID*

URL endpoint: /chinook/customer/{customerID}

Response: customer for given ID

Example for customer with ID: **10**

```json
{
    "id": 10,
    "firstName": "Eduardo",
    "lastName": "Martins",
    "company": "Woodstock Discos",
    "address": "Rua Dr. Falcão Filho, 155",
    "city": "São Paulo",
    "state": "SP",
    "country": "Brazil",
    "postalCode": "01007-010",
    "phone": "+55 (11) 3033-5446",
    "fax": "+55 (11) 3033-4564",
    "email": "eduardo@woodstock.com.br",
    "supportRepId": 4
}
```

#### *POST customer*

URL endpoint: /chinook/customer/add

Response: Inserts a new customer into the Customer Table

Example for customer in json format:
```json
{
    "firstName": "Eduardo",
    "lastName": "Martins",
    "company": "Woodstock Discos",
    "address": "Rua Dr. Falcão Filho, 155",
    "city": "São Paulo",
    "state": "SP",
    "country": "Brazil",
    "postalCode": "01007-010",
    "phone": "+55 (11) 3033-5446",
    "fax": "+55 (11) 3033-4564",
    "email": "eduardo@woodstock.com.br",
    "supportRepId": 4
}
```

#### *PUT customer*

URL endpoint: /chinook/customer/update/{customerID}

Response: Updates an existsing customer by ID

Example for customer of ID: 10, being updated with:
```json
{
    "firstName": "Eduardo",
    "lastName": "Martins",
    "company": "Woodstock Discos",
    "address": "Rua Dr. Falcão Filho, 155",
    "city": "São Paulo",
    "state": "SP",
    "country": "Brazil",
    "postalCode": "01007-010",
    "phone": "+55 (11) 3033-5446",
    "fax": "+55 (11) 3033-4564",
    "email": "eduardo@woodstock.com.br",
    "supportRepId": 4
}
```

#### *GET customer by Email*

URL endpoint: /chinook/customer/email/{email}

Response: customer that matches the given email

Example for Email: "eduardo@woodstock.com.br":
```json
{
    "firstName": "Eduardo",
    "lastName": "Martins",
    "company": "Woodstock Discos",
    "address": "Rua Dr. Falcão Filho, 155",
    "city": "São Paulo",
    "state": "SP",
    "country": "Brazil",
    "postalCode": "01007-010",
    "phone": "+55 (11) 3033-5446",
    "fax": "+55 (11) 3033-4564",
    "email": "eduardo@woodstock.com.br",
    "supportRepId": 4
}
```

<p align="right">(<a href="#top">back to top</a>)</p>

**Artists**

*Get All artists*

URL endpoint: /chinook/artist/findAll

Response: list of all artists

```json
[
    {
        "id": 1,
        "name": "UMI"
    },
    {
        "id": 2,
        "name": "Accept"
    },
    {
        "id": 3,
        "name": "Aerosmith"
    },
    {
        "id": 4,
        "name": "Alanis Morissette"
    },
...
```

*GET Artist by ID*

URL endpoint: /chinook/artist/{artistId}

Response: artist for given ID

Example for artist with ID: 22

```json
{
    "id": 22,
    "name": "Led Zeppelin"
}
```

*POST artist*

URL endpoint: /chinook/artist/add -requires body to send with request

Response: details of added artist

Example body of new artist:

```json
{
    "name": "Madrugada"
}
```

*PUT artist*

URL endpoint: /chinook/artist/update

Response: details of updated artist

Example body of updated artist:

```json
{   
    "id": 278,
    "name": "Nick Waterhouse"
}
```

<p align="right">(<a href="#top">back to top</a>)</p>



**Album**

*Get All albums*

URL endpoint: /chinook/albums

Response: list of all albums

```json
[
    {
        "id": 1,
        "title": "For Those About To Rock We Salute You",
        "albumId": {
        	"id": 1,
        	"name": "UMI"
        }
    },
    {
        "id": 2,
        "name": "Balls to the Wall",
        "albumId": {
        	"id": 2,
        	"name": "Accept"
        }
    },
...
```

*Get Album by ID*

URL endpoint: chinook/album/{albumId}

Response: album for given id

Example for album with id : 99

```json
{
    "id": 99,
    "title": "Fear Of The Dark",
    "artistId": {
        "id": 90,
        "name": "Iron Maiden"
    }
}
```


---

*Put Album*

URL endpoint: chinook/album/update/{albumid}

Response: Updates an existing album byID

Example for album of ID: 21, being updated with:

```json
{
    "title": "Touch the Sky",
    "artistId": {
        "id": 279,
        "name": "The Black Pumas"
    }
}
```

<p align="right">(<a href="#top">back to top</a>)</p>

**Employee**

*Get all employees*

URL endpoint: chinook/allemployees

Response: list of all employees

```json
[
    {
        "id": 1,
        "lastName": "Adams",
        "firstName": "Andrew",
        "title": "General Manager",
        "reportsTo": null,
        "birthDate": "1962-02-18T00:00:00Z",
        "hireDate": "2002-08-13T23:00:00Z",
        "address": "11120 Jasper Ave NW",
        "city": "Edmonton",
        "state": "AB",
        "country": "Canada",
        "postalCode": "T5K 2N1",
        "phone": "+1 (780) 428-9482",
        "fax": "+1 (780) 428-3457",
        "email": "andrew@chinookcorp.com"
    },
    {
        "id": 2,
        "lastName": "Edwards",
        "firstName": "Nancy",
        "title": "Sales Manager",
        "reportsTo": {
            "id": 1,
            "lastName": "Adams",
            "firstName": "Andrew",
            "title": "General Manager",
            "reportsTo": null,
            "birthDate": "1962-02-18T00:00:00Z",
            "hireDate": "2002-08-13T23:00:00Z",
            "address": "11120 Jasper Ave NW",
            "city": "Edmonton",
            "state": "AB",
            "country": "Canada",
            "postalCode": "T5K 2N1",
            "phone": "+1 (780) 428-9482",
            "fax": "+1 (780) 428-3457",
            "email": "andrew@chinookcorp.com"
        },
        "birthDate": "1958-12-08T00:00:00Z",
        "hireDate": "2002-04-30T23:00:00Z",
        "address": "825 8 Ave SW",
        "city": "Calgary",
        "state": "AB",
        "country": "Canada",
        "postalCode": "T2P 2T3",
        "phone": "+1 (403) 262-3443",
        "fax": "+1 (403) 262-3322",
        "email": "nancy@chinookcorp.com"
    },
...

```

*GET employee by ID*

URL endpoint: /chinook/employees/{employeeId}

Response: employee for given ID

Example for employee with ID: 6

```json
{
    "id": 6,
    "lastName": "Mitchell",
    "firstName": "Michael",
    "title": "IT Manager",
    "reportsTo": {
        "id": 1,
        "lastName": "Adams",
        "firstName": "Andrew",
        "title": "General Manager",
        "reportsTo": null,
        "birthDate": "1962-02-18T00:00:00Z",
        "hireDate": "2002-08-13T23:00:00Z",
        "address": "11120 Jasper Ave NW",
        "city": "Edmonton",
        "state": "AB",
        "country": "Canada",
        "postalCode": "T5K 2N1",
        "phone": "+1 (780) 428-9482",
        "fax": "+1 (780) 428-3457",
        "email": "andrew@chinookcorp.com"
    },
    "birthDate": "1973-06-30T23:00:00Z",
    "hireDate": "2003-10-16T23:00:00Z",
    "address": "5827 Bowness Road NW",
    "city": "Calgary",
    "state": "AB",
    "country": "Canada",
    "postalCode": "T3B 0C5",
    "phone": "+1 (403) 246-9887",
    "fax": "+1 (403) 246-9899",
    "email": "michael@chinookcorp.com"
}
```

POST employee

URL endpoint: /chinook/employee/add

Response: Inserts a new employee into the Employee Table

Example for employee in json fotmat:

```json
{
    "lastName": "Westbrook",
    "firstName": "Russell",
    "title": "General Manager",
    "reportsTo": null,
    "birthDate": "1986-03-20T00:00:00Z",
    "hireDate": "2005-09-17T23:00:00Z",
    "address": "5034 Casper Ave SW",
    "city": "Edmonton",
    "state": "AB",
    "country": "Canada",
    "postalCode": "T7K 1N3",
    "phone": "+1 (780) 429-9482",
    "fax": "+1 (780) 429-3457",
    "email": "ruswestbrook@chinookcorp.com"
}
```

*PUT employee*

URL endpoint: /chinook/employee/update/{updateID}

Response: Updates an existing employee by ID

Example of employee of ID: 1, being updated with:

```json
{
    "lastName": "Westbrook",
    "firstName": "Russell",
    "title": "General Manager",
    "reportsTo": null,
    "birthDate": "1986-03-20T00:00:00Z",
    "hireDate": "2005-09-17T23:00:00Z",
    "address": "5034 Casper Ave SW",
    "city": "Edmonton",
    "state": "AB",
    "country": "Canada",
    "postalCode": "T7K 1N3",
    "phone": "+1 (780) 429-9482",
    "fax": "+1 (780) 429-3457",
    "email": "ruswestbrook@chinookcorp.com"
}
```

<p align="right">(<a href="#top">back to top</a>)</p>

**Genre**

*Get all genres*

URL endpoint: chinook/allgenres

Response: list of all genres

```json
[
    {
        "id": 1,
        "name": "Rock"
    },
    {
        "id": 2,
        "name": "Jazz"
    },
    {
        "id": 3,
        "name": "Metal"
    },
    {
        "id": 4,
        "name": "Alternative & Punk"
    },
...
```

*Get genre by ID*

URL endpoint: /chinook/genre/{genreId}

Response: genre for given ID

Example for genre with ID: 15

```json
{
    "id": 15,
    "name": "Electronica/Dance"
}
```

*POST genre*

URL endpoint: /chinook/genre/add

Response: Inserts a new  genre into the Genre Table

Example for genre in json format:

```json
{
    "name": "Trip Hop"
}
```

<p align="right">(<a href="#top">back to top</a>)</p>

**Mediatype**

*Get all mediatypes*

URL endpoint: chinook/allmediatypes

Response: list of all mediatypes

```json
[
    {
        "id": 1,
        "name": "MPEG audio file"
    },
    {
        "id": 2,
        "name": "Protected AAC audio file"
    },
    {
        "id": 3,
        "name": "Protected MPEG-4 video file"
    },
    {
        "id": 4,
        "name": "Purchased AAC audio file"
    },
...
```

*Get mediatype by ID*

URL endpoint: /chinook/mediatype/{mediatypeId}

Response: mediatype for given ID

Example for mediatype with ID: 5

```json
{
    "id": 5,
    "name": "AAC audio file"
}
```

*POST mediatype*

URL endpoint: /chinook/mediatype/add

Response: Inserts a new mediatype into the Mediatype Table

Example for mediatype in json format:

```json
{
    "name": "FLAC audio file"
}
```

<p align="right">(<a href="#top">back to top</a>)</p>

**Playlist**

*Get all playlists*

URL endpoint: chinook/playlists

Response: list of all playlists

```json
[
    {
        "id": 1,
        "name": "Music"
    },
    {
        "id": 2,
        "name": "Movies"
    },
    {
        "id": 3,
        "name": "TV Shows"
    },
    {
        "id": 4,
        "name": "Audiobooks"
    },
    {
        "id": 5,
        "name": "90’s Music"
    },
...
```

*GET playlist by ID*

URL endpoint: /chinook/playlist/{playlistId}

Response: playlist for given ID

Example for playlist with ID: 6

```json
{
    "id": 6,
    "name": "Audiobooks"
}
```

*POST playlist*

URL endpoint: /chinook/playlist/add

Response: Inserts a new playlist into the Playlist Table

Example for playlist in json format:

```json
{
    "name": "Road Trip"
}
```

*PUT playlist*

URL endpoint: /chinook/playlist/update/{playlistId}

Response: Updates an existing playlist by ID

Example for playlist of ID: 15, being updated with:

```json
{
        "name": "On-The-Go"
}
```

*DELETE playlist*

URL endpoint: /chinook/playlist/delete/{playlistId}

Response: Deletes an existing playlist by ID

<p align="right">(<a href="#top">back to top</a>)</p>

**Playlist Tracks**

*GET all playlist tracks*

URL endpoint: chinook/playlisttracks

Response: list of all playlist tracks

```json
[
    {
        "id": {
            "playlistId": 1,
            "trackId": 1
        }
    },
    {
        "id": {
            "playlistId": 8,
            "trackId": 1
        }
    },
    {
        "id": {
            "playlistId": 17,
            "trackId": 1
	}
...
```

*GET specific track from specific playlist*

URL endpoint: /chinook/playlisttrack/{playlistId}/{trackId}

Response: list of playlist ID and track ID

Example: playlist with ID 17 and track ID 3

```json
{
    "id": {
        "playlistId": 17,
        "trackId": 3
    }
}
```

*POST specific track in playlist*

URL endpoint: /chinook/playlisttrack/add/{playlistId}/{trackId}

Response: adding a track in a playlist

Example: adding in playlist with ID 17 track with ID 4

```json
{
    "id": {
        "playlistId": 17,
        "trackId": 4
    }
}
```

*PUT playlist track*

URL endpoint: /chinook/playlisttrack/update

Response: updating a track in a playlist

Example: adding in playlist with ID 8 track with ID 276

```json
{"id": {
            "playlistId": 8,
            "trackId": 276
        }
}
```

*DELETE playlist track*

URL endpoint: /chinook/playlisttrack/delete

Response: deleting a specified playlist

<p align="right">(<a href="#top">back to top</a>)</p>

## Credits and Contact

[Kamil](https://github.com/rwenmax) • [Konrad](https://github.com/KonradDlugosz) • [Talal](https://github.com/talal1998) • [Alex](https://github.com/alexsusanu) • [Anthony](https://github.com/MagerXser) • [Ed](https://github.com/EdBencito) • [Nikolas](https://github.com/Moodhunter34) • [Ishmael](https://github.com/ishariffSG) • [Mihai](https://github.com/Ascerte)  

---

## License

**Free**
