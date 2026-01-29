package core;


import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;

public class ScreenshotOnFailureExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {
        var testInstance = context.getRequiredTestInstance();

        try {
            if (!(testInstance instanceof BaseUiTest baseTest)) {
                return;
            }

            WebDriver driver = baseTest.driver;

            boolean failed = context.getExecutionException().isPresent();
            if (failed && driver != null) {
                ScreenshotUtil.capture(driver, context.getDisplayName().replace("()", ""));
            }
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}
