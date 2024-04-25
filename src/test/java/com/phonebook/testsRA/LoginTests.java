package com.phonebook.testsRA;

import com.phonebook.dto.AuthRequestDto;
import com.phonebook.dto.AuthResponseDto;
import com.phonebook.dto.ErrorDto;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class LoginTests extends TestBase {
    AuthRequestDto auth = AuthRequestDto.builder()
            .username("kat@gmail.com")
            .password("Xbrbnjxrf90!")
            .build();

    @Test
    public void loginSuccessTest() {
        AuthResponseDto dto = given()
                .body(auth)
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(AuthResponseDto.class);

        System.out.println(dto.getToken());
    }
    @Test
    public void loginSuccessTest2(){
        String responseToken=given()
                .body(auth)
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(200)
                .body(containsString("token"))
                .extract().path("token");
        System.out.println(responseToken);
    }

    @Test
    public void loginWithWrongEmail(){
         given().body(AuthRequestDto.builder()
                .username("katgmail.com")
                .password("Xbrbnjxrf90!")
                .build())
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(401)
                 .assertThat().body("message",equalTo("Login or Password incorrect"));
               // .extract().response().as(ErrorDto.class);

    }
}
