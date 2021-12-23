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
        <li><a href="#token">Token</a></li>
      </ul>
      <ul>
        <li><a href="#track">Track</a></li>
      </ul>
	    <ul>
        <li><a href="#discount">Discount</a></li>
      </ul>
	    <ul>
        <li><a href="#customer">Customer</a></li>
      </ul>
      <ul>
        <li><a href="#invoice">Invoice</a></li>
      </ul>
      <ul>
        <li><a href="#invoiceline">Invoiceline</a></li>
      </ul>
	    <ul>
        <li><a href="#artists">Artists</a></li>
      </ul>
	    <ul>
        <li><a href="#album">Album</a></li>
      </ul>
	    <ul>
        <li><a href="#employee">Employee</a></li>
      </ul>
	    <ul>
        <li><a href="#genre">Genre</a></li>
      </ul>
	    <ul>
        <li><a href="#mediatype">Media-type</a></li>
      </ul>
	    <ul>
        <li><a href="#playlists">Playlists</a></li>
      </ul>
	    <ul>
        <li><a href="#playlist-tracks">Playlist Tracks</a></li>
      </ul>
	    <ul>
        <li><a href="#popularity-functions">Popularity functions</a></li>
      </ul>
	    <ul>
        <li><a href="#discontinued">Discontinued</a></li>
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
- [x] Advanced Search Functions
- [x] Junit Testing
- [x] Required Authentication for Chosen Requests with varying Permission Levels

## Functions

This section demonstrates how to use requests for given entity. 

Functions that Update, Add, Delete or Get sensitive information such as Customer or Employee Data require an additional post-fix of "/{token}", in that post-fix a token is inserted for authentication.

### **Token**

#### *GET all tokens*

URL endpoint: chinook/tokens

Response: list of all tokens 

```json
[
    {
        "id": 1,
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg",
        "email": "andrew@chinookcorp.com",
        "permissionLevel": 2
    },
    {
        "id": 2,
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg",
        "email": "exmaple@chinookcorp.com",
        "permissionLevel": 1
    },
]
```

#### *GET token by Token*

URL endpoint: /chinook/token/findbytoken/{token}

Response: token object for given token

Example for token with token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg"

```json
{
    "id": 1,
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg",
    "email": "andrew@chinookcorp.com",
    "permissionLevel": 2
}
```

#### Create token

URL endpoint: /chinook/token/add/{email}

Response: Generates a new token based on the email


#### DELETE token by token

URL endpoint: /chinook/token/deletebytoken/{token}

Response: deletes token row from the tokens table based on the given token

#### DELETE token by token id

URL endpoint: /chinook/token/deletebyid/{token}

Response: deletes token row from the tokens table based on the given token id

<p align="right">(<a href="#top">back to top</a>)</p>

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
    }
]
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
    }
]
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
    }
]
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

### Discount

Discount controller contains requests for setting, editing and deleting discounts for **tracks, albums and playlists.**  Here is a list of requests endpoint for all the discounts, they follow a pattern of 

**chinook/[table name]/[verb]/[optional - id]** : 

| Request type    | Track discount               | Album discount             | Playlist discount               |
| --------------- | ---------------------------- | -------------------------- | ------------------------------- |
| GET all         | /tracks-discount             | /album-discount            | /playlists-discount             |
| GET by ID       | /tracks-discount/{id}        | /album-discount{id}        | /playlists-discount{id}         |
| POST discount   | /tracks-discount/add         | /album-discount/add        | /playlists-discount/add         |
| PUT discount    | /tracks-discount/update      | /album-discount/update     | /playlists-discount/update      |
| DELETE discount | /tracks-discount/delete/{id} | /album-discount/delete{id} | /playlists-discount/delete/{id} |

***Please not that the post and put methods would require a body for the discount table to be populated ***

Example of JSON body to add discount for tracks: 

```json
{
    "trackId": 10,
    "expiryDate": "2022-11-11",
    "amount": 0.50
}
```

Example to get all track discounts: /chinook/tracks-discount

