package com.pedido.controller;

import com.pedido.pagecontent.PedidoPageContent;
import com.pedido.test.ContainerConfiguration;
import com.pedido.dto.request.ItemRequest;
import com.pedido.dto.response.PedidoResponse;
import com.pedido.proxy.ProdutoReservaClient;
import com.pedido.proxy.dto.ProdutoResponse;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PedidoControllerTest extends ContainerConfiguration {

    @MockitoBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @MockitoBean
    private ProdutoReservaClient client;

    static RequestSpecification specification;

    @LocalServerPort
    private int port;

    @Value("${keycloak.port}")
    private Integer keycloakPort;

    @BeforeAll
    static void setUp() {
        specification = new RequestSpecBuilder()
                .setBasePath("/pedidos")
                .build();
    }

    @Test
    void create() {
        String token = getToken("admin", "admin123");
        List<ItemRequest> requests = List.of(new ItemRequest(1L, 2), new ItemRequest(2L, 1));

        Mockito
                .when(client.getProdutos(List.of(1L, 2L)))
                .thenReturn(List.of(new ProdutoResponse(1L, "Notebook", BigDecimal.valueOf(2000), 3),
                        new ProdutoResponse(2L, "Smartphone", BigDecimal.valueOf(1700), 10)));


        PedidoResponse response = RestAssured.given()
                .spec(specification)
                .contentType(ContentType.JSON)
                .port(port)
                .header("Authorization", token)
                .body(requests)
                .when()
                .post()
                .then()
                .statusCode(200)
                .log()
                .everything()
                .extract()
                .body()
                .as(PedidoResponse.class);

        Assertions.assertThat(response.getValorTotal().doubleValue()).isEqualTo(5700);
        Assertions.assertThat(response.getStatus()).isEqualTo("PENDENTE");
        Assertions.assertThat(response.getUsuarioId()).isEqualTo("0060eaee-7afd-4cb6-b86d-034c4b37f1cb");



    }

    @Test
    void getById() {
        String token = getToken("admin", "admin123");

        PedidoResponse response = RestAssured.given()
                .spec(specification)
                .port(port)
                .header("Authorization", token)
                .pathParam("id", 1)
                .when()
                .get("/{id}")
                .then()
                .log()
                .everything()
                .statusCode(200)
                .extract()
                .as(PedidoResponse.class);

        Assertions.assertThat(response.getValorTotal().doubleValue()).isEqualTo(150.00);
        Assertions.assertThat(response.getUsuarioId()).isEqualTo("f9076fd1-511b-4b09-95a6-57e005531af3");
        Assertions.assertThat(response.getStatus()).isEqualTo("FINALIZADO");



    }

    @Test
    void listar() {


        String token = getToken("admin", "admin123");

        PedidoPageContent pageContent = RestAssured.given()
                .spec(specification)
                .port(port)
                .header("Authorization", token)
                .when()
                .param("direction", "ASC")
                .get()
                .then()
                .log()
                .everything()
                .statusCode(200)
                .extract()
                .as(PedidoPageContent.class);

        List<PedidoResponse> content = pageContent.getContent();

        Assertions.assertThat(content).hasSizeGreaterThanOrEqualTo(5);

        PedidoResponse pedido = content.get(0);

        Assertions.assertThat(pedido.getValorTotal().doubleValue()).isEqualTo(150.00);
        Assertions.assertThat(pedido.getUsuarioId()).isEqualTo("f9076fd1-511b-4b09-95a6-57e005531af3");
        Assertions.assertThat(pedido.getStatus()).isEqualTo("FINALIZADO");
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