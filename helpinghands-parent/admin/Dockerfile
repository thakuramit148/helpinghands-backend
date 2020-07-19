FROM openjdk:8-alpine

ARG JAR_FILE="admin.jar"

EXPOSE 8082 

COPY target/$JAR_FILE $JAR_FILE

ENTRYPOINT java -jar `ls | grep *.jar`
