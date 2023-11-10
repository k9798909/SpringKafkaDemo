FROM maven:3.6.3-openjdk-17-slim AS build-stage
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip

FROM openjdk:17-alpine AS production-stage
WORKDIR /app
COPY --from=build-stage /home/app/target/consumer.jar .
ENV PORT=8081
EXPOSE 8081
CMD ["java", "-jar", "consumer.jar"]