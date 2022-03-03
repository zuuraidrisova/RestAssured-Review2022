package review.day04_POJO_PostRequestWithPOJO_WithDifferentBodyTypes;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PostRequestTestWithStringMapObjectExternalFileAsBody {


    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = "http://54.236.150.168";
        RestAssured.port = 8000;
        RestAssured.basePath = "/api";

    }

    @DisplayName("test post request with string as body")
    @Test
    public void testPostRequestWithStringAsBody(){

        Faker faker = new Faker();

        String randomName = faker.name().firstName();

        String body =  "{\n" +
                "\"name\"  : \"" + randomName+ "\",\n" +

                "\"gender\": \"Female\",\n" +

                "\"phone\": 6234567890\n" +

                "}";


        given().
                contentType(ContentType.JSON).
                log().all().
                body(body).
        when().
                post("/spartans").
        then().
                statusCode(equalTo(201)).
                contentType(ContentType.JSON).
                body("data.name", equalTo(randomName) );


    }

    @DisplayName("test post request with external file as body")
    @Test
    public void testPostRequestWithExternalFileAsBody(){

        //create a new file from root folder  spartan.json
        //create a file object that point to spartan.json u just added
        //so we can use this as body in post request


        File file = new File("spartan.json");

        given().
                contentType(ContentType.JSON).
                log().all().
                body(file).
        when().
                post("/spartans").
        then().
                log().all().
                statusCode(equalTo(201)).
                contentType(ContentType.JSON).
                body("success", is("A Spartan is Born!")).
                body("data.name", is("From File"));

    }


    @DisplayName("test post request with java map object as body")
    @Test
    public void testPostRequestWithMapObjectAsBody(){

        //add dependency jackson-databind

        // create a Map<String, Object> as hashMap
        //add name, gender, phone

        Faker faker = new Faker();

        Map<String, Object> mapAsBody = new LinkedHashMap<>();

        mapAsBody.put("name", faker.name().firstName());
        mapAsBody.put("gender", faker.demographic().sex());
        mapAsBody.put("phone", 3912731123L);


        given().
                contentType(ContentType.JSON).
                log().all().
                body(mapAsBody).//jackson-data-bind turn ur java map object to json here
        when().
                post("/spartans").
        then().
                log().all().
                statusCode(equalTo(201)).
                contentType(ContentType.JSON).
                body("success", is("A Spartan is Born!"));



    }



}
