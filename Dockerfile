FROM openjdk:11-jre-slim
COPY ./target/*.jar /Documents/mydocker/twstock.jar
WORKDIR /Documents/mydocker
ENTRYPOINT ["java","-jar","twstock.jar"]