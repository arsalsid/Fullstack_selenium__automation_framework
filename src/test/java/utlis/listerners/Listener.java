package utlis.listerners;

import baseTest.DriverFactory;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import utlis.allure.AllureCleanup;
import utlis.allure.AllureReportUtils;

public class Listener extends DriverFactory implements ITestListener {

    private static String getTestMethodName(ITestResult result) {
        return result.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ITestContext context) {
        AllureCleanup.cleanPreviousResults();
        Reporter.log("Cleaned previous Allure results for a fresh report.");
    }

    @Override
    public void onTestStart(ITestResult result) {
        Reporter.log(getTestMethodName(result) + " test is starting.");
        Allure.getLifecycle().updateTestCase(testCase -> testCase.setName(getTestMethodName(result)));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Reporter.log(getTestMethodName(result) + " test passed.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Reporter.log(getTestMethodName(result) + " test failed.");
        WebDriver activeDriver = resolveDriver(result);
        AllureReportUtils.attachScreenshot(activeDriver, "Failure Screenshot");
        AllureReportUtils.attachPageSource(activeDriver, "Failure Page Source");
        if (result.getThrowable() != null) {
            AllureReportUtils.attachText("Failure Reason", result.getThrowable().toString());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Reporter.log(getTestMethodName(result) + " test skipped.");
        if (result.getThrowable() != null) {
            AllureReportUtils.attachText("Skip Reason", result.getThrowable().toString());
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        Reporter.log("Test failed but within success ratio: " + getTestMethodName(result));
    }

    private WebDriver resolveDriver(ITestResult result) {
        ITestContext context = result.getTestContext();
        Object contextDriver = context.getAttribute("driver");
        if (contextDriver instanceof WebDriver webDriver) {
            return webDriver;
        }
        return driver;
    }
}
