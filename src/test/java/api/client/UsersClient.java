package api.client;


import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UsersClient {
    private final RequestSpecification spec;

    public UsersClient(RequestSpecification spec) {
        this.spec = spec;
    }

    public Response deleteUser(int id) {
        return given(spec)
                .when()
                .delete("/users/" + id);
    }

}
