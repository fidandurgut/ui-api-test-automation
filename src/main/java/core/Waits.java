package core;

import core.config.EnvConfigLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class Waits {
    private final WebDriverWait wait;

    public Waits(WebDriver driver) {
        int timeout = EnvConfigLoader.get().timeouts.explicitWaitSeconds;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    public WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean urlContains(String fragment) {
        return wait.until(d -> d.getCurrentUrl().contains(fragment));
    }
}
