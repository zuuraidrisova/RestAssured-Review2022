package review.day13_Review;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ProvidingCookieInRequest {

          /*
Provide cookie  with this request : https://postman-echo.com/cookies
 Try to add 2 cookies in the requests by following the doc.
     */

   @BeforeAll
    public static void setup(){

       RestAssured.baseURI = "https://postman-echo.com";

   }

   @DisplayName("testing cookies")
   @Test
    public void testCookies(){

       given().
               log().all().
               contentType(ContentType.JSON).
               cookie("myFirstCookie", "hello").
               cookie("mySecondCookie", "Done").
       when().
               get("/cookies").
       then().
               log().all().
               statusCode(is(200));

   }


   @AfterAll
    public static void teardown(){

       RestAssured.reset();
   }


}
