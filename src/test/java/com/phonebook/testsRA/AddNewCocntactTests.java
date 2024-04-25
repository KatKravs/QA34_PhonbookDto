package com.phonebook.testsRA;

import com.phonebook.dto.ContactDto;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AddNewCocntactTests extends TestBase{
    @Test
    public void AddNewContactPositiveTest(){
        ContactDto contactDto = ContactDto.builder()
                .name("Vasya")
                .lastName("Petrovich")
                .email("ssa@gm.com")
                .phone("1234567890")
                .address("Koeln")
                .description("adadasda")
                .build();

        String message = given()
                .header(AUTH, TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract().path("message");
    }

    @Test
    public void AddNewContactNegativeAnyFormatTest(){
        ContactDto contactDto = ContactDto.builder()
                .name("Vasya")
                .lastName("Petrovich")
                .email("ssa@gm.com")
                .phone("12345")
                .address("Koeln")
                .description("adadasda")
                .build();

        given()
                .header(AUTH, TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .post("contacts")
                .then()
                .assertThat().statusCode(400)
                .extract().path("message");
    }

    @Test
    public void AddContactUnathorithiredTest(){
        ContactDto contactDto = ContactDto.builder()
                .name("Vasya")
                .lastName("Petrovich")
                .email("ssa@gm.com")
                .phone("1234567890")
                .address("Koeln")
                .description("adadasda")
                .build();

        String message = given()
                .header(AUTH,"token")
                .body(contactDto)
                .contentType(ContentType.JSON)
                .post("contacts")
                .then()
                .assertThat().statusCode(401)
                .extract().path("message");
    }

    @Test
    public void DuplicateContactFieldsTest() {

        ContactDto contactDto1 = ContactDto.builder()
                .name("Ivan")
                .lastName("Ivanovich")
                .email("unique_email@gm.com")
                .phone("1234567890")
                .address("Moscow")
                .description("First contact")
                .build();


        given()
                .header(AUTH, TOKEN)
                .body(contactDto1)
                .contentType(ContentType.JSON)
                .post("contacts");


        ContactDto contactDto2 = ContactDto.builder()
                .name("Petr")
                .lastName("Petrovich")
                .email("unique_email@gm.com")
                .phone("1234567890")
                .address("Saint Petersburg")
                .description("Second contact, should cause conflict")
                .build();


        String message = given()
                .header(AUTH, TOKEN)
                .body(contactDto2)
                .contentType(ContentType.JSON)
                .post("contacts")
                .then()
                .assertThat().statusCode(409)
                .extract().path("message");
    }
}



