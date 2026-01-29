package core.config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

final class ConfigLoader {

    private static final String DEFAULT_ENV = "dev";

    private ConfigLoader() {
    }

    static String resolveEnv() {
        String env = System.getProperty("env");
        if (env == null || env.isBlank()) {
            return DEFAULT_ENV;
        }
        return env;
    }

    static <T> T loadYaml(String fileName, Class<T> type, String label) {
        Yaml yaml = new Yaml();
        try (InputStream in = ConfigLoader.class
                .getClassLoader()
                .getResourceAsStream(fileName)) {

            if (in == null) {
                throw new IllegalStateException(label + " not found: " + fileName);
            }
            return yaml.loadAs(in, type);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load " + label + ": " + fileName, e);
        }
    }
}
