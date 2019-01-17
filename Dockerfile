FROM openjdk:8-jdk-alpine
LABEL maintainer=“rjformagio@gmail.com”
VOLUME /tmp
ARG JAR_FILE=target/wirecard-challenge-1.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]