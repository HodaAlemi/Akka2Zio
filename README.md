## Introduction
An example of migration of Classic Akka to Zio. Master branch has Classic Akka implementation.


## Running the application
The build tool is ```sbt``` and server will be available at http://localhost:9001/

## Post endpoint json example
```
{
    "id": 1,
    "items": [
        {
            "id": 12,
            "name":"pizza",
            "amount": 1,
            "price" : 20.5
        }, {
            "id": 13,
            "name":"drink",
            "amount": 1,
            "price" : 2.0
        }],
    "totalPrice": 22.5,
    "date": "2023-02-25"
    } 
```

