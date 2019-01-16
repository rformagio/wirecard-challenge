# WireCard Challenge

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

## What was developement

http://localhost:8080/services/payments/2/status

http://localhost:8080/services/payments

```json
{
    "paymentId": 1,
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

## Architeture

## TO-DO
