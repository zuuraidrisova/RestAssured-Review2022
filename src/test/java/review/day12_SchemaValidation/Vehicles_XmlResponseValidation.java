package review.day12_SchemaValidation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class Vehicles_XmlResponseValidation {


    @BeforeAll
    public static void setUp() {

        //we are going to send a get request to this endpoint
        //in this Rest API request, it uses query param to specify response content type

        //https://vpic.nhtsa.dot.gov/api/vehicles/GetMakeForManufacturer/988?format=xml

        RestAssured.baseURI = "https://vpic.nhtsa.dot.gov";
        RestAssured.basePath = "/api/vehicles";

    }



    @DisplayName("test /vehicles endpoint to validate xml response")
    @Test
    public void testXML(){

        given().
                log().all().
                queryParam("format","xml").
        when().
                get("/GetMakeForManufacturer/988").
        then().
                log().all().
                statusCode(is(200)).
                contentType(ContentType.XML).
                //the path must match, and value is always String in xml
                body("Response.Count", equalTo("2")).
                body("Response.Message",equalTo("Results returned successfully")).
                body("Response.SearchCriteria", equalTo("Manufacturer:988")).
                body("Response.Results.MakesForMfg[0].Mfr_Name", equalTo("AMERICAN HONDA MOTOR CO., INC.")).
                body("Response.Results.MakesForMfg[0].Make_ID",equalTo("474")).
                body("Response.Results.MakesForMfg[0].Make_Name",equalTo("HONDA")).
                body("Response.Results.MakesForMfg[1].Mfr_Name", equalTo("AMERICAN HONDA MOTOR CO., INC.")).
                body("Response.Results.MakesForMfg[1].Make_ID",equalTo("475")).
                body("Response.Results.MakesForMfg[1].Make_Name",equalTo("ACURA"));



    }


    @AfterAll
    public static void teardown(){

        RestAssured.reset();
    }



}