```json
[
    {
        "id": 1,
        "trackId": 2,
        "expiryDate": "2022-11-11",
        "amount": 0.20
    },
    {
        "id": 3,
        "trackId": 10,
        "expiryDate": "2022-11-11",
        "amount": 0.50
    }
]
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
    }
]
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

Response: Updates an existing customer by ID

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

### **Discontinued**

#### *Creating the Table*

```sql
USE `Chinook`;

CREATE TABLE `Discontinued`
(
	`TrackId` int,
	FOREIGN KEY (`TrackId`) REFERENCES `Track`(`TrackId`),
    `IsDiscontinued` BOOLEAN
);
```

#### *POST discontinued*

URL endpoint: /chinook/track/discontinue/{trackId}

Response: Inserts a pre-existing track by TrackID into the discontinued table

Example for trackId: 300:

```json
{
    "isDiscontinued": true
}
```

#### *GET ALL Discontinued Entries*

URL endpoint: /chinook/track/discontinue/everything

Response: Gets all entries in the Discontinued Table

```json
[
    {
        "id": 1,
        "trackId": {
            "id": 300,
            "name": "O Erê",
            "albumId": 27,
            "mediaTypeId": 1,
            "genreId": 8,
            "composer": "Bernardo Vilhena/Bino/Da Gama/Lazao/Toni Garrido",
            "milliseconds": 206942,
            "bytes": 6950332,
            "unitPrice": 0.99
        },
        "isDiscontinued": true
    },
    {
        "id": 3,
        "trackId": {
            "id": 302,
            "name": "A Estrada",
            "albumId": 27,
            "mediaTypeId": 1,
            "genreId": 8,
            "composer": "Da Gama/Lazao/Toni Garrido",
            "milliseconds": 282174,
            "bytes": 9344477,
            "unitPrice": 0.99
        },
        "isDiscontinued": false
    }
]
```
#### *GET ALL Discontinued Entries, where the Track is Discontinued*

URL endpoint: /chinook/track/discontinue/everything/true

Response: Gets all entries in the Discontinued Table where isDiscontinued = true

```json
[
    {
        "id": 1,
        "trackId": {
            "id": 300,
            "name": "O Erê",
            "albumId": 27,
            "mediaTypeId": 1,
            "genreId": 8,
            "composer": "Bernardo Vilhena/Bino/Da Gama/Lazao/Toni Garrido",
            "milliseconds": 206942,
            "bytes": 6950332,
            "unitPrice": 0.99
        },
        "isDiscontinued": true
    },
    {
        "id": 2,
        "trackId": {
            "id": 301,
            "name": "A Sombra Da Maldade",
            "albumId": 27,
            "mediaTypeId": 1,
            "genreId": 8,
            "composer": "Da Gama/Toni Garrido",
            "milliseconds": 285231,
            "bytes": 9544383,
            "unitPrice": 0.99
        },
        "isDiscontinued": true
    }
]
```

#### *GET ALL Discontinued Entries, where the Track is not Discontinued*

URL endpoint: /chinook/track/discontinue/everything/false

Response: Gets all entries in the Discontinued Table where isDiscontinued = false

```json
[
    {
        "id": 3,
        "trackId": {
            "id": 302,
            "name": "A Estrada",
            "albumId": 27,
            "mediaTypeId": 1,
            "genreId": 8,
            "composer": "Da Gama/Lazao/Toni Garrido",
            "milliseconds": 282174,
            "bytes": 9344477,
            "unitPrice": 0.99
        },
        "isDiscontinued": false
    }
]
```

#### *PUT Discontinued*

URL endpoint: /chinook/track/discontinue/update/{trackId}

Response: Changes the value of the isDiscontinued for a specific TrackID

Example for trackId: 300:

```json
{
    "isDiscontinued": false
}
```

#### *DELETE Discontinued*

URL endpoint: /chinook/track/discontinue/delete/{trackId}

Response: Deletes a row in the Discontinued table through a specific TrackID

Example for trackId: 300:

```json
Row with : 300 deleted.
```

<p align="right">(<a href="#top">back to top</a>)</p>

### **Invoice**

#### *GET all invoices*

URL endpoint: chinook/invoices

Response: list of all invoices

```json
[
  {
    "id": 1,
    "invoiceDate": "2009-01-01T00:00:00Z",
    "billingAddress": "Theodor-Heuss-Straße 34",
    "billingCity": "Stuttgart",
    "billingState": null,
    "billingCountry": "Germany",
    "billingPostalCode": "70174",
    "total": 1.98
  },
  {
    "id": 2,
    "invoiceDate": "2009-01-02T00:00:00Z",
    "billingAddress": "Ullevålsveien 14",
    "billingCity": "Oslo",
    "billingState": null,
    "billingCountry": "Norway",
    "billingPostalCode": "0171",
    "total": 3.96
  }
]
```

#### *GET invoice by ID*

URL endpoint: /chinook/invoice/{invoiceId}

Response: invoice for given ID

Example for invoice with ID: **11**

```json
{
  "id": 11,
  "invoiceDate": "2009-02-03T00:00:00Z",
  "billingAddress": "4 Chatham Street",
  "billingCity": "Dublin",
  "billingState": "Dublin",
  "billingCountry": "Ireland",
  "billingPostalCode": null,
  "total": 5.94
}
```

#### *POST invoice*

URL endpoint: /chinook/invoice/add

Response: Insert a new invoice into the invoice table

Example for invoice in json format:

```json
{
  "customerId": 2,
  "invoiceDate": "2009-02-03T00:00:00Z",
  "billingAddress": "3 Chatham Street",
  "billingCity": "Dublin",
  "billingState": "Dublin",
  "billingCountry": "Ireland",
  "billingPostalCode": null,
  "total": 5.94
}
```

#### *PUT invoice*

URL endpoint: /chinook/invoice/update

Response: Update a new invoice into the invoice table

Example for invoice in json format with ID: **11**

```json
{
  "id": 11,
  "customerId": 3,
  "invoiceDate": "2009-02-03T00:00:00Z",
  "billingAddress": "4 Chatham Street",
  "billingCity": "Dublin",
  "billingState": "Dublin",
  "billingCountry": "Ireland",
  "billingPostalCode": null,
  "total": 5.94
}
```

<p align="right">(<a href="#top">back to top</a>)</p>

### **Invoiceline**

#### *GET all invoicelines*

URL endpoint: chinook/invoicelines

Response: list of all invoicelines

```json
[
  {
    "id": 1,
    "invoiceId": 1,
    "trackId": 2,
    "unitPrice": 0.99,
    "quantity": 1
  },
  {
    "id": 2,
    "invoiceId": 1,
    "trackId": 4,
    "unitPrice": 0.99,
    "quantity": 1
  }
]
```

#### *GET invoicelines by ID*

URL endpoint: chinook/invoiceline/{id}

Response: invoiceline for given ID

Example for invoice with ID: **2**

```json
{
  "id": 2,
  "invoiceId": 1,
  "trackId": 4,
  "unitPrice": 0.99,
  "quantity": 1
}
```

#### *GET invoicelines by invoiceID*

URL endpoint: chinook/invoiceline/invoice/{id}

Response: invoicelines for a given invoiceID

Example of the invoicelines for the invoiceID: **1**

```json
[
  {
    "id": 1,
    "invoiceId": 1,
    "trackId": 2,
    "unitPrice": 0.99,
    "quantity": 1
  },
  {
    "id": 2,
    "invoiceId": 1,
    "trackId": 4,
    "unitPrice": 0.99,
    "quantity": 1
  }
]
```

<p align="right">(<a href="#top">back to top</a>)</p>

#### *POST track into invoiceline*

URL endpoint: chinook/invoiceline/track/add?costumerId=1&trackId=1

Response: added track to an invoiceline

Example of an invoiceline when a trackId has been selected by a customer

```json
{
  "id": 2258,
  "invoiceId": 422,
  "trackId": 1,
  "unitPrice": 0.99,
  "quantity": 1
}
```

#### *POST Album into invoiceline*

URL endpoint: chinook/invoiceline/album/add?costumerId=1&albumId=1

Response: added all the tracks from an album to the invoiceline

Example of the invoiceline when an albumId(1) has been selected by a customer

```json
[
  {
    "id": 2259,
    "invoiceId": 422,
    "trackId": 1,
    "unitPrice": 0.99,
    "quantity": 1
  },
  {
    "id": 2260,
    "invoiceId": 422,
    "trackId": 6,
    "unitPrice": 0.99,
    "quantity": 1
  }
]
```

#### *POST Playlist into invoiceline*

URL endpoint: chinook/invoiceline/playlist/add?costumerId=1&playlistId=12

Response: added all the tracks from a playlist to the invoiceline

Example of the invoiceline when an playistId (12) has been selected by a customer

```json
[
  {
    "id": 2269,
    "invoiceId": 422,
    "trackId": 12,
    "unitPrice": 0.99,
    "quantity": 1
  },
  {
    "id": 2270,
    "invoiceId": 422,
    "trackId": 12,
    "unitPrice": 0.99,
    "quantity": 1
  }
]
```

#### *PUT Update an invoiceline*

URL endpoint: chinnok/invoiceline/update

Response: Update an existing valid invoiceline 

Example for invoiceline in json format with ID: **11**


```json
{
"invoiceId": 3,
"trackId": 30,
"unitPrice": 0.99,
"quantity": 1
}
```

#### *DELETE delete an invoiceline*

URL endpoint: chinnok/invoiceline/delete/{id}

Response: Delete an existing invoiceline

Example for invoiceline deleted in json format with ID: **10**

```json
{
  "Deleted": true
}
```

<p align="right">(<a href="#top">back to top</a>)</p>

### **Artists**

#### *Get All artists*

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

#### *GET Artist by ID*

URL endpoint: /chinook/artist/{artistId}

Response: artist for given ID

Example for artist with ID: 22

```json
{
    "id": 22,
    "name": "Led Zeppelin"
}
```

#### *POST artist*

URL endpoint: /chinook/artist/add -requires body to send with request

Response: details of added artist

Example body of new artist:

```json
{
    "name": "Madrugada"
}
```

#### *PUT artist*

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




### **Album**

**Album**


#### *Get All albums*

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

#### *Get Album by ID*

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

#### *Put Album*

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

### **Employee**

#### *Get all employees*

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

#### *GET employee by ID*

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

#### *POST employee*

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

#### *PUT employee*

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

### **Genre**

#### *Get all genres*

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

#### *Get genre by ID*

URL endpoint: /chinook/genre/{genreId}

Response: genre for given ID

Example for genre with ID: 15

```json
{
    "id": 15,
    "name": "Electronica/Dance"
}
```

#### *POST genre*

URL endpoint: /chinook/genre/add

Response: Inserts a new  genre into the Genre Table

Example for genre in json format:

```json
{
    "name": "Trip Hop"
}
```

<p align="right">(<a href="#top">back to top</a>)</p>


### **Mediatype**

### *Get all mediatypes*

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

#### *Getmediatype by ID*

URL endpoint: /chinook/mediatype/{mediatypeId}

Response: mediatype for given ID

Example for mediatype with ID: 5

```json
{
    "id": 5,
    "name": "AAC audio file"
}
```

#### *POST mediatype*

URL endpoint: /chinook/mediatype/add

Response: Inserts a new mediatype into the Mediatype Table

Example for mediatype in json format:

```json
{
    "name": "FLAC audio file"
}
```

<p align="right">(<a href="#top">back to top</a>)</p>

### **Playlists**

#### *Get all playlists*

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

#### *GET playlist by ID*

URL endpoint: /chinook/playlist/{playlistId}

Response: playlist for given ID

Example for playlist with ID: 6

```json
{
    "id": 6,
    "name": "Audiobooks"
}
```

#### *POST playlist*

URL endpoint: /chinook/playlist/add

Response: Inserts a new playlist into the Playlist Table

Example for playlist in json format:

```json
{
    "name": "Road Trip"
}
```

#### *PUT playlist*

URL endpoint: /chinook/playlist/update/{playlistId}

Response: Updates an existing playlist by ID

Example for playlist of ID: 15, being updated with:

```json
{
        "name": "On-The-Go"
}
```

#### DELETE playlist

URL endpoint: /chinook/playlist/delete/{playlistId}

Response: Deletes an existing playlist by ID

```json
Playlist deleted
```



<p align="right">(<a href="#top">back to top</a>)</p>

### **Playlist Tracks**

#### *GET all playlist tracks*

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

#### *GET specific track from specific playlist*

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

#### *POST specific track in playlist*

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

#### *PUT playlist track*

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

#### DELETE playlist track

URL endpoint: /chinook/playlisttrack/delete

Response: deleting a specified playlist

```json
Playlist Track Deleted
```



<p align="right">(<a href="#top">back to top</a>)</p>

### **Popularity functions**

#### *GET top 5 tracks*

URL endpoint: chinook/tracks/top5

Response: list of the top 5 tracks based on number of sales

Example

```json
[
    "The Trooper",
    "Dazed and Confused",
    "Hallowed Be Thy Name",
    "The Number Of The Beast",
    "Sure Know Something"
]
```

#### *GET top 5 albums*

URL endpoint: chinook/albums/top5

Response: list of the top 5 albums based on number of sales

Example

```json
[
  "Minha Historia",
  "Greatest Hits",
  "Unplugged",
  "Acústico",
  "Greatest Kiss"
]
```

#### *GET top 5 genres*

URL endpoint: chinook/albums/top5

Response: list of the top 5 genres based on number of sales

Example

```json
[
  "Rock",
  "Latin",
  "Metal",
  "Alternative & Punk",
  "Jazz"
]
```

#### *GET top 5 artists*

URL endpoint: chinook/albums/top5

Response: list of the top 5 artists based on number of sales

Example

```json
[
  "Iron Maiden",
  "U2",
  "Metallica",
  "Led Zeppelin",
  "Os Paralamas Do Sucesso"
]
```
#### *Search in Artist, Album & Playlist  for occurrences of {word} (Work in Progress)*

URL endpoint: chinook/search/{word}

Response: list of occurrences of {word}

Example 1: {word} = mo

```json
{
        "id": 173,
        "title": "No More Tears (Remastered)",
        "artistId": {
            "id": 114,
            "name": "Ozzy Osbourne"
        }
    },
    {
        "id": 282,
        "title": "Mozart: Wind Concertos",
        "artistId": {
            "id": 216,
            "name": "Berliner Philharmoniker, Claudio Abbado & Sabine Meyer"
        }
    },
    {
        "id": 317,
        "title": "Mozart Gala: Famous Arias",
        "artistId": {
            "id": 249,
            "name": "Sir Georg Solti, Sumi Jo & Wiener Philharmoniker"
        }
    },
    {
        "id": 345,
        "title": "Monteverdi: L'Orfeo",
        "artistId": {
            "id": 273,
            "name": "C. Monteverdi, Nigel Rogers - Chiaroscuro; London Baroque; London Cornett & Sackbu"
        }
    },
    {
        "id": 204,
        "title": "Morning Dance",
        "artistId": {
            "id": 53,
            "name": "Spyro Gyra"
        }
    }
```

Example 2: {word} = san

```json
{
        "id": 67,
        "name": "Santana Feat. Eric Clapton"
    },
    {
        "id": 101,
        "name": "Lulu Santos"
    },
    {
        "id": 242,
        "name": "Edo de Waart & San Francisco Symphony"
    },
    {
        "id": 245,
        "name": "Michael Tilson Thomas & San Francisco Symphony"
    },
    {
        "id": 63,
        "name": "Santana Feat. Lauryn Hill & Cee-Lo"
    }
```

<p align="right">(<a href="#top">back to top</a>)</p>

---


## Credits and Contact

[Kamil](https://github.com/rwenmax) • [Konrad](https://github.com/KonradDlugosz) • [Talal](https://github.com/talal1998) • [Alex](https://github.com/alexsusanu) • [Anthony](https://github.com/MagerXser) • [Ed](https://github.com/EdBencito) • [Nikolas](https://github.com/Moodhunter34) • [Ishmael](https://github.com/ishariffSG) • [Mihai](https://github.com/Ascerte)  

---

## License

**Free**
