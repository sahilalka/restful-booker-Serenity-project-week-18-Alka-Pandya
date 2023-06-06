package com.resrful.booker.bookinginfo;

import com.resrful.booker.constants.EndPoints;
import com.resrful.booker.model.AuthPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class AuthSteps {
    @Step("Creating token with userName : {0} and passWord : {1}")
    public ValidatableResponse createToken(String username, String password){
        AuthPojo authPojo = AuthPojo.getAuthPojo(username, password);
        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .when()
                .body(authPojo)
                .post(EndPoints.GET_AUTH_TOKEN)
                .then();
    }

}
