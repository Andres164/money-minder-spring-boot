FROM openjdk:21-slim
ENV PORT 8080
EXPOSE 8080
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]