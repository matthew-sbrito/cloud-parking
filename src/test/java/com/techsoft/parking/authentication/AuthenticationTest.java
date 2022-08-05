package com.techsoft.parking.authentication;

import com.techsoft.parking.authentication.dto.form.AuthenticateLoginDTO;
import com.techsoft.parking.controller.AbstractContainerBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@Slf4j
@SpringBootTest
class AuthenticationTest extends AbstractContainerBase {

    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest() {
        log.info("Random port: {}", randomPort);

        RestAssured.port = randomPort;
    }
    @Test
    void toBeReturnSuccessWithValidCredentials() {

        AuthenticateLoginDTO loginBody = new AuthenticateLoginDTO();

        loginBody.setUsername("matheus");
        loginBody.setPassword("123456");

        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(loginBody)
                .post("/login")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void toBeReturnErrorWithInvalidCredentials() {
        AuthenticateLoginDTO loginBody = new AuthenticateLoginDTO();

        loginBody.setUsername("mathes");
        loginBody.setPassword("123456");

        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(loginBody)
                .post("/login")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
