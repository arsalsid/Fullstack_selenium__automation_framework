package tests;

import api.base.BaseAPI;
import api.get.GetBooking;
import api.post.CreateBooking;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import utlis.allure.AllureReportUtils;

import java.io.IOException;

@Epic("API Automation")
@Feature("Restful Booker - Booking API")
public class E2EAPIRunner {

    @Test(description = "Create a new valid Data and GET ID of Details", priority = 1)
    @Story("Positive booking flow")
    // @Severity(io.qameta.allure.SeverityLevel.CRITICAL)
    @Description("Creates a booking via POST /booking and validates the created record via GET /booking/{id}")
    public void createValidDataAndGetIdWithDetails() throws IOException {
        String bookingId = createBooking();
        getBookingDetails(bookingId);
        AllureReportUtils.attachText("Created Booking ID", bookingId);
    }

    @Test(description = "Create Invalid Data and GET ID of Details", priority = 2)
    @Story("Negative booking flow")
    // @Severity(io.qameta.allure.SeverityLevel.NORMAL)
    @Description("Validates invalid POST payload handling and 404 response for a non-existent booking ID")
    public void createInvalidDataAndGetIdWithDetails() throws IOException {
        createInvalidBooking();
        String invalidBookingId = BaseAPI.readExpectation("invalidBooking.id");
        getInvalidBookingDetails(invalidBookingId);
    }

    @Step("Create booking via POST /booking")
    private String createBooking() throws IOException {
        return new CreateBooking().createNewBooking();
    }

    @Step("Get booking details for ID: {bookingId}")
    private void getBookingDetails(String bookingId) throws IOException {
        new GetBooking().getBookingById(bookingId);
    }

    @Step("Submit invalid booking payload via POST /booking")
    private void createInvalidBooking() throws IOException {
        new CreateBooking().createInvalidBooking();
    }

    @Step("Get invalid booking details for ID: {invalidBookingId}")
    private void getInvalidBookingDetails(String invalidBookingId) throws IOException {
        new GetBooking().getInvalidBooking(invalidBookingId);
    }
}
