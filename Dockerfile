FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/jwt-app.war
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]