FROM openjdk:17-jdk-alpine
COPY ems-monitor-app/build/libs/ems-monitor-app-1.0-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=dev"]