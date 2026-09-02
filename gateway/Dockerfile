FROM maven:4.0.0-rc-5-amazoncorretto-21 AS build

WORKDIR build

COPY . ./

RUN mvn clean package -DskipTests


FROM amazoncorretto:21.0.12

WORKDIR app

COPY --from=build /build/target/*.jar /app.jar

#ENV KEYCLOACK_URL=''
ENV EUREKA_URL=''
ENV CLIENT_ID=''
ENV JWK_URI=''

EXPOSE 8300

CMD ["java", "-jar", "/app.jar"]