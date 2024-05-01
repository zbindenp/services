FROM eclipse-temurin:21-jre-alpine

COPY build/libs/main*.jar /app/server.jar


ENV DATABASE_HOST="172.28.140.162"
ENV DATABASE_PORT="31433"
ENV DATABASE_USERNAME="sa"
ENV DATABASE_PASSWORD="Bison2024"
ENV OTEL_COLLECTOR="http://172.28.140.162:4318/v1/traces"
ENV PRODUCTS_BASE_URL="http://products:8081"
ENV CUSTOMERS_BASE_URL="http://customers:8082"

ENV JAVA_OPTS="-XX:MaxRAMPercentage=80 -XX:+UseG1GC -XX:+UseStringDeduplication"

ENTRYPOINT ["sh", "-c", "java -Djava.security.egd='file:/dev/./urandom' -Duser.timezone='Europe/Zurich' $JAVA_OPTS -jar /app/server.jar"]