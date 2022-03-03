package review.day04_POJO_PostRequestWithPOJO_WithDifferentBodyTypes;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import review.pojo.Spartan;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PutAndPatchRequestWithMapAndPOJOAsBody {

    @BeforeAll
    public static void setup(){

        baseURI  = "http://54.236.150.168";
        port = 8000;
        basePath = "/api";

    }

    @DisplayName("test put request with map as body")
    @Test
    public void testPutRequestWithMapAsBody(){

        //put request to update spartan with id 3567
        //name: put with map, gender :Male, phone; 7283519231
        //check status code is 204

        //how do i actually know that data is updated since put request does not have body?
        // we can make get request after put request and assert the body

        Map<String, Object> mapAsBody = new LinkedHashMap<>();

        Faker faker = new Faker();

        String randomName = faker.name().firstName();

        mapAsBody.put("name", randomName);
        mapAsBody.put("gender", "Female");
        mapAsBody.put("phone",98361923131l);

        given().
                contentType(ContentType.JSON).
                log().all().
                body(mapAsBody).
                pathParam("id", 3567).
        when().
                put("spartans/{id}").
        then().
                log().all().
                statusCode(is(204));

        //  MAKING ANOTHER GET REQUEST TO MAKE SURE IT WORKED !!!!!

        given().
                log().all().
                accept(ContentType.JSON).
                pathParam("id", 3567).
        when().
                get("/spartans/{id}").
        then().
                statusCode(is(200)).
                contentType(ContentType.JSON).
                body("name", is(randomName));



    }


    @DisplayName("test put request with pojo as body")
    @Test
    public void testPutRequestWithPOJOAsBody(){

        //POJO is plain old java object

        //put request to update spartan with id 3567
        //name: put with pojo, gender :Male, phone; 7283519231
        //check status code is 201

        //how do i actually know that data is updated since put request does not have body?
        // we can make get request after put request and assert the body

        Spartan spartan = new Spartan("POJO", "Male",7283519231l);

        given().
                contentType(ContentType.JSON).
                log().all().
                body(spartan).
                pathParam("id", 3567).
        when().
                put("/spartans/{id}").
        then().
                statusCode(is(204));


        //  MAKING ANOTHER GET REQUEST TO MAKE SURE IT WORKED !!!!!

        given().
                accept(ContentType.JSON).
                log().all().
                pathParam("id", 3567).
        when().
                get("/spartans/{id}").
        then().
                statusCode(is(200)).
                contentType(ContentType.JSON).
                body("name", is("POJO"));


    }


    @DisplayName("test patch request with map as body")
    @Test
    public void testPatchRequestWithMapAsBody(){

        Map<String, Object> mapAsBody = new LinkedHashMap<>();

        Faker faker = new Faker();

        String randomName = faker.name().firstName();

        mapAsBody.put("name", randomName);

        given().
                contentType(ContentType.JSON).
                log().all().
                pathParam("id", 3567).
                body(mapAsBody).
        when().
                patch("/spartans/{id}").
        then().
                log().all().
                statusCode(is(204));

        //add one qet request to make sure it updated just name partially

        given().
                log().all().
                pathParam("id", 3567).
        when().
                get("/spartans/{id}").
        then().
                statusCode(is(200)).
                contentType(ContentType.JSON).
                body("name", is(randomName));

    }



}
