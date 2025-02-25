# SIMPLE-CRUD
Simple CRUD Operation

## Technologies

* Java 17
* Spring Framework
* Spring Boot
* Spring Security
* Spring Aspect
* H2 Database
* Lombok
* Mapstruct
* Junit5
* Mockito
* Docker

## HTTP Headers

* API requires client credentials to provide access:
    * `x-client-id: {clientId}`
    * `x-client-secret: {clientSecret}`

## HTTP Codes

Simple Crud returns the following status codes in its API:

* **200** - OK
* **201** - Created
* **400** - Bad Request
* **401** - Unauthorized
* **404** - Not Found
* **500** - Internal Server Error
