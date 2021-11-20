FROM adoptopenjdk/openjdk11:alpine-jre
COPY /target/BattleShipBot-1.0.jar /app/app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app/app.jar"]