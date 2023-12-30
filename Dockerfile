FROM openjdk:17-jdk-alpine
EXPOSE 8080
WORKDIR /app
COPY gestao-vendas/target/*.jar  /app/gestao_vendas.jar
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar .
#ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/tree/main/instrumentation/jdbc.jar .
ENV JAVA_TOOL_OPTIONS "-javaagent:./opentelemetry-javaagent.jar"
CMD ["java", "-jar", "/app/gestao_vendas.jar"]