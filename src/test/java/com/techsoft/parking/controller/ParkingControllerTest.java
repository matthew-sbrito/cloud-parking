package com.techsoft.parking.controller;

import com.techsoft.parking.dto.form.ParkingCreateDTO;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import lombok.extern.slf4j.Slf4j;

import org.hamcrest.Matchers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingControllerTest extends AbstractContainerBase {

    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest() {
        log.info("Random port: {}", randomPort);

        RestAssured.port = randomPort;
    }

    @Test
    void whenFindAllCheckResult() {
        RestAssured.given()
                .when()
                .get("/v1/parking")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", Matchers.equalTo("Not possible find parking list!"));
    }


    @Test
    void whenCreateCheckIsCreated() {
        ParkingCreateDTO createDTO = new ParkingCreateDTO();

        createDTO.setColor("AMARELO");
        createDTO.setLicense("WER-8448");
        createDTO.setModel("BRASILIA");
        createDTO.setState("BA");

        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(createDTO)
                .post("/v1/parking")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("license", Matchers.equalTo("WER-8448"))
                .body("color", Matchers.equalTo("AMARELO"))
                .body("model", Matchers.equalTo("BRASILIA"))
                .body("state", Matchers.equalTo("BA"));
    }
}