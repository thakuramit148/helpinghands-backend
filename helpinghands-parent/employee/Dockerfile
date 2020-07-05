FROM openjdk:8

ARG JAR_FILE="employee.jar"

EXPOSE 8084

COPY target/$JAR_FILE $JAR_FILE

ENTRYPOINT java -jar `ls | grep *.jar`
