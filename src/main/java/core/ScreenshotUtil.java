package core;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ScreenshotUtil {

    public static void capture(WebDriver driver, String testName) {
        if (!(driver instanceof TakesScreenshot ts)) return;

        File src = ts.getScreenshotAs(OutputType.FILE);

        try {
            Path destDir = Paths.get("target", "screenshots");
            Files.createDirectories(destDir);

            Path dest = destDir.resolve(testName + ".png");
            Files.copy(src.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
