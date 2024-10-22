FROM openjdk:17-oracle
LABEL maintainer="lizaveta.arkhanhelskaya@gmail.com"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/freelancer-1.0.0.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]