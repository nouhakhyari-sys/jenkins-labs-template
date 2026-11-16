# Image of the calculator web app
# Start from an image that already contains Java 21 (runtime only)
FROM eclipse-temurin:21-jre

# The version is given by Jenkins: docker build --build-arg APP_VERSION=1.0.12 ...
ARG APP_VERSION=dev
ENV APP_VERSION=${APP_VERSION}

WORKDIR /app
# Copy the jar built by the pipeline (stage Package) into the image
COPY build/calculator-web.jar app.jar

# The app listens on port 8080 inside the container
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
