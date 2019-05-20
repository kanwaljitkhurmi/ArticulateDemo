FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/articulate-1.0.jar articulate.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java -jar /articulate.jar --debug
EXPOSE 8080