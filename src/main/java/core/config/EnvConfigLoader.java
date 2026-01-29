package core.config;

import core.config.model.EnvConfig;

public final class EnvConfigLoader {

    private static final EnvConfig config;

    static {
        String env = ConfigLoader.resolveEnv();
        String fileName = "config/env-" + env + ".yml";
        config = ConfigLoader.loadYaml(fileName, EnvConfig.class, "environment config");
    }

    private EnvConfigLoader() {
    }

    public static EnvConfig get() {
        return config;
    }
}
