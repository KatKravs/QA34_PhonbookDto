package com.phonebook.testsRA;

import com.phonebook.dto.ContactDto;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UpdateContactTests extends TestBase{

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
    public void updateContactByIdPositiveTest(){
        ContactDto updatedContactDto = ContactDto.builder()
                .id(id)
                .name("Oliver")
                .lastName("Kan")
                .email("ail@gm.com")
                .phone("9876543210")
                .address("kan77@gmail.com")
                .description("NewDescription")
                .build();

        given()
                .header(AUTH, TOKEN)
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .put("contacts/")
                .then()
                .assertThat().statusCode(200)
                //.extract().path("message");
        .assertThat().body("message", equalTo("Contact was updated"));


    }
}
