package com.phonebook.testsRA;

import com.phonebook.dto.AlContactsDto;
import com.phonebook.dto.ContactDto;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetAllContacts extends TestBase{
@Test
    public void getAllContactsSuccessTest(){
    AlContactsDto contactsDto = given()
            .header(AUTH, TOKEN)
            .when()
            .get("contacts")
            .then()
            .assertThat().statusCode(200)
            .extract().body().as(AlContactsDto.class);
    for (ContactDto contact :contactsDto.getContacts() ) {
        System.out.println(contact.getId()+"***"+contact.getName());
        System.out.println("=================");

    }
}

    @Test
    public void getAllContactsUnauthorizedTest(){
        AlContactsDto contactsDto = given()
                .header(AUTH,"eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJ")
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(401)
                .extract().body().as(AlContactsDto.class);
    }

}
