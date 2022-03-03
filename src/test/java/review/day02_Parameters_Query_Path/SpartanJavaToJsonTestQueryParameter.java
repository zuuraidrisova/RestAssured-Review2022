package review.day02_Parameters_Query_Path;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SpartanJavaToJsonTestQueryParameter {


    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = "http://54.236.150.168:8000";
        RestAssured.basePath = "/api";


    }

    //making query param more obvious
    @DisplayName("test Search spartan endpoint to practice query parameter")
    @Test
    public void testSearchSpartanEndpoint(){


        given().
                log().all().
                accept(ContentType.JSON).
                queryParam("gender","Female").
                queryParam("nameContains", "ea").
        when().
                get("/spartans/search").
        then().
                log().all().
                statusCode(equalTo(200)).
                contentType(ContentType.JSON).
                body("totalElement", equalTo(3));


    }


    @DisplayName("test Search endpoint to practice query parameter another way")
    @Test
    public void testSearchSpartanEndpointAnotherWay(){


        given().
                log().all().
                accept(ContentType.JSON).
        when().
                get("/spartans/search?gender=Female&nameContains=ea").
        then().
                log().all().
                statusCode(equalTo(200)).
                contentType(ContentType.JSON).
                body("totalElement", equalTo(3));


    }


}
