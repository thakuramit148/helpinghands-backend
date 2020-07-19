FROM openjdk:8-alpine

ARG JAR_FILE="api-gateway.jar"

COPY target/$JAR_FILE $JAR_FILE

ENTRYPOINT java -jar `ls | grep *.jar`
