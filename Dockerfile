FROM maven:3.9.8-eclipse-temurin-17

WORKDIR /app

COPY . /app

RUN mvn clean package

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/target/health-care-0.0.1-SNAPSHOT.jar"]