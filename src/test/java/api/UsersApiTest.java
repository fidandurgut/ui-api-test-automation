package api;

import api.client.UsersClient;
import core.BaseApiTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("api")
public class UsersApiTest extends BaseApiTest {

    @Test
    void shouldDeleteUser() {
        var users = new UsersClient(spec);

        var resp = users.deleteUser(1);
        assertThat(resp.statusCode()).isIn(200, 204);

        String body = resp.asString();
        if (body != null && !body.isBlank()) {
            assertThat(resp.contentType()).contains("application/json");
            assertThat(resp.jsonPath().getInt("id")).isEqualTo(1);

            // if API returns username/email etc, validate presence (optional)
            String username = resp.jsonPath().getString("username");
            if (username != null) {
                assertThat(username).isNotBlank();
            }
        }
    }
}
