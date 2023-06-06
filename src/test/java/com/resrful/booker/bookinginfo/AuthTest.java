package com.resrful.booker.bookinginfo;

import com.resrful.booker.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class AuthTest extends TestBase {
    static String username = "admin";
    static String password = "password123";

    @Steps
    AuthSteps authSteps;

    @Title("This will create Auth token")
    @Test
    public void test001() {
        ValidatableResponse response = authSteps.createToken(username, password);
        response.log().all().statusCode(200);


    }
}