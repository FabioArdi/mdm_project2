FROM openjdk:17

WORKDIR /
COPY . .

EXPOSE 8080

CMD ["java", "-jar", "target/project2-0.0.1-SNAPSHOT.jar"]