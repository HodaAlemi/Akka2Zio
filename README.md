## Introduction
An example of migration of Classic Akka to Zio. Master branch has Classic Akka implementation.


## Running the application
The build tool is ```sbt``` and server will be available at http://localhost:9001/

## POST request json payload example
```
{
    "item":"milk",
    "price" : 1.5
}
```

## GET request response example
````
{
    "orders": [
        {
            "item": "milk",
            "price": 1.5
        },
        {
            "item": "chips",
            "price": 2.0
        }
    ]
}
````