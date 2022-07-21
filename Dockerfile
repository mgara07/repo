FROM openjdk:8-jdk-alpine3.8
ENV MYSQL_HOST=mysql
EXPOSE 8080
EXPOSE 3306
ARG JAR_FILE=target/softib-*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
