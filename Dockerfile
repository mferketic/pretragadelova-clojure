FROM openjdk:8-alpine

COPY target/uberjar/pretragadelova.jar /pretragadelova/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/pretragadelova/app.jar"]
