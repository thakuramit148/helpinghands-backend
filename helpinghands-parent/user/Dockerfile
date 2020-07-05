FROM openjdk:8

ARG JAR_FILE="user.jar"

EXPOSE 8081

COPY target/$JAR_FILE $JAR_FILE

ENTRYPOINT java -jar `ls | grep *.jar`
