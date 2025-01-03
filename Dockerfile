#베이스 도커 이미지
FROM openjdk:17-jdk-alpine

#작업 디렉토리
WORKDIR /app

#demo 폴더에 있는 "gradle clean bootJar" 결과를 도커 컨테이너 내부에 복사
COPY ./demo/build/libs/*.jar app.jar

#"gradle clean bootJar" 결과를 도커 컨테이너 내부에 복사
#COPY ./build/libs/*.jar app.jar

#서비스 포트 실행
EXPOSE 8080

#spring boot 실행
CMD ["java", "-jar", "app.jar"]
