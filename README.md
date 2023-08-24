## Introduction
An example of migration of classic Akka to Zio. ``Main`` branch has classic Akka implementation and ``ZIO`` branch has ZIO implementation of the same application.


## Running the application
The build tool is ```sbt``` and server will be available at http://localhost:9001/

## POST request json payload example
```
{
    "item":"milk",
    "price" : 1.5
}
```

## GET response example
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
