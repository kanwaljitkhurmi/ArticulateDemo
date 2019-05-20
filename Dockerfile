FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/articulateDemo-0.0.1.jar articulateDemo.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java -jar /articulateDemo.jar --debug
EXPOSE 8080