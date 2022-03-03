package review.day02_Parameters_Query_Path;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SpartanJavaToJsonTestPathParameter {

    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = "http://54.236.150.168:8000";
        RestAssured.basePath = "/api";

    }

    @DisplayName("testing single spartan endpoint using {id} parameter")
    @Test
    public void testSingleSpartanEndpointWithId(){


        given().
                log().all().
                accept(ContentType.JSON).
                pathParam("id", 979).
        when().
                get("/spartans/{id}").
        then().
                statusCode(is(200)).
                contentType(ContentType.JSON);


    }


    @DisplayName("testing single spartan endpoint using id another way")
    @Test
    public void testSingleSpartanEndpointWithIdAnotherWay(){


        given().
                log().all().
                accept(ContentType.JSON).
        when().
                get("/spartans/{id}", 979).
        then().
                statusCode(equalTo(200)).
                contentType(ContentType.JSON);


    }

    @DisplayName("testing single spartan endpoint body")
    @Test
    public void testSingleSpartanEndpointBody(){


        given().
                log().all().
                accept(ContentType.JSON).
        when().
                get("/spartans/{id}", 979).
        then().
                log().all().
                statusCode(equalTo(200)).
                contentType(ContentType.JSON).
                body("id", is(979)).
                body("name", equalTo("Elsie")).
                body("gender", is(equalTo("Male"))).
                body("phone", is(1604036455));



    }





}
