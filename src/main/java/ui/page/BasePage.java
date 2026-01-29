package ui.page;

import core.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final Waits waits;

    protected BasePage(WebDriver driver, Waits waits) {
        this.driver = driver;
        this.waits = waits;
    }

    protected WebElement find(By locator) {
        return waits.visible(locator);
    }

    protected void click(By locator) {
        waits.clickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return find(locator).getText().trim();
    }
}

