FROM eclipse-temurin:21-jre-alpine as builder
COPY build/libs/products*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./

ENV DATABASE_HOST="172.28.140.162"
ENV DATABASE_PORT="31433"
ENV DATABASE_USERNAME="sa"
ENV DATABASE_PASSWORD="Bison2024"
ENV OTEL_COLLECTOR="http://172.28.140.162:4318"

ENV JAVA_OPTS="-XX:MaxRAMPercentage=80 -XX:+UseG1GC -XX:+UseStringDeduplication"

ENTRYPOINT ["sh", "-c", "java -Djava.security.egd='file:/dev/./urandom' -Duser.timezone='Europe/Zurich' $JAVA_OPTS org.springframework.boot.loader.launch.JarLauncher"]