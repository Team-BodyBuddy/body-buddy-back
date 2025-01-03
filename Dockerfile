FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY .demo//build/libs/*.jar app.jar
#COPY ./build/libs/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
