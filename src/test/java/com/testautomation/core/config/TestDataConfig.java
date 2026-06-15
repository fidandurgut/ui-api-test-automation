package com.testautomation.core.config;


import com.testautomation.core.config.model.TestData;

public final class TestDataConfig {

    private static final TestData data;

    static {
        String env = ConfigLoader.resolveEnv();
        String fileName = "testdata/" + env + ".yml";
        data = ConfigLoader.loadYaml(fileName, TestData.class, "test data");
    }

    private TestDataConfig() {
    }

    public static TestData get() {
        return data;
    }
}
