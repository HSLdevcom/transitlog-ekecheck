FROM openjdk:8-jre-slim
#Install curl for health check
RUN apt-get update && apt-get install -y --no-install-recommends curl
ADD build/libs/transitlog-ekecheck.jar /usr/app/transitlog-ekecheck.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xms256m", "-Xmx4096m", "-jar", "/usr/app/transitlog-ekecheck.jar"]
