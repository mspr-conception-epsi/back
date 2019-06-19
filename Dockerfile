FROM maven:3.5-jdk-8-alpine as build

WORKDIR /app

COPY . /app
RUN ls -la
RUN mvn install

FROM openjdk:8-jre-alpine

WORKDIR /app

COPY --from=build /app/target/mspr-api-0.0.1-SNAPSHOT.jar /app

CMD ["java -jar mspr-api-0.0.1-SNAPSHOT.jar"]