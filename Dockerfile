FROM openjdk:17
COPY target/customer-service-0.0.1-SNAPSHOT.jar customer-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/customer-service.jar"]
