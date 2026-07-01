package api.base;

import baseTest.PropertyReader;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.io.IOException;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class BaseAPI {

    public static final String REQUEST_PATH_CREATE_BOOKING = "request/CreateBookingRequest.json";
    public static final String REQUEST_PATH_CREATE_BOOKING_NEGATIVE = "request/CreateBookingNegativeRequest.json";
    public static final String SCHEMA_CREATE_BOOKING_RESPONSE = "schemas/create-booking-response-schema.json";

    private BaseAPI() {
    }

    public static RequestSpecification setupRequest() throws IOException {
        return RestAssured.given()
                .filter(new AllureRestAssured())
                .baseUri(PropertyReader.getInstance().readProperty("baseURI"))
                .contentType(ContentType.JSON);
    }

    public static ResponseSpecification successJsonSpec() throws IOException {
        return successJsonSpec(readExpectedStatusCode("createBooking.statusCode", 200));
    }

    public static ResponseSpecification successJsonSpec(int expectedStatusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(expectedStatusCode)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification notFoundSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }

    public static ResponseSpecification negativeSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(not(equalTo(200)))
                .build();
    }

    public static ValidatableResponse validateSuccessJson(Response response) throws IOException {
        return response.then()
                .log().all()
                .spec(successJsonSpec());
    }

    public static int readExpectedStatusCode(String expectationKey, int defaultStatusCode) throws IOException {
        String value = PropertyReader.getInstance().readExpectation(expectationKey);
        return Integer.parseInt(value);
    }

    public static String readExpectation(String key) throws IOException {
        return PropertyReader.getInstance().readExpectation(key);
    }
}
