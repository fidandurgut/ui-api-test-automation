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

public abstract class BaseApiTest {

    protected static RequestSpecification spec;
    protected final TestData td = TestDataConfig.get();

    @BeforeAll
    static void apiSetup() {
        var env = EnvConfigLoader.get();

        spec = new RequestSpecBuilder()
                .setBaseUri(env.api.baseUrl)
                .setContentType("application/json")
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .log(LogDetail.BODY)
                .build();

        RestAssured.config = config()
                .httpClient(httpClientConfig()
                        .setParam("http.connection.timeout", (int) Duration.ofSeconds(env.timeouts.apiSeconds).toMillis())
                        .setParam("http.socket.timeout", (int) Duration.ofSeconds(env.timeouts.apiSeconds).toMillis())
                );
    }
}
