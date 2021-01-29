FROM openjdk:11.0-jdk-slim
COPY target/spring-movie-collection-0.0.1.jar app.jar
COPY movie-photos movie-photos
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]