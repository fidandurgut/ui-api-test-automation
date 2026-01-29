package core;

import core.config.EnvConfigLoader;
import core.config.TestDataConfig;
import core.config.model.TestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import ui.page.InventoryPage;
import ui.page.LoginPage;


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

    protected InventoryPage loginAsStandardUser() {
        var loginPage = new LoginPage(driver, waits).open(baseUrl);
        loginPage.login(td.credentials.username, td.credentials.password);
        return new InventoryPage(driver, waits);
    }
}
