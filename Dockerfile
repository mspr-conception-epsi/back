FROM maven:3.6.0-jdk-8 AS build
RUN git pull https://github.com/mspr-conception-epsi/back.git /home/app/
RUN mvn -f /home/app/back/pom.xml clean package

FROM openjdk:8-jdk
COPY --from=build /home/app/back/target/mspr-api-0.0.1-SNAPSHOT.jar /usr/local/lib/pharmacy-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/pharmacy-api.jar"]