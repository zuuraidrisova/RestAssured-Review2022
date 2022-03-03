package review.day06_GroovyMagic_Authentication_Authorization;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import review.utility.LibraryAppUtil;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LibraryAppTestWithTokenGeneratedInBeforeAll {

    //posting to library app
    // body is not json , it's x-www-urlencoded-form-data

    //https://library2.cybertekschool.com/rest/v1/login
    // baseURI  is https://library2.cybertekschool.com
    // basePath is /rest/v1
    // we are working on POST /login

    // Post body , type x-www-urlencoded-form-data
    //email : librarian123455@library
    //password : QoyNEHxI

    private static String libraryToken;

    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = "https://library2.cybertekschool.com";
        RestAssured.basePath = "/rest/v1";


        // libraryApp should have authorization in header section
        // as token "x-library-token"  and longToken
        // in order to be able to send any  get requests

        libraryToken = LibraryAppUtil.loginAndGetToken("librarian123455@library", "QoyNEHxI");

    }


    @DisplayName("test library app /dashboard_stats endpoint using token")
    @Test
    public void testDashBoardStatsEndpointWithToken(){

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
                header("x-library-token", libraryToken).
        when().
                get("/dashboard_stats").
        then().
                log().all().
                statusCode(is(200)).
                body("book_count", equalTo("23460")).
                body("borrowed_books", is("4453")).
                body("users", is(equalTo("7396")));


    }



    //add a test for the POST /decode endpoint
    // this endpoint does not need authorization
    // it accept form param as name token value your long token
    // and return json response as user information and authority
    // assert the email of user is same as the email you used the token

    @DisplayName("test /decode endpoint")
    @Test
    public void testDecodeEndpoint(){

        given().
                log().all().
                contentType(ContentType.URLENC).//this specify what kind of data u r sending to the server as body
                accept(ContentType.JSON).//this is telling i want json back as response
                formParam("token",libraryToken).
        when().
                post("/decode").
        then().
                log().all().
                statusCode(is(200)).
                body("email", is("librarian123455@library")).
                body("id", is("7278")).
                body("full_name", is("Test Librarian 123455")).
                body("user_group_id", is("2"));


    }


    @DisplayName("Test /get_user_by_id/2080 endpoint")
    @Test
    public void testSingleUserUsingID(){

        //2080 is valid id

        given().
                log().all().
                accept(ContentType.JSON).
                header("x-library-token", libraryToken).
                pathParam("id", 2080).
        when().
                get("/get_user_by_id/{id}").
        then().
                log().all().
                statusCode(is(200)).
                body("id",is("2080")).
                body("full_name", is("dfasdf")).
                body("email", equalTo("fasf@fa.com")).
                body("password", is("c7d48bbf2b960adc10b0aba11bf336a5")).
                body("user_group_id", is("3")).
                body("image", is(nullValue())).
                body("extra_data", is(nullValue())).
                body("status", is("ACTIVE")).
                body("is_admin", is("0")).
                body("start_date", is("2020-11-06")).
                body("end_date", is("2020-12-06")).
                body("address", equalTo("afafasdfdasa"));



    }


    ///get_all_users
    @DisplayName("Test /get_all_users endpoint")
    @Test
    public void testGetAllUsersEndpoint(){

        given().
                log().all().
                header("x-library-token", libraryToken).
        when().
                get("/get_all_users").
        then().
                log().all().
                statusCode(is(200)).
                contentType(ContentType.JSON);

    }



    //get_user_groups
    @DisplayName("Test /get_user_groups endpoint")
    @Test
    public void testGetUserGroupsEndpoint(){

        given().
                log().all().
                header("x-library-token", libraryToken).
        when().
                get("/get_user_groups").
        then().
                log().all().
                statusCode(is(200)).
                contentType(ContentType.JSON).
                body("[0].id", is("2")).
                body("[0].name", is("Librarian")).
                body("[1].id", is("3")).
                body("[1].name", is("Students"));

    }


    ///get_book_categories
    @DisplayName("Test /get_book_categories endpoint")
    @Test
    public void testGetBookCategoriesEndpoint(){


        given().
                header("x-library-token", libraryToken).
                log().all().
        when().
                get("/get_book_categories").
        then().
                log().all().
                statusCode(is(200)).
                contentType(ContentType.JSON).
                body("[0].id", is("1")).
                body("[0].name", is("Action and Adventure"));

    }


}



