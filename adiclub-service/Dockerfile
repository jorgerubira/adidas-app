FROM openjdk:11-jdk

RUN mkdir /opt/app
COPY target/*.jar /opt/app/app.jar
CMD ["java", "-Xmx500m", "-jar", "/opt/app/app.jar"]