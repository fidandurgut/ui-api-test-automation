package api;

import api.client.CartsClient;
import api.client.ProductsClient;
import api.dto.CartRequest;
import core.BaseApiTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("api")
public class CartsApiTest extends BaseApiTest {

    @Test
    void shouldCreateCart() {
        var carts = new CartsClient(spec);
        var products = new ProductsClient(spec);

        var productResp = products.getProduct(1);
        assertThat(productResp.statusCode()).isEqualTo(200);
        int productId = productResp.jsonPath().getInt("id");

        int quantity = 2;
        var req = new CartRequest(
                1,
                "2026-01-01",
                List.of(
                        new CartRequest.CartItem(productId, quantity)
                )
        );

        var resp = carts.createCart(req);
        assertThat(resp.statusCode()).isIn(200, 201);

        int cartId = resp.jsonPath().getInt("id");
        assertThat(cartId).isGreaterThan(0);

        assertThat(resp.jsonPath().getInt("userId")).as("userId echoed back").isEqualTo(1);

        List<Map<String, Object>> items = resp.jsonPath().getList("products");
        assertThat(items).isNotEmpty();

        boolean found = false;
        for (Map<String, Object> item : items) {
            Number pid = (Number) item.get("productId");
            if (pid != null && pid.intValue() == productId) {
                Number qty = (Number) item.get("quantity");
                assertThat(qty).isNotNull();
                assertThat(qty.intValue()).isGreaterThanOrEqualTo(1);
                assertThat(qty.intValue()).isEqualTo(quantity);
                found = true;
                break;
            }
        }
        assertThat(found).as("Response should contain requested productId").isTrue();
    }
}
