#Use an official OpenJDK base image (Java 17, matching our project)

FROM openjdk:17-jdk-slim

#Set the working directory inside the container

WORKDIR /app

#Copy the Maven build file and source code

COPY pom.xml .
RUN mkdir -p ./src 
COPY src ./src

#Install Maven inside the container (slim image doesn't have it)

RUN apt-get update && apt-get install -y maven

#Build the application (creates the JAR file)

RUN mvn clean package -DskipTests

#Expose the app's port (8080, as in application.properties)

EXPOSE 8080

#Run the JAR file (target/TransactionFaultDetector-0.0.1-SNAPSHOT.jar)

CMD ["java", "-jar", "target/TransactionFaultDetector-0.0.1-SNAPSHOT.jar"]
