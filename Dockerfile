FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/ReconcileAuditSystem-0.0.1-SNAPSHOT.jar /app/ReconcileAuditSystem-0.0.1-SNAPSHOT.jar
EXPOSE 8085

LABEL authors="HarshVardhan"

ENTRYPOINT ["java", "-jar", "ReconcileAuditSystem-0.0.1-SNAPSHOT.jar"]