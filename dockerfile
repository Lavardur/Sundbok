# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-24 AS build
WORKDIR /app

# Cache deps
COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline

# Build
COPY src ./src
RUN mvn -B -q -DskipTests package

# ---- Runtime stage ----
FROM eclipse-temurin:24-jre
WORKDIR /app

# If your JAR name differs, adjust the pattern or set <finalName>app</finalName> in pom.xml
COPY --from=build /app/target/*-SNAPSHOT.jar /app/app.jar

# Good container defaults
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
