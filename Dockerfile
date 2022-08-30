FROM  openjdk:18-alpine as buildStep

WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests=true

FROM  openjdk:18-alpine as runStep
COPY --from=buildStep /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]