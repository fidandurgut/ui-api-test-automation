package ui.page;


import core.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {


    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By loginBtn = By.id("login-button");
    private final By error = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver, Waits waits) {
        super(driver, waits);
    }


    public LoginPage open(String baseUrl) {
        driver.get(baseUrl);
        waits.visible(username);
        return this;
    }

    public void login(String user, String pass) {
        type(username, user);
        type(password, pass);
        click(loginBtn);
    }

    public String errorMessage() {
        return getText(error);
    }

    public boolean isAtLogin() {
        return driver.getCurrentUrl().contains("saucedemo.com");
    }
}
