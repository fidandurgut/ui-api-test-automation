package ui;

import core.BaseUiTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.page.LoginPage;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Tag("ui")
public class FailedLoginTest extends BaseUiTest {

    @Test
    void shouldShowErrorMessageOnFailedLogin() {
        var loginPage = new LoginPage(driver, waits)
                .open(baseUrl);

        loginPage.login(td.credentials.username, td.credentials.wrongPassword);

        assertThat(loginPage.errorMessage())
                .containsIgnoringCase("username and password do not match");

        assertThat(loginPage.isAtLogin()).isTrue();
    }
}
