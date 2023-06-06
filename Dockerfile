FROM eclipse-temurin:19-alpine
COPY target/security-0.0.1-SNAPSHOT.jar security-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/security-0.0.1-SNAPSHOT.jar"]