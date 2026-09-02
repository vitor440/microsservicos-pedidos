FROM maven:4.0.0-rc-5-amazoncorretto-21 AS build

WORKDIR build

COPY . ./

RUN mvn clean package -DskipTests


FROM amazoncorretto:21.0.12

WORKDIR app

COPY --from=build /build/target/*.jar /app.jar

ENV DB_URL=''
ENV DB_USERNAME=''
ENV DB_PASSWORD=''
#ENV KEYCLOACK_URL=''
ENV JWK_URI=''
ENV EUREKA_URL=''
ENV CLIENT_ID=''
ENV KAFKA_URL=''

EXPOSE 8000

CMD ["java", "-jar", "/app.jar"]