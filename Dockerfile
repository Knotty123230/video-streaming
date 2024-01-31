FROM openjdk:17
WORKDIR /authorization-server-api
COPY ./build/libs/*.jar app.jar
EXPOSE 0
ENTRYPOINT ["java", "-jar", "app.jar"]
