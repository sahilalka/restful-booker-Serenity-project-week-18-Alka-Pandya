package com.resrful.booker.bookinginfo;

import com.resrful.booker.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

@RunWith(SerenityRunner.class)
public class BookingCRUDTest extends TestBase {
    static String username = "admin";
    static String password = "password123";
    static String firstname = "Raja";
    static String lastname = "Ram";
    static int totalprice = 288;
    static boolean depositpaid = true;

    static String additionalneeds = "Breakfast";
    static int bookingID;
    static String token;


    @Steps
    BookingSteps bookingSteps;
    @Title("create token")
    @Test
    public void test001() {
        ValidatableResponse response = bookingSteps.createTokenId(username, password);
        response.log().all().statusCode(200);
        token = response.extract().path("token");
        System.out.println("this is for viewing : " + token);
    }

    @Title("This will create a booking")
    @Test
    public void test002() {

        HashMap<String, Object> bookingDatesMap = new HashMap<>();
        bookingDatesMap.put("checkin", "2023-01-01");
        bookingDatesMap.put("checkout", "2023-02-01");

        ValidatableResponse response = bookingSteps.createBooking(firstname, lastname, totalprice, depositpaid, bookingDatesMap, additionalneeds);
        response.log().all().statusCode(200);
        bookingID = response.extract().path("bookingid");
        System.out.println(bookingID);


    }

    @Title("Verify if the user was added to the application")

    @Test
    public void test003() {
        ValidatableResponse response = bookingSteps.getBookingInforByID(bookingID);
        response.log().all().statusCode(200);

    }

    @Title("This will update a booking")
    @Test
    public void test004() {
        firstname = firstname + "_updated";

        HashMap<String, Object> bookingDatesMap = new HashMap<>();
        bookingDatesMap.put("checkin", "2023-01-01");
        bookingDatesMap.put("checkout", "2024-01-01");

        ValidatableResponse response = bookingSteps.updateBooking(bookingID, token, firstname, lastname, totalprice, depositpaid, bookingDatesMap, additionalneeds);
        response.log().all().statusCode(200);

    }

    @Title("This will update a partial booking ")
    @Test
    public void test005() {
        lastname = lastname + "_updated";

        ValidatableResponse response = bookingSteps.partialUpdateBooking(bookingID, token, lastname);
        response.log().all().statusCode(200);

    }

    @Title("Verify if the booking  was updated partially")

    @Test
    public void test006() {
        bookingSteps.getBookingInforByID(bookingID, token).statusCode(200);
    }

    @Title("This will delete booking and verify booking is deleted")

    @Test
    public void test007() {
        bookingSteps.deleteBookingData(bookingID,token).statusCode(201);
        bookingSteps.getBookingInforByIDAfterDeletion(bookingID,token).statusCode(404);

    }




}
