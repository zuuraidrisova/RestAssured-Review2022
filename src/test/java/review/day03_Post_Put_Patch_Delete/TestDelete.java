package review.day03_Post_Put_Patch_Delete;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestDelete {

    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = "http://54.236.150.168:8000";
        RestAssured.basePath = "/api";
    }

    @DisplayName("testing delete request")
    @Test
    public void testDeleteRequest(){

        given().
                log().all().
                pathParam("id", 3658).
        when().
                delete("/spartans/{id}").
        then().
                statusCode(204);
    }


}
