FROM  openjdk:18-alpine
WORKDIR /app
COPY target/edVantage-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]