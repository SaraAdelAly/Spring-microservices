FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

#copy the pom file into the working directory which is /app
COPY pom.xml .

#cache the dependency unless a change has been detected
RUN mvn dependency:go-offline -B

 COPY src ./src

#package everything in a single jar
RUN mvn clean package

#use the openJDK runtime to run the built code (by maven{the builder})
FROM openjdk:17-jdk AS ruuner

WORKDIR /app

#it's going to copy the patient service snapshot jar that was created in the builder step into the runner step and place it at ./app.jar
COPY --from=builder ./app/target/patient-service-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 4000

ENTRYPOINT ["java", "-jar", "app.jar"]



