package utlis;

import baseTest.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import utlis.allure.AllureReportUtils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class Utilities extends DriverFactory {

    public static void waitForClickable(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForElementToBeClickable(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(110));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    public static void waitForVisibility(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForVisibility10(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(60)).until(ExpectedConditions.visibilityOf(element));
    }
    public static void enterText(String xpath,String input){
        try{
            WebElement element = driver.findElement(By.xpath(xpath));
            waitForVisibility(element);
            input = input.trim().toLowerCase();
            element.clear();
            element.sendKeys(input);
        }catch (NoSuchElementException e) {
            System.out.println("Unable to enter element with the given element Locator" + xpath);
        }
    }
    public static void toBeClickOnButton(String xpath){
        try {
            waitForElementToBeClickable(xpath);
            driver.findElement(By.xpath(xpath)).click();
        }catch (NoSuchElementException e){
            System.out.println("Unable to click element with the given element Locator" + xpath);
        }
    }
    public static void clickOnButton(String xpath){
        try {
            waitForClickable(driver.findElement(By.xpath(xpath)));
            driver.findElement(By.xpath(xpath)).click();
        }catch (NoSuchElementException e){
            System.out.println("Unable to click element with the given element Locator" + xpath);
        }
    }

    public static void clearSearchBar(String xpath) {
        try {
            WebElement searchBar = driver.findElement(By.xpath(xpath));
            searchBar.clear();
        }catch (Exception e) {
            System.out.println("Unable to clear search bar: " + e.getMessage());
        }
    }
    public static void navigateBackToWindow() {
        driver.navigate().back();
    }
    public static void refreshPage() {
        driver.navigate().refresh();
    }
    public static boolean AssertTitle(String expectedTitle){
        String actualResult = driver.getTitle();
        System.out.println("The title of Page is : " + actualResult);
        if(expectedTitle.equals(actualResult)) {
            return true;
        }else {
            return false;
        }
    }
    public static void validateItem(String xpath) {
        Assert.assertTrue(isElementPresent(xpath));
    }
    public static void validateText(String xpath, String expectedText) {
        WebElement element = driver.findElement(By.xpath(xpath));
        String actualText = element.getText().trim();
        expectedText = expectedText.trim();
        System.out.println("The actual text is : "  + actualText);
        Assert.assertTrue(actualText.equalsIgnoreCase(expectedText));
    }

    public static void validateProduct(String xpath, String expectedText) {
        WebElement element = driver.findElement(By.xpath(xpath));
        String actualText = element.getText();
        System.out.println("The expected text is : " + actualText);
        Assert.assertTrue(actualText.equalsIgnoreCase(expectedText));

    }

    public static boolean isElementPresent(String xpath){
        WebElement  element = driver.findElement(By.xpath(xpath));
        waitForVisibility10(element);
        if (element == null){
            return false;
        }else {
            return true;
        }
    }
    public static String takeScreenshot(WebDriver driver, String testName) throws InterruptedException {
        Thread.sleep(2000);
        TakesScreenshot scrShot = ((TakesScreenshot) driver);
        File src = scrShot.getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir") + "//Screenshots//" + testName + ".png");

        try {
            FileHandler.copy(src, dest);
            AllureReportUtils.attachScreenshot(driver, testName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return System.getProperty("user.dir") + "//Screenshots//" + testName + ".png";

    }

    public static void takeScreenShotOnTestFailure(WebDriver driver, String testName, ITestResult result) throws InterruptedException {
        System.out.println("***** Error "+result.getName()+" test has failed *****");
        String methodName=result.getName().toString().trim();
        ITestContext context = result.getTestContext();
        driver = (WebDriver)context.getAttribute("driver");
        takeScreenshot(driver,methodName);

    }
    public static void scrollDown(WebDriver driver, int pixels) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0," + pixels + ");");
    }
}
