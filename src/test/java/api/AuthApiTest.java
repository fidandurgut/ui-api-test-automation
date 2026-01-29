package api;

import api.client.AuthClient;
import api.dto.LoginRequest;
import core.BaseApiTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("api")
public class AuthApiTest extends BaseApiTest {

    @Test
    void shouldLoginAndReturnToken() {
        var auth = new AuthClient(spec);

        var resp = auth.loginResponse(new LoginRequest(td.apiCredentials.username, td.apiCredentials.password));

        //bug: should return 200
        assertThat(resp.statusCode()).isEqualTo(201);
        assertThat(resp.contentType()).contains("application/json");

        String token = resp.jsonPath().getString("token");
        assertThat(token)
                .as("Token should be returned on successful login")
                .isNotBlank();
    }

    @Test
    void negative_shouldRejectInvalidLogin() {
        var auth = new AuthClient(spec);

        var resp = auth.loginResponse(new LoginRequest("invalid_user", "invalid_pass"));
        assertThat(resp.statusCode())
                .as("Invalid credentials should not succeed")
                .isIn(400, 401);
    }
}
