FROM openjdk:17
ARG DATABASE
WORKDIR /
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT java -XX:MaxRAMPercentage=100 -XX:MinRAMPercentage=100 -XshowSettings:vm -version && java -XX:MaxRAMPercentage=100 -XX:MinRAMPercentage=100 -Ddatabase.url=${DATABASE} -jar /app.jar