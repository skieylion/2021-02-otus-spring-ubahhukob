FROM adoptopenjdk/openjdk11:alpine-jre
COPY /target/BattleShipEngine-1.0.jar /app/app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/app.jar"]