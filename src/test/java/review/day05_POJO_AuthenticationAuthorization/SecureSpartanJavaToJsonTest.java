package review.day05_POJO_AuthenticationAuthorization;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import review.day04_POJO_PostRequestWithPOJO_WithDifferentBodyTypes.GetOneSpartanJavaToJsonMethod;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SecureSpartanJavaToJsonTest {


    //authentication gives u identity : tells  who you are
    //authorization tells you what u can do
    //Authentication it is about who you are, and authorization is what you can
    // do according to who you are


    //we dont use our own ip in this one because our ip is with no auth
    //we use one of followings because they are with basic auth :


    //add BeforeAll and use spartanApp with basic auth
    @BeforeAll
    public static void setUp(){

        baseURI = "http://54.236.150.168";
        port = 8000;
        basePath = "/api";


    }

    @DisplayName("testing get spartans/{id} endpoint with no credentials")
    @Test
    public void testGetSingleSpartanEndpoint() {

        int id = GetOneSpartanJavaToJsonMethod.createOneSpartan();

        given().
                accept(ContentType.JSON).
                log().all().
                pathParam("id", id).
        when().
                get("/spartans/{id}").
        then().
                statusCode(is(200));


    }

    @DisplayName("testing get spartans/{id} endpoint with credentials")
    @Test
    public void testGetSingleSpartanEndpointSecured() {

        int id = GetOneSpartanJavaToJsonMethod.createOneSpartan();

        given().
                accept(ContentType.JSON).
                log().all().
                auth().basic("admin", "admin").
                pathParam("id", id).
        when().
                get("/spartans/{id}").
        then().
                statusCode(is(200));



    }





}
