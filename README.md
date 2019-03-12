#IMDb Querier

### Specs
scala : 2.11.12

play : 2.5.10

play-slick : 2.0.0


### Features

1. Find cast
2. Get ratings rank
3. Separation degree with Kevin Bacon

### Database

Use IMDb dataset. Some additional custom tables were created for data normalization. Please refer to 1.sql.

### API Guideline

##### 1. Finding information associated with title

Request:

```
POST /api/v1/search/title-info

{
    "title" : "Kate & Leopold"
}
```


Response: 
```
{
    "title": "Kate & Leopold",
    "genres": "Comedy,Fantasy,Romance",
    "start_year": 2001,
    "runtime_minutes": 118,
    "is_adult": false,
    "end_year": null,
    "tconst": 35423,
    "directors": [
        {
            "name": "James Mangold",
            "profession": "producer,director,writer",
            "birth_year": 1963,
            "death_year": null
        }
    ],
    "writers": [
        {
            "name": "James Mangold",
            "profession": "producer,director,writer",
            "birth_year": 1963,
            "death_year": null
        },
        {
            "name": "Steven Rogers",
            "profession": "writer,producer",
            "birth_year": null,
            "death_year": null
        }
    ],
    "actor_names": [
        {
            "name": "Hashim Al-Mashat",
            "profession": "camera_department",
            "birth_year": null,
            "death_year": null
        }
    ]
}
```

##### 2. Ratings rank by genre

Request:

```
POST /api/v1/search/ratings?offset=0&limit=5

{
	"genre" : "Comedy"
}
```


Response: 
```
[
    {
        "title": "El huésped del sevillano",
        "start_year": 1970,
        "end_year": null,
        "runtime": 86,
        "is_adult": false,
        "average_rating": 7,
        "num_votes": 6
    },
    {
        "title": "Kate & Leopold",
        "start_year": 2001,
        "end_year": null,
        "runtime": 118,
        "is_adult": false,
        "average_rating": 6.4,
        "num_votes": 73472
    }
]
```

##### 3. Degree with Kevin Bacon

Request:

```
POST /api/v1/search/degree

{
	"actor" : "Channing Tatum"
}
```


Response: 
```
{
    "degree" : 6
}
```
