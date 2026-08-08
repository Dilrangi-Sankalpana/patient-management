import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthIntegrationTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:4004";
    }

    @Test
    public void shouldReturnStatusCode200WithValidToken() {
        String loginPayload = """
                   {
                     "email": "testuser@test.com",
                     "password": "password123"
                   }\s
                \s""";

        Response response = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/api-gateway/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract()
                .response();
    }

    @Test
    public void shouldReturnStatusCode401OnInvalidLogin() {
        String loginPayload = """
                   {
                     "email": "invaliduser@test.com",
                     "password": "wrong password"
                   }\s
                \s""";

        given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/api-gateway/auth/login")
                .then()
                .statusCode(401);
    }
}
