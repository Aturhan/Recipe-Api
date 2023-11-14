FROM amazoncorretto:17-alpine AS Builder
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM amazoncorretto:17-alpine
WORKDIR recipe
COPY --from=Builder /target/*.jar recipe.jar
ENTRYPOINT ["java","-jar","recipe.jar"]