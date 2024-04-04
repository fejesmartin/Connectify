# Official Maven image as a parent image
FROM maven:eclipse-temurin AS build

# Set the working directory to /app
WORKDIR /app

# Copy the project files
COPY pom.xml .
COPY src ./src

RUN mvn install -DskipTests

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run app when the container launches
CMD ["java", "-jar", "target/connectify-0.0.1-SNAPSHOT.jar"]