package com.testautomation.core;

import com.testautomation.core.config.EnvConfigLoader;
import com.testautomation.core.config.TestDataConfig;
import com.testautomation.core.config.model.TestData;
import com.testautomation.core.driver.DriverFactory;
import com.testautomation.core.extensions.ScreenshotOnFailureExtension;
import com.testautomation.ui.pages.InventoryPage;
import com.testautomation.ui.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;


@ExtendWith(ScreenshotOnFailureExtension.class)
public abstract class BaseUiTest {

    protected final String baseUrl = EnvConfigLoader.get().app.baseUrl;
    protected final TestData td = TestDataConfig.get();
    protected WebDriver driver;
    protected Waits waits;

    @BeforeEach
    void setUp() {
        driver = DriverFactory.create();
        waits = new Waits(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    protected InventoryPage loginAsStandardUser() {
        var loginPage = new LoginPage(driver, waits).open(baseUrl);
        loginPage.login(td.credentials.username, td.credentials.password);
        return new InventoryPage(driver, waits);
    }
}
