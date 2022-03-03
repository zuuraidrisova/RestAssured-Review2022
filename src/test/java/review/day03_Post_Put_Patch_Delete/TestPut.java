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


public class TestPut {

    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = "http://54.236.150.168:8000";
        RestAssured.basePath = "/api";
    }

    @DisplayName("testing put request for updating data")
    @Test
    public void testPutRequest(){

        String updatedBody = "{\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"name\": \"Akmat\",\n" +
                "  \"phone\": 99111863231\n" +
                "}";

        //3661

        given().
                contentType(ContentType.JSON).
                log().all().
                pathParam("id", 3661).
                body(updatedBody).
         when().
                put("/spartans/{id}").
         then().
                statusCode(204);

    }
}
