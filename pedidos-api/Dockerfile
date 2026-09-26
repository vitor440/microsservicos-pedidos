FROM amazoncorretto:21.0.12

WORKDIR app

COPY ./target/*.jar /app.jar

ENV DB_URL=''
ENV DB_USERNAME=''
ENV DB_PASSWORD=''
#ENV KEYCLOACK_URL=''
ENV JWK_URI=''
ENV EUREKA_URL=''
ENV CLIENT_ID=''
ENV KAFKA_URL=''

EXPOSE 8100

CMD ["java", "-jar", "/app.jar"]
