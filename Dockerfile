#베이스 도커 이미지
FROM openjdk:17-jdk-alpine

#작업 디렉토리
WORKDIR /app

#demo 폴더에 있는 "gradle clean bootJar" 결과를 도커 컨테이너 내부에 복사
COPY ./demo/build/libs/*.jar app.jar

#"gradle clean bootJar" 결과를 도커 컨테이너 내부에 복사
#COPY ./build/libs/*.jar app.jar

#시스템 환경변수 가져오기
ARG MYSQL_URL
ARG MYSQL_PORT
ARG MYSQL_USER
ARG MYSQL_PW

#DB 접속을 위한 환경변수 설정
ENV MYSQL_URL ${MYSQL_URL}
ENV MYSQL_PORT ${MYSQL_PORT}
ENV MYSQL_USER ${MYSQL_USER}
ENV MYSQL_PW ${MYSQL_PW}

#서비스 포트 실행
EXPOSE 8080

#spring boot 실행
CMD ["java", "-jar", "app.jar"]
