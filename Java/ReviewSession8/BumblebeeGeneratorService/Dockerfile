FROM openjdk:8-jdk-alpine

ENV PROFILE docker

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} generator_service-1.0.jar

EXPOSE 8080:8080
EXPOSE 8090:8090

ENTRYPOINT exec java $JAVA_OPTS -Dspring.profiles.active=$PROFILE -jar generator_service-1.0.jar