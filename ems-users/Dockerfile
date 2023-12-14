FROM openjdk:17-jdk-alpine
COPY ems-users-app/build/libs/ems-users-app-1.0-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=dev"]