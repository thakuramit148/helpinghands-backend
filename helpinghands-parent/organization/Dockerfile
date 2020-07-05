FROM openjdk:8

ARG JAR_FILE="organization.jar"

EXPOSE 8083

COPY target/$JAR_FILE $JAR_FILE

ENTRYPOINT java -jar `ls | grep *.jar`
