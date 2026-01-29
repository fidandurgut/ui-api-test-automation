package core.config.model;

public class EnvConfig {
    public App app;
    public Api api;

    public Timeouts timeouts;

    public static class App {
        public String baseUrl;
    }

    public static class Api {
        public String baseUrl;
    }

    public static class Timeouts {
        public int explicitWaitSeconds;
        public int apiSeconds;

    }
}
