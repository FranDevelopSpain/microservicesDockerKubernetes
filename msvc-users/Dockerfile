FROM openjdk:17.0.4

WORKDIR /app

COPY ./target/msvc-users-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "msvc-users-0.0.1-SNAPSHOT.jar"]

