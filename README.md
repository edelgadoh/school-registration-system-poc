# school-registration-system-poc
Java Demo Application with SpringBoot for Course and Student API (CRUD) and Enrollment API.

## Build + Tests
To build locally and to validate all requirements automatically, run the Unit tests and Functional Tests with:
```
./gradlew clean build
```

## Docker-compose
The Dockerfile is instructed to build the application, create the fat jar and 
let it ready to be used as an image.

To start the mysql & application containers in one shot, just execute:
```
docker-compose up -d
```

 
If there is a need to rebuild the application, just include --build in the command:
```
docker-compose up --build
```

To stop all the containers, execute:
```
docker-compose down
```

## Documentation

The REST API documentation is generated automatically by Swagger 2, 
and it's available at http://localhost:8080//swagger-ui/#/

![API Screenshot](screenshot-doc-api.png?raw=true)


## Notes:
All the project was tested on:

- docker version 20.10.13
- docker-compose version 1.29.2
- openjdk 11.0.13 2021-10-19
