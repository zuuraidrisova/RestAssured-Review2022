package review.day03_Post_Put_Patch_Delete;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TestPost {

    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = "http://54.236.150.168:8000";
        RestAssured.basePath = "/api";
    }

    @DisplayName("adding a new spartan ")
    @Test
    public void testPostRequest(){

        String body = "{\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"name\": \"Zoya\",\n" +
                "  \"phone\": 9917286281\n" +
                "}";

        System.out.println("body = " + body);

        given().
                contentType(ContentType.JSON).
                log().all().
                body(body).
        when().
                post("/spartans").
        then().
                log().all().
                contentType(ContentType.JSON).
                statusCode(201).
                body("success", is("A Spartan is Born!"));


    }

    //please create another test and make a post request, store the response object
    //save the id into int variable
    //save the name to String var
    //as hw save the spartan data field into map

    @DisplayName("Practice extracting data")
    @Test
    public void testPostRequestExtractingData(){

        String body = "{\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"name\": \"Aya\",\n" +
                "  \"phone\": 99172863231\n" +
                "}";

        Response response =

        given().
                contentType(ContentType.JSON).
                log().all().
                body(body).
        when().
                post("/spartans");


        System.out.println("id using path = " + response.path("data.id"));

        System.out.println("name using path  = " + response.path("data.name"));



        JsonPath jsonPath =  response.jsonPath();

        int id =  jsonPath.getInt("data.id");

        System.out.println("id using json path = " + id);

        String name = jsonPath.getString("data.name");

        System.out.println("name using json path  = " + name);



    }


}
