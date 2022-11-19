FROM adoptopenjdk/openjdk11:latest

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} file.jar
ENTRYPOINT ["java","-jar","/file.jar"]

EXPOSE 8080:8080