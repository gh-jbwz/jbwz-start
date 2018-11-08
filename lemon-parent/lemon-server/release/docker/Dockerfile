FROM openjdk:8
ENV appName=lemon-server
COPY ./jar/${appName}.jar /jbwz/web/app.jar
WORKDIR /jbwz
#-Djava.security.egd=file:/dev/./urandom  make Tomcat startup faster
ENTRYPOINT ["java","-jar","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=pro","web/app.jar"]
