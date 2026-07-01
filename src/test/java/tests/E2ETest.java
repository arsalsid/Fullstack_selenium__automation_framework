package tests;

import baseTest.DriverFactory;
import baseTest.PropertyReader;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import pages.LandingPage;
import utlis.Utilities;

import java.io.IOException;

@Epic("UI Automation")
@Feature("Amazon Landing Page")
public class E2ETest extends DriverFactory {

    private final LandingPage landingPage = new LandingPage();

    @Test(description = "Verify Title of landingPage", priority = 1)
    @Story("Landing page title validation")
    // @Severity(io.qameta.allure.SeverityLevel.CRITICAL)
    @Description("Validates that the Amazon landing page title matches the expected value from expectations.properties")
    public void verifyTitleOfLandingPage() throws IOException, InterruptedException {
        String expectedTitle = PropertyReader.getInstance().readExpectation("landingPageTitle");
        landingPage.verifyPageTitle(expectedTitle);
        Utilities.takeScreenshot(driver, "LandingPage");
    }

    @Test(description = "User Enter the product item in a search bar", priority = 2)
    @Story("Product search")
    // @Severity(io.qameta.allure.SeverityLevel.NORMAL)
    @Description("Enters a product keyword in the Amazon search bar using test data from config.properties")
    public void enterSearchItemOnLandingPage() throws Exception {
        landingPage.searchProduct();
        Utilities.takeScreenshot(driver, "LandingPage_Search");
    }
}
