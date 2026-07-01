package api.post;

import api.base.BaseAPI;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import utlis.JSONUtils;
import utlis.allure.AllureReportUtils;

import java.io.IOException;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateBooking {

    @Step("Create new booking with valid JSON payload")
    public String createNewBooking() throws IOException {
        Response response = BaseAPI.setupRequest()
                .body(JSONUtils.readRequestFileFromClasspath(BaseAPI.REQUEST_PATH_CREATE_BOOKING))
                .when()
                .post("/booking")
                .then()
                .spec(BaseAPI.successJsonSpec())
                .log().all()
                .body(matchesJsonSchemaInClasspath(BaseAPI.SCHEMA_CREATE_BOOKING_RESPONSE))
                .body("bookingid", notNullValue())
                .body("booking.firstname", equalTo(BaseAPI.readExpectation("createBooking.firstname")))
                .body("booking.lastname", equalTo(BaseAPI.readExpectation("createBooking.lastname")))
                .extract()
                .response();

        String bookingId = response.path("bookingid").toString();
        AllureReportUtils.attachJson("Create Booking Response", response.asPrettyString());
        JSONUtils.saveResponseToFile(response, "Booking_" + bookingId + ".json");

        return bookingId;
    }

    @Step("Create booking with invalid JSON payload")
    public void createInvalidBooking() throws IOException {
        Response response = BaseAPI.setupRequest()
                .body(JSONUtils.readRequestFileFromClasspath(BaseAPI.REQUEST_PATH_CREATE_BOOKING_NEGATIVE))
                .when()
                .post("/booking")
                .then()
                .spec(BaseAPI.negativeSpec())
                .log().all()
                .extract()
                .response();

        JSONUtils.saveResponseToFile(response, "Negative_Booking_Response.json");
        AllureReportUtils.attachJson("Negative Booking Response", response.asPrettyString());
    }
}
