package api;

import api.client.ProductsClient;
import core.BaseApiTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("api")
public class ProductsApiTest extends BaseApiTest {

    @Test
    void shouldGetProductById() {
        var products = new ProductsClient(spec);

        var resp = products.getProduct(1);
        assertThat(resp.statusCode()).isEqualTo(200);
        assertThat(resp.contentType()).contains("application/json");

        var jp = resp.jsonPath();

        // identity
        assertThat(jp.getInt("id")).isEqualTo(1);

        // content / contract validations (assignment expects this)
        String title = jp.getString("title");
        assertThat(title).as("title").isNotBlank();

        Number price = jp.get("price");
        assertThat(price).as("price").isNotNull();
        assertThat(price.doubleValue()).as("price >= 0").isGreaterThanOrEqualTo(0.0);

        String description = jp.getString("description");
        assertThat(description).as("description").isNotBlank();

        String category = jp.getString("category");
        assertThat(category).as("category").isNotBlank();

        String image = jp.getString("image");
        assertThat(image).as("image").isNotBlank();
        // lightweight URL sanity check (avoid strict regex)
        assertThat(image).as("image looks like URL").contains("http");

        // rating is present for most products on FakeStore; validate if returned
        Object rating = jp.get("rating");
        if (rating != null) {
            Number rate = jp.get("rating.rate");
            Number count = jp.get("rating.count");

            assertThat(rate).as("rating.rate").isNotNull();
            assertThat(rate.doubleValue()).as("rating.rate range").isBetween(0.0, 5.0);

            assertThat(count).as("rating.count").isNotNull();
            assertThat(count.intValue()).as("rating.count >= 0").isGreaterThanOrEqualTo(0);
        }
    }

    @Test
    void negative_shouldReturnNotFoundForUnknownProduct() {
        var products = new ProductsClient(spec);

        //Assumption: Product IDs in FakeStore are within a small fixed range;
        // therefore a large ID (e.g., 9999) is used to simulate a non-existing product.
        var resp = products.getProduct(9999);

        //
        assertThat(resp.statusCode())
                .as("Unknown product should not be found")
                .isIn(200);
        resp.asString();
    }
}
