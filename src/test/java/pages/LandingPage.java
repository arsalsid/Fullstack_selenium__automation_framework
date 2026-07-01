package pages;

import baseTest.DriverFactory;
import baseTest.PropertyReader;
import constants.LandingPageEnum;
import io.qameta.allure.Step;
import utlis.Utilities;

import java.io.IOException;

public class LandingPage extends DriverFactory {

    @Step("Verify landing page title is: {expectedValue}")
    public void verifyPageTitle(String expectedValue) {
        Utilities.AssertTitle(expectedValue);
    }

    @Step("Search product on landing page")
    public void searchProduct() throws IOException {
        String product = PropertyReader.getInstance().readProperty("searchProduct");
        Utilities.enterText(LandingPageEnum.ENTER_SEARCH_PRODUCT.getLocator(), product);
        System.out.println("Searched Product :" +product);
        Utilities.clickOnButton(LandingPageEnum.CLICK_ON_SEARCH_BTN.getLocator());
    }
}
