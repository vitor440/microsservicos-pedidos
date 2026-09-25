package com.produtos.controller;

import com.produtos.dto.request.ProdutoRequest;
import com.produtos.dto.response.ProdutoResponse;
import com.produtos.pagecontent.ProdutoPageContent;
import com.produtos.test.ContainerConfiguration;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProdutoControllerTest extends ContainerConfiguration {

    @MockitoBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${keycloak.port}")
    private Integer keycloakPort;

    @LocalServerPort
    private Integer port;

    private static RequestSpecification specification;

    @BeforeAll
    static void setUp() {
        specification = new RequestSpecBuilder()
                .setBasePath("/produtos")
                .build();
    }

    @Test
    void create() {
        String token = getToken("admin", "admin123");
        ProdutoRequest request = new ProdutoRequest("TV Led 4K", BigDecimal.valueOf(2999), 10);

        ProdutoResponse response = RestAssured
                .given()
                .spec(specification)
                .port(port)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(201)
                .log()
                .everything()
                .extract()
                .body()
                .as(ProdutoResponse.class);
    }

    @Test
    void getById() {

        String token = getToken("admin", "admin123");

        ProdutoResponse response = RestAssured
                .given()
                .spec(specification)
                .port(port)
                .header("Authorization", token)
                .pathParam("id", 4)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .log()
                .everything()
                .extract()
                .body()
                .as(ProdutoResponse.class);


        Assertions.assertThat(response.getNome()).isEqualTo("Monitor LG 24");
        Assertions.assertThat(response.getPreco().doubleValue()).isEqualTo(899.90);
        Assertions.assertThat(response.getQuantidade()).isEqualTo(20);
    }

    @Test
    void list() {
        String token = getToken("admin", "admin123");

        ProdutoPageContent pageContent = RestAssured
                .given()
                .spec(specification)
                .port(port)
                .header("Authorization", token)
                .when()
                .get()
                .then()
                .statusCode(200)
                .log()
                .everything()
                .extract()
                .body()
                .as(ProdutoPageContent.class);

        List<ProdutoResponse> content = pageContent.getContent();

        Assertions.assertThat(content).hasSizeGreaterThanOrEqualTo(20);

    }

    public String getToken(String username, String password) {

        String tokenString = RestAssured
                .given()
                .port(keycloakPort)
                .basePath("/realms/produtos-realm/protocol/openid-connect")
                .contentType(ContentType.URLENC)
                .formParam("grant_type", "password")
                .formParam("client_id", "client123")
                .formParam("username", username)
                .formParam("password", password)
                .formParam("client_secret", "**********")
                .when()
                .post("/token")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .extract()
                .asString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return "Bearer " + jsonParser.parseMap(tokenString)
                .get("access_token")
                .toString();

    }


}