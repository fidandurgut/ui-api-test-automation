package core.config.model;

public class TestData {

    public Credentials credentials;
    public ApiCredentials apiCredentials;
    public Customer customer;

    public static class Credentials {
        public String username;
        public String password;
        public String wrongPassword;
    }

    public static class Customer {
        public String firstName;
        public String lastName;
        public String postalCode;
    }

    public static class ApiCredentials {
        public String username;
        public String password;
    }
}

