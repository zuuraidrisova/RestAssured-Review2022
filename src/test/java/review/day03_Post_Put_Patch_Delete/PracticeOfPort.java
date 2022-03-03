package review.day03_Post_Put_Patch_Delete;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PracticeOfPort {

    //http://54.236.150.168

    @BeforeAll
    public static void setup(){

        baseURI = "http://54.236.150.168";
        port = 8000;
        basePath = "/api";

    }

    @DisplayName("testing spartans search endpoint with port example")
    @Test
    public void testSpartanSearchEndpoint9() {

        given().
                log().all().
                accept(ContentType.JSON).
                queryParam("gender", "Female").
        when().
                get("/spartans/search").
        then().
                log().all().
                contentType(ContentType.JSON).
                statusCode(equalTo(200));

    }


}
