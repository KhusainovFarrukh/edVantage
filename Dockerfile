FROM  openjdk:18-alpine
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests=true
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]