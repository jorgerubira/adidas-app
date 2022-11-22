FROM adoptopenjdk/openjdk11:latest

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} file.jar
ENTRYPOINT ["java","-Xmx500m","-jar","/file.jar"]

EXPOSE 8080:8080