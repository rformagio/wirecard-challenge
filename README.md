# WireCard Challenge

## What was the challenge ?

To developement an API for payments. In this particular case, it was developed an API for two payment types:
- Credit Card
- Boleto

It was used *Spring Boot*.

It was developed 2 rest services:

##### 1) Create payments (POST)
```
http://localhost:8080/services/payments
```

This service creates payment. It can receive 2 types of *json*: 
- Credit Card Payment

``` json
{
    "paymentId": null,
    "clientId": 1, 
    "amount": 200, 
    "type": "CREDIT_CARD",
    "status": null,
    "buyer": {
        "buyerId": null,
        "name": "Antonio Carlos",
        "email": "antonio@gmail.com",
        "cpf": "1234567-9"
    },
    "holderName": "Antonio Carlos",
    "cardNumber": "378282246310005",
    "cvv": "4",
    "expirationDate": "2021-01-15",
    "issuer": null
}
```
  clientId, amount, type, name, email, cpf, holderName, cardNumber, cvv and expirationDate are mandatory !
  
  *paymentId* is created.
  
  *status* is returned depending on the process of the payment. The *issuer* is also returned depending on the card number.
  - Boleto Payment
  
  ```
  {
      "paymentId": null,
      "clientId": 1, 
      "amount": 200, 
      "type": "BOLETO",
      "status": null,
      "buyer": {
          "buyerId": null,
          "name": "Antonio Carlos",
          "email": "antonio@gmail.com",
          "cpf": "1234567-9"
      },
      "barCode": null,
  }
  ```
  clientId, amount, type, name, email, cpf are mandatory !
  
  *paymentId* is created.
  
  *status* and *barCode* are returned depending on the process of the payment.   
##### 2 ) Status (GET)
```
http://localhost:8080/services/payments/{paymentId}/status
```
This service verify the status of a payment. It returns a payment (Boleto or Credit Card).
It receives a *paymentId*. The paymentId can be obtained from the return of the payment service (a payment that was created previously)

For developement was used:
- Spring Boot
- Bean Validation
- ResponseStatusException
- Swagger
- Lombok

Also was developed the ``@CreditCardNumber`` annotation for validation of credit card number. It was used *moip* library.

## Getting started

#### What you'll need

- JDK 1.8 or later
- Maven 3.2

#### Installing and Running

In the root directory, execute the command:

```
$ mvn spring-boot:run
```

#### Running the Tests

```
mvn clean install
```

#### Testing

The project is using *SWAGGER*, so, if the application is running, you can visit the url:
```
http://localhost:8080/swagger-ui.html
```
Or

you can use same *curl* examples:

- Create a Credit Card Payment:

```
curl -X POST "http://localhost:8080/services/payments" -H  "accept: */*" -H  "Content-Type: application/json" -d "{    \"paymentId\": null,    \"clientId\": 1,     \"amount\": 200,     \"type\": \"CREDIT_CARD\",    \"status\": null,    \"buyer\": {        \"buyerId\": null,        \"name\": \"Antonio Carlos\",        \"email\": \"antonio@gmail.com\",        \"cpf\": \"1234567-9\"    },    \"holderName\": \"Antonio Carlos\",    \"cardNumber\": \"378282246310005\",    \"cvv\": \"4\",    \"expirationDate\": \"2021-01-15\",    \"issuer\": null}"
```

- Create a Boleto Payment:
```
curl -X POST "http://localhost:8080/services/payments" -H  "accept: */*" -H  "Content-Type: application/json" -d "{    \"paymentId\": null,    \"clientId\": 1,     \"amount\": 200,     \"type\": \"BOLETO\",    \"status\": null,    \"buyer\": {        \"buyerId\": null,        \"name\": \"Antonio Carlos\",        \"email\": \"antonio@gmail.com\",        \"cpf\": \"1234567-9\"    },   \"barCode\": null}"
```

- Verify status: (YOU MUST REPLACE {paymentID} )
```
curl -X GET "http://localhost:8080/services/payments/{paymentId}/status" -H  "accept: */*"
```

## TO-DO

 - Improve Bean Validation: 
    - apply to service and dao layers
    - CPF validation
 - Bar Code generator for Boleto Payment
 - Add security
 - Add logging
 
 ## PLUS: Docker
 
 It´s possible run the application in simple Docker container.
 
 If you have Docker installed, run the command:
 ```
 mvn clean install dockerfile:build
 ```
 
 This should create the image: ``rformagio/wirecard-challenge``
 And now, to put services up and running:
 ```
 docker run -p 8080:8080 --name wirecard rformagio/wirecard-challenge
 ```
 
 You can test !!! The *URL*s are the same. 
 ```
 http://localhost:8080/swagger-ui.html
 ```
 
 That´s it !!
 
 
 
 
