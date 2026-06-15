package com.testautomation.core.config;

import com.testautomation.core.config.model.EnvConfig;

public final class EnvConfigLoader {

    private static final EnvConfig config;

    static {
        String env = ConfigLoader.resolveEnv();
        String fileName = "environments/" + env + ".yml";
        config = ConfigLoader.loadYaml(fileName, EnvConfig.class, "environment config");
    }

    private EnvConfigLoader() {
    }

    public static EnvConfig get() {
        return config;
    }
}
