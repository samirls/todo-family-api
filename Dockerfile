FROM openjdk:17-jdk-alpine3.13

EXPOSE 9000
WORKDIR /app

ENV DATABASE_CONNECTION_URL=""
ENV SCOPE="prod"
ENV PASSWORD=""
ENV USER=""

COPY build/libs/todofamily.jar /app/todofamily.jar

ENTRYPOINT ["java", "-jar", "todofamily.jar"]
