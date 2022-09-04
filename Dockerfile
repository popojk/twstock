FROM openjdk:11-jre-slim
COPY ./target/*.jar /Documents/mydocker/twstock.jar
# copy the Nginx config

WORKDIR /Documents/mydocker
ENTRYPOINT ["java","-jar","twstock.jar"]