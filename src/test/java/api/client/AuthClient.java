package api.client;


import api.dto.LoginRequest;
import api.dto.LoginResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class AuthClient {

    private final RequestSpecification spec;

    public AuthClient(RequestSpecification spec) {
        this.spec = spec;
    }

    public LoginResponse login(LoginRequest req) {
        return given(spec)
                .body(req)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .as(LoginResponse.class);
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

    public int loginStatus(LoginRequest req) {
        return given(spec)
                .body(req)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .statusCode();
    }
}
