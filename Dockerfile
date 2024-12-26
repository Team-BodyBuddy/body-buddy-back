FROM gradle:8.12-jdk-alpine
WORKDIR /app
COPY ./ ./
CMD java -jar *.jar
