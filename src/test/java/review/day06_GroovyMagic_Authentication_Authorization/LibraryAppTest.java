package review.day06_GroovyMagic_Authentication_Authorization;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import review.day05_POJO_AuthenticationAuthorization.PostRequestWithFormAsBody_LibraryApp;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LibraryAppTest {

    //posting to library app
    // body is not json , it's x-www-urlencoded-form-data

    //https://library2.cybertekschool.com/rest/v1/login
    // baseURI  is https://library2.cybertekschool.com
    // basePath is /rest/v1
    // we are working on POST /login

    // Post body , type x-www-urlencoded-form-data
    //email : librarian123455@library
    //password : QoyNEHxI

    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = "https://library2.cybertekschool.com";
        RestAssured.basePath = "/rest/v1";


    }

    @DisplayName("test library app /dashboard_stats endpoint using authentication")
    @Test
    public void testDashBoardStatsEndpointWithToken(){


        // libraryApp should have authorization in header section
        // as token "x-library-token"  and longToken
        // in order to be able to send any  get requests

        String token = PostRequestWithFormAsBody_LibraryApp.loginAndGetToken("librarian123455@library","QoyNEHxI");

        /*

        actual is below:

        {
    "book_count": "23459",
    "borrowed_books": "4453",
    "users": "7396"
    }
         */

        given().
                log().all().
                header("x-library-token", token).
        when().
                get("/dashboard_stats").
        then().
                log().all().
                statusCode(is(200)).
                body("book_count", equalTo("23459")).
                body("borrowed_books", is("4453")).
                body("users", is(equalTo("7396")));


    }


}
