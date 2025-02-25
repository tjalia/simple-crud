FROM eclipse-temurin:17-jdk-alpine

COPY target/userprofile-0.0.1-SNAPSHOT.jar app/userprofile-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app/userprofile-0.0.1-SNAPSHOT.jar"]