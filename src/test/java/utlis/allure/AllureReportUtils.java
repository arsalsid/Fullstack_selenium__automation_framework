package utlis.allure;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public final class AllureReportUtils {

    private AllureReportUtils() {
    }

    public static void attachScreenshot(WebDriver driver, String name) {
        if (driver == null) {
            return;
        }
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), ".png");
    }

    public static void attachText(String name, String content) {
        Allure.addAttachment(name, "text/plain", content);
    }

    public static void attachJson(String name, String json) {
        Allure.addAttachment(name, "application/json", json);
    }

    @Attachment(value = "{0}", type = "image/png")
    public static byte[] screenshotAsBytes(WebDriver driver) {
        if (driver == null) {
            return new byte[0];
        }
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public static void attachPageSource(WebDriver driver, String name) {
        if (driver == null) {
            return;
        }
        String pageSource = driver.getPageSource();
        Allure.addAttachment(
                name,
                "text/html",
                new ByteArrayInputStream(pageSource.getBytes(StandardCharsets.UTF_8)),
                ".html"
        );
    }
}
