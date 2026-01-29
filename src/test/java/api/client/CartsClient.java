package api.client;

import api.dto.CartRequest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class CartsClient {
    private final RequestSpecification spec;

    public CartsClient(RequestSpecification spec) {
        this.spec = spec;
    }

    public Response createCart(CartRequest req) {
        return given(spec)
                .body(req)
                .when()
                .post("/carts");
    }
}


