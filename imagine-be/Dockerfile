FROM eclipse-temurin:17-jre-alpine AS builder

COPY build/libs/imagine-be-1.0.0.jar imagine-be-1.0.0.jar

RUN java -Djarmode=layertools -jar ./imagine-be-1.0.0.jar extract

FROM eclipse-temurin:17-jre-alpine

COPY --from=builder /dependencies/ ./
COPY --from=builder /spring-boot-loader/ ./
COPY --from=builder /snapshot-dependencies/ ./
COPY --from=builder /application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]

