# Builds up an image
#
# 2020-04-20 Vasily Eremenko <eremenko.vo@pecom.ru>

FROM openjdk:8-jdk

WORKDIR /app

ADD target/event-manager-web-1-SNAPSHOT.jar .
ADD devtools/application.properties .
ADD devtools/logback.xml .

EXPOSE 8080
EXPOSE 8443
EXPOSE 9990

CMD ["java", \
"-Duser.timezone=GMT+03:00", \
"-Dpecom.logging.category=event-manager-web", \
"-Dpecom.logging.footprint.prefix=EMW", \
"-Dspring.config.additional-location=./application.properties", \
"-jar", "event-manager-web-1-SNAPSHOT.jar"]
