package api.client;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ProductsClient {
    private final RequestSpecification spec;

    public ProductsClient(RequestSpecification spec) {
        this.spec = spec;
    }

    public Response getProduct(int id) {
        return given(spec)
                .when()
                .get("/products/" + id);
    }
}
