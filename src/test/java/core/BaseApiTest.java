package core;


import core.config.EnvConfigLoader;
import core.config.TestDataConfig;
import core.config.model.TestData;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.time.Duration;

import static io.restassured.config.HttpClientConfig.httpClientConfig;
import static io.restassured.config.RestAssuredConfig.config;
import static io.restassured.specification.ProxySpecification.host;

public abstract class BaseApiTest {

    protected static RequestSpecification spec;
    protected final TestData td = TestDataConfig.get();

    @BeforeAll
    static void apiSetup() {
        var env = EnvConfigLoader.get();

        spec = new RequestSpecBuilder()
                .setBaseUri(env.api.baseUrl)
                .setContentType("application/json")
                .setAccept("application/json")
                // Some public APIs/WAFs return 403 for requests without a UA (common in CI).
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .log(LogDetail.BODY)
                .build();

        if (System.getenv("WEBSHARE_USER") != null) {
            RestAssured.proxy = host("p.webshare.io")
                .withPort(3128)
                .withAuth(System.getenv("WEBSHARE_USER"), System.getenv("WEBSHARE_PASS"));
        }

        RestAssured.config = config()
                .httpClient(httpClientConfig()
                        .setParam("http.connection.timeout", (int) Duration.ofSeconds(env.timeouts.apiSeconds).toMillis())
                        .setParam("http.socket.timeout", (int) Duration.ofSeconds(env.timeouts.apiSeconds).toMillis())
                );
    }
}
