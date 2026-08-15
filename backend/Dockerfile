# Multi-stage Dockerfile for BeautyStor backend (Java 21)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /workspace
# cache mvn deps by copying only pom first
COPY pom.xml ./
COPY src ./src
RUN mvn -B -DskipTests package -DskipImages

FROM eclipse-temurin:21-jre
WORKDIR /app
# copy fat jar produced by spring-boot-maven-plugin
COPY --from=build /workspace/target/*.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
