FROM openjdk:8

ARG JAR_FILE="api-gateway.jar"

COPY target/$JAR_FILE $JAR_FILE

ENTRYPOINT java -jar `ls | grep *.jar`
