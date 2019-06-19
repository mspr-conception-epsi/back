FROM maven:3.6.0-jdk-8 AS build
WORKDIR /app
COPY . .
RUN mvn clean install
FROM openjdk:8-jdk
COPY --from=build target/mspr-api-0.0.1-SNAPSHOT.jar /usr/local/lib/pharmacy-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/pharmacy-api.jar"]