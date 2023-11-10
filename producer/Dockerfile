FROM maven:3.6.3-openjdk-17-slim AS build-stage
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip

FROM openjdk:17-alpine AS production-stage
WORKDIR /app
COPY --from=build-stage /home/app/target/producer.jar .
ENV PORT=8082
EXPOSE 8082
CMD ["java", "-jar", "producer.jar"]