package api.client;


import api.dto.LoginRequest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class AuthClient {

    private final RequestSpecification spec;

    public AuthClient(RequestSpecification spec) {
        this.spec = spec;
    }

    public Response loginResponse(LoginRequest req) {
        return given(spec)
                .body(req)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .response();
    }

}
