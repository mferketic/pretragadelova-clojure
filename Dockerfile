FROM openjdk:8-alpine

COPY target/uberjar/carservice.jar /carservice/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/carservice/app.jar"]
