package review.day05_POJO_AuthenticationAuthorization;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PostRequestWithFormAsBody_LibraryApp {

    //posting to library app
    // body is not json , it's x-www-urlencoded-form-data

    //https://library2.cybertekschool.com/rest/v1/login
    // baseURI  is https://library2.cybertekschool.com
    // basePath is /rest/v1
    // we are working on POST /login

    // Post body , type x-www-urlencoded-form-data
    //email : librarian123455@library
    //password : QoyNEHxI

    // in URLs if you do not see port , because it's omitted because it's so common
    //  http --->> 80
    //  https --->> 443
    //  anything other than above 2 ports need to be explicitly set in the URL
    // for example spartan app use port 8000 -->> yourip:8000


    @BeforeAll
    public static void setup(){

        RestAssured.baseURI= "https://library2.cybertekschool.com";
        RestAssured.basePath = "/rest/v1";

    }

    @DisplayName("test post request to library app with x-www-urlencoded-form-data")
    @Test
    public void testPostRequestLoginEndpointLibraryApp(){

        String username = "librarian123455@library";
        String password = "QoyNEHxI";

        given().
                log().all().
                contentType(ContentType.URLENC).
                formParam("email", username).
                formParam("password", password ).
        when().
                post("/login").
        then().
                log().all().
                statusCode(is(200)).
                //we cannot validate token since it is dynamic,
                // but we can see whether it is null or not null
                body("token", is(notNullValue()));


    }


    /**
     * A static utility method to get the token by providing username and password
     * as Post request to /Login endpoint and capture the token field from response json
     * @param username
     * @param password
     * @return the token as String from the response json
     */

    public static String loginAndGetToken(String username, String password){

        baseURI = "https://library2.cybertekschool.com";
        basePath = "/rest/v1";

        Response response =

        given().
                contentType(ContentType.URLENC).
                formParam("email", username).
                formParam("password", password).
        when().
                post("/login");


        JsonPath jsonPath = response.jsonPath();

        String token = jsonPath.getString("token");

        return token;


    }


    @DisplayName("test loginAndGetToken() method for later reuse")
    @Test
    public void testLoginAndGetTokenMethod(){

        String username = "librarian123455@library";
        String password = "QoyNEHxI";

        String token = loginAndGetToken(username, password);

        System.out.println("token = " + token);

    }


}
