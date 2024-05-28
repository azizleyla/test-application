FROM openjdk:22-oracle
ARG JAR_FILE=unitech/target/*.jar
COPY ${JAR_FILE} unitech.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar", "/unitech.jar"]