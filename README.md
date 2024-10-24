# Set up

## Pre-required
- Java 17
- Maven
- Docker

# Commands to build and run locally
```
$ mvn -f ./freelancer/pom.xml clean install
$ mvn -f ./notification/pom.xml clean install
$ docker-compose up
```

# System Design Overview

The solution consists of two services: the Freelancer Service and the Notification Service. These services communicate asynchronously using RabbitMQ as the message broker.

## Freelancer Service

The Freelancer Service is responsible for managing freelancer information, including storing and retrieving data from an H2 database. It also functions as a producer in the messaging system.
- Database (H2): Used to store and manage freelancer records.
- Producer Role: Whenever freelancer information is updated (e.g., new entries, updates to existing records), the service publishes messages containing the updated data to a RabbitMQ queue, which is consumed by the Notification Service.
- Security Configuration: Implemented a simple security setup using in-memory users for authentication and authorization. Freelancer service is used by freelancer users (users with role FREELANCER, login/password user/user) and staff users (users with role STAFF, login/password admin/admin).

## Notification Service

The Notification Service acts as a consumer, retrieving messages from the RabbitMQ queue and handling notifications accordingly.

 - Consumer Role: The service listens to the RabbitMQ queue. When a message containing updated freelancer information is received, it processes the message and stores the relevant data in its own H2 database for further use.
 - Database (H2): Used to store notification-related data or any processed information received from the Freelancer Service.

# Request samples

## Notification service

Exposed endpoint to retrieve all notifications
```
curl --location 'http://localhost:8081/notifications' \
```

## Freelancer service

Freelancer service is used by freelancer users (users with role FREELANCER, login/password user/user) and staff users (users with role STAFF, login/password admin/admin)

### Freelancer users

Create a new freelancer
```
curl --location 'http://localhost:8080/freelancers' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic dXNlcjp1c2Vy' \
--data '{
    "firstName": "Marry",
    "lastName": "Black",
    "dateOfBirth": "11-02-1990",
    "gender": "female"
}'
```

Update a freelancer
```
curl --location --request PUT 'http://localhost:8080/freelancers/48a24ac7-bb1c-4dff-a00b-e8a03b48ff6c' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic dXNlcjp1c2Vy' \
--data '{
    "firstName": "Marry",
    "lastName": "Black",
    "dateOfBirth": "13-02-1992",
    "gender": "female"
}'
```
Delete a freelancer
```
curl --location --request DELETE 'http://localhost:8080/freelancers/48a24ac7-bb1c-4dff-a00b-e8a03b48ff6c' \
--header 'Authorization: Basic dXNlcjp1c2Vy'
```

### Staff users
Get all the newly registered freelancers
```
curl --location 'http://localhost:8080/freelancers/new' \
--header 'Authorization: Basic YWRtaW46YWRtaW4='
```
Update the freelancer’s status to VERIFIED
```
curl --location --request PUT 'http://localhost:8080/freelancers/48a24ac7-bb1c-4dff-a00b-e8a03b48ff6c/verify' \
--header 'Authorization: Basic YWRtaW46YWRtaW4='
```
Get all deleted freelancer in last 7 days
```
curl --location 'http://localhost:8080/freelancers/deleted' \
--header 'Authorization: Basic YWRtaW46YWRtaW4='
```

# Swagger docs

```
http://localhost:8080/v3/api-docs
http://localhost:8080/swagger-ui/index.html
```


