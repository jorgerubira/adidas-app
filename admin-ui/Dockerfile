FROM openjdk:11

RUN mkdir /opt/app
COPY target/*.jar /opt/app/app.jar
CMD ["java", "-Xmx500m", "-jar", "/opt/app/app.jar"]