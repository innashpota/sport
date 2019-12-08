FROM openjdk:8-jdk-alpine AS BUILD_IMAGE
COPY build.gradle gradlew settings.gradle /
COPY src src
COPY gradle gradle
RUN ./gradlew build -x test

FROM openjdk:8-jre-alpine
COPY --from=BUILD_IMAGE /build/libs/sport*.jar app.jar
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]