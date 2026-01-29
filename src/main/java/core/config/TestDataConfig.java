package core.config;


import core.config.model.TestData;

public final class TestDataConfig {

    private static final TestData data;

    static {
        String env = ConfigLoader.resolveEnv();
        String fileName = "config/testdata-" + env + ".yml";
        data = ConfigLoader.loadYaml(fileName, TestData.class, "test data");
    }

    private TestDataConfig() {
    }

    public static TestData get() {
        return data;
    }
}
