package com.resrful.booker.bookinginfo;

import com.resrful.booker.constants.EndPoints;
import com.resrful.booker.constants.Path;
import com.resrful.booker.model.AuthPojo;
import com.resrful.booker.model.BookingPojo;
import com.resrful.booker.model.PartialUpdatePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class BookingSteps {
    @Step("creating token : {0}")
    public ValidatableResponse createTokenId(String userName, String password) {
        AuthPojo authPojo = new AuthPojo();
        authPojo.setUsername(userName);
        authPojo.setPassword(password);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")

                .body(authPojo)
                .when()
                .post(Path.AUTH)
                .then();
    }

    @Step("Creating booking with firstname:{0},lastname:{1},totalprice:{2},bookingdates:{3} and additionalneeds:{3}")

    public ValidatableResponse createBooking(String firstname, String lastname, int totalprice, boolean depositpaid, HashMap<String, Object> bookingdates, String additionalneeds) {
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setBookingdates(bookingdates);
        bookingPojo.setAdditionalneeds(additionalneeds);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .body(bookingPojo)
                .when()
                .post(EndPoints.CREATE_BOOKING)
                .then().statusCode(200);

    }

    @Step("Getting the booking information with firstname: {0}")
    public ValidatableResponse getBookingInforByID(int bookingID) {
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                /*.header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                */
                .pathParam("id", bookingID)
                .when()
                .header("Connection", "keep-alive")
                .get(EndPoints.GET_BOOKING_BY_ID)
                .then().statusCode(200);
    }

    @Step("Update the user information and verify the updated information")
    public ValidatableResponse updateBooking(int bookingID, String token, String firstname, String lastname, int totalprice, boolean depositpaid, HashMap<String, Object> bookingdates, String additionalneeds) {
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setBookingdates(bookingdates);
        bookingPojo.setAdditionalneeds(additionalneeds);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token="+ token)
                .pathParam("id", bookingID)
                .body(bookingPojo)
                .when()
                .put(EndPoints.UPDATE_BOOKING_BY_ID)
                .then().statusCode(200);

    }

    @Step("Update the user information and verify the updated information")
    public ValidatableResponse partialUpdateBooking(int bookingID, String token, String lastname) {
        PartialUpdatePojo partialUpdatePojo = new PartialUpdatePojo();
        partialUpdatePojo.setLastname(lastname);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token="+ token)
                .pathParam("id", bookingID)
                .body(partialUpdatePojo)
                .when()
                .patch(EndPoints.PARTUPDATE_BOOKING_BY_ID)
                .then().statusCode(200);

    }
    @Step("Getting the booking information with firstname: {0}")
    public ValidatableResponse getBookingInforByID(int bookingID, String token) {
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token="+ token)
                .pathParam("id", bookingID)
                .when()
                .get(EndPoints.GET_BOOKING_BY_ID)
                .then().statusCode(200);
    }

    @Step("This will delete booking by ID")
    public ValidatableResponse deleteBookingData(int bookingID, String token) {
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token="+ token)
                .pathParam("id", bookingID)
                .when()
                .delete(EndPoints.DELETE_booking_BY_ID)
                .then().statusCode(201);

    }

    @Step("Getting the booking information with firstname: {0}")
    public ValidatableResponse getBookingInforByIDAfterDeletion(int bookingID, String token) {
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .header("Cookie", "token="+ token)
                .header("Connection", "keep-alive")
                .pathParam("id", bookingID)
                .when()
                .get(EndPoints.GET_BOOKING_BY_ID)
                .then().statusCode(404);
    }


}
