import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class PatientApiIntegrationTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:4004";
    }

    @Test
    public void shouldReturnPatientsOnValidLogin() {
        String loginPayload = """
                   {
                     "email": "testuser@test.com",
                     "password": "password123"
                   }\s
                \s""";

        String token = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/api-gateway/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract()
                .jsonPath()
                .get("token");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api-gateway/patient")
                .then()
                .statusCode(200)
                .body("patients", notNullValue());
    }
}
