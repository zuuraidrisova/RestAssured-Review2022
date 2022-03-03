package review.day04_POJO_PostRequestWithPOJO_WithDifferentBodyTypes;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import review.pojo.Spartan;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PostRequestWithPOJOAsBody {


    //Serialization : pojo to json
    //De-Serialization : json to pojo

    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = "http://54.236.150.168";
        RestAssured.port = 8000;
        RestAssured.basePath = "/api";

    }

    @DisplayName("test post request with pojo")
    @Test
    public void testPostRequestWithPOJO(){

        Spartan spartan = new Spartan("Irina", "Female", 2198639233211l);

        given().
                log().all().
                contentType(ContentType.JSON).
                body(spartan).
        when().
                post("/spartans").
        then().
                log().all().
                contentType(ContentType.JSON).
                statusCode(equalTo(201))
                .body("data.name",is("Irina"));



    }

}
