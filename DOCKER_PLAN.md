Docker Containerization Plan

Goal
Package the Spring Boot REST API into a Docker container for easy deployment.

What We Did

Step 1: Created Dockerfile
We made a Dockerfile that uses OpenJDK 17 as the base image. The Dockerfile copies the JAR file into the container and sets it to run on port 8080.

Step 2: Built the Application
We used Maven to build the application into a JAR file using the clean package command.

Step 3: Built Docker Image
We used the docker build command to create a Docker image from our Dockerfile. This packages the application with all its dependencies.

Step 4: Tested Locally
We ran the Docker container locally to make sure the REST API works correctly. We tested the endpoints using Swagger UI.

Step 5: Published to Docker Hub
We tagged the image and pushed it to Docker Hub so anyone can pull and run our application.

Why Docker

Docker makes sure the application runs the same way on any machine. It includes everything the app needs so you don't have to install Java or configure anything manually.

Commands We Used

Build JAR: ./mvnw clean package -DskipTests

Build image: docker build -t employeetaskmanager-rest:1.0 .

Run container: docker run -d -p 8080:8080 --name employee-api employeetaskmanager-rest:1.0

Push to hub: docker push username/employeetaskmanager-rest:1.0

What We Learned

We learned how to containerize a Spring Boot application and deploy it using Docker. We also learned how to publish images to Docker Hub for sharing.