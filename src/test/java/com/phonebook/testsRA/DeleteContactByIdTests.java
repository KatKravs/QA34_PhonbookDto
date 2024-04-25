package com.phonebook.testsRA;

import com.phonebook.dto.ContactDto;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactByIdTests extends TestBase {
    String id;

    @BeforeMethod
    public void precondition() {
        ContactDto contactDto = ContactDto.builder()
                .name("Oliver")
                .lastName("Kan")
                .email("kan@gm.com")
                .phone("1234567890")
                .address("Berlin")
                .description("goalkeeper")
                .build();


        String message = given()
                .header(AUTH, TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract().path("message");

        //System.out.println(message);
        String[] split = message.split(": ");
        id = split[1];

    }

    @Test
    public void deleteContactByIdSuccessTest() {
        given()
                .header(AUTH, TOKEN)
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                //.extract().path("message")
                .assertThat().body("message", equalTo("Contact was deleted!"));
        //System.out.println(message);

    }

    @Test
    public void deleteContactByInvalidIdTest() {
        int invalidId = 9999;
        given()
                .header(AUTH, TOKEN)
                .delete("contacts/" + invalidId)
                .then()
                .assertThat().statusCode(400)
                //.extract().path("message");
                .assertThat().body("message", equalTo("Contact with id: 9999 not found in your contacts!"));
        //System.out.println(message);
    }

    @Test
    public void deleteContactUnaftorithiredTest() {
        given()
                .header(AUTH, "token")
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("message", equalTo("JWT strings must contain exactly 2 period characters. Found: 0"));


    }
}
