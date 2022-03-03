package review.day12_SchemaValidation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import review.utility.ConfigurationReader;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class SpartanSchemaValidation {

    /*
JsonSchema Validation Warm up :
Add the AllSpartansSchema.json and SearchSchema.json under resources folder

Create a class called SchemaValidationTest
Add 2 tests:
GET /spartans
GET /spartans/search
Validate the response against the schema in classpath

 */

    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = ConfigurationReader.getProperty("spartan1.base_url");
        RestAssured.basePath = "/api";

    }

    @DisplayName("testing /spartans endpoint json schema validation")
    @Test
    public void testSpartansEndpointJsonSchemaValidation() {

        given().
                log().all().
                accept(ContentType.JSON).
        when().
                get("/spartans").
        then().
                body(matchesJsonSchemaInClasspath("allSpartansSchema.json"));

    }


    @DisplayName("testing spartans/search endpoint json schema validation")
    @Test
    public void testSearchEndpointJsonSchemaValidation() {

        given().
                log().all().
                accept(ContentType.JSON).
                queryParam("gender","Male").
        when().
                get("/spartans/search").
        then().
                body(matchesJsonSchemaInClasspath("spartanSearchSchema.json"));

    }


        /*
    what if my schema file is not under resources folder ?
     then matchesJsonSchemaInClasspath  method will not work because
     it only look for schema under resources folder.
        We have to use matchesJsonSchema method and provide full path
        if file is not under resource folder
     */
        @DisplayName("test Spartans Schema From Different Location")
        @Test
        public void testSpartansSchemaFromDifferentLocation(){

            //allSpartans2Schema.json

            //create a File object that point to the schema
            //use matchesJsonSchema() method that accepts a file and do ur validation

            File file = new File("allSpartans2Schema.json");

            given().
                    log().all().
                    accept(ContentType.JSON).
            when().
                    get("/spartans").
            then().
                    body(matchesJsonSchema(file));
        }



    @AfterAll
    public static void teardown(){

        RestAssured.reset();

    }



}
