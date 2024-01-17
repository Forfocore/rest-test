package org.example;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.pojos.FoodPojo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RestTest {

    @BeforeAll
    static void beforeAll() {
        Specifications.installSpecifications(
                Specifications.requestSpecification("http://localhost:8080/api", ContentType.JSON), Specifications.responseSpecification(200)
        );

    }

    @Test
    @DisplayName("Тестирование API при вводе типичных данных.")
    void test1() {


        FoodPojo foodPojo = new FoodPojo();
        foodPojo.setName("Мандарин");
        foodPojo.setType("FRUIT");
        foodPojo.setExotic(true);


        Response response = given()
                .body(foodPojo)
                .when()
                .post("/food");
        String cookies = response.getCookie("JSESSIONID");
        given()
                .sessionId(cookies)
                .when()
                .get("/food")
                .then().log().all()
                .assertThat()
                .body("last().name", equalTo(foodPojo.getName()))
                .body("last().type", equalTo(foodPojo.getType()))
                .body("last().exotic", equalTo(foodPojo.getExotic()));
    }

    @Test
    @DisplayName("Тестирование API при вводе нетипичных данных.")
    void test2() {


        FoodPojo foodPojo = new FoodPojo();
        foodPojo.setName("123450$&!");
        foodPojo.setType("VEGETABLE");
        foodPojo.setExotic(false);


        Response response = given()
                .body(foodPojo)
                .when()
                .post("/food");
        String cookies = response.getCookie("JSESSIONID");
        given()
                .sessionId(cookies)
                .when()
                .get("/food")
                .then().log().all()
                .assertThat()
                .body("last().name", equalTo(foodPojo.getName()))
                .body("last().type", equalTo(foodPojo.getType()))
                .body("last().exotic", equalTo(foodPojo.getExotic()));
    }
}
