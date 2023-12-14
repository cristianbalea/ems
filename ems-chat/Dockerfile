FROM openjdk:17-jdk-alpine
COPY ems-chat-app/build/libs/ems-chat-app-1.0-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=dev"]