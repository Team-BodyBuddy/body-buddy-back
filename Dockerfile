FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY ./*.jar ./
CMD java -jar *.jar
