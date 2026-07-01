package api.get;

import api.base.BaseAPI;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import utlis.JSONUtils;
import utlis.allure.AllureReportUtils;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetBooking {

    @Step("GET /booking/{bookingId}")
    public void getBookingById(String bookingId) throws IOException {
        Response response = BaseAPI.setupRequest()
                .get("/booking/" + bookingId)
                .then()
                .spec(BaseAPI.successJsonSpec())
                .log().all()
                .body("firstname", notNullValue())
                .body("firstname", equalTo(BaseAPI.readExpectation("createBooking.firstname")))
                .body("lastname", equalTo(BaseAPI.readExpectation("createBooking.lastname")))
                .extract()
                .response();

        JSONUtils.saveResponseToFile(response, "Booking_" + bookingId + "_details.json");
        AllureReportUtils.attachJson("Get Booking Response", response.asPrettyString());
    }

    @Step("GET invalid booking /booking/{invalidId}")
    public void getInvalidBooking(String invalidId) throws IOException {
        BaseAPI.setupRequest()
                .get("/booking/" + invalidId)
                .then()
                .spec(BaseAPI.notFoundSpec())
                .log().all();
    }
}
