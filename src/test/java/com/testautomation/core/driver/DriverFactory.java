package com.testautomation.core.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.opentest4j.TestAbortedException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver create() {
        Path chromeBinary = resolveChromeBinary()
                .orElseThrow(() -> new TestAbortedException(
                        "Chrome/Chromium binary not found. Set -Dchrome.binary=/path/to/chrome or CHROME_BINARY=/path/to/chrome."));

        configureChromeDriver();

        ChromeOptions options = new ChromeOptions();
        options.setBinary(chromeBinary.toString());

        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));
        if (headless) {
            options.addArguments("--headless=new");
        }

        options.addArguments("--window-size=1400,900");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--incognito");

        return new ChromeDriver(options);
    }

    private static void configureChromeDriver() {
        String configuredDriver = firstNonBlank(
                System.getProperty("webdriver.chrome.driver"),
                System.getenv("WEBDRIVER_CHROME_DRIVER")
        );

        if (configuredDriver != null && Files.exists(Path.of(configuredDriver))) {
            System.setProperty("webdriver.chrome.driver", configuredDriver);
            return;
        }

        WebDriverManager.chromedriver().setup();
    }

    private static Optional<Path> resolveChromeBinary() {
        String configuredBinary = firstNonBlank(
                System.getProperty("chrome.binary"),
                System.getenv("CHROME_BINARY")
        );

        if (configuredBinary != null) {
            Path path = Path.of(configuredBinary);
            if (Files.exists(path)) {
                return Optional.of(path);
            }
        }

        return Optional.ofNullable(firstExistingPath(
                "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome",
                "/Applications/Chromium.app/Contents/MacOS/Chromium",
                "/usr/bin/google-chrome",
                "/usr/bin/google-chrome-stable",
                "/usr/bin/chromium",
                "/usr/bin/chromium-browser"
        ));
    }

    private static Path firstExistingPath(String... paths) {
        for (String candidate : paths) {
            Path path = Path.of(candidate);
            if (Files.exists(path)) {
                return path;
            }
        }
        return null;
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }
}
