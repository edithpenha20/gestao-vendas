FROM openjdk:17-jdk-alpine
EXPOSE 8080
WORKDIR /app
COPY /target/gestao-vendas-0.0.1-SNAPSHOT.jar  /app/gestao_vendas.jar
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar .
ENV JAVA_TOOL_OPTIONS "-javaagent:./opentelemetry-javaagent.jar"
CMD ["java", "-jar", "/app/gestao_vendas.jar"]