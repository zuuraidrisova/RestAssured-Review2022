package review.day10_RequestSpecResponseSpec;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import review.utility.ConfigurationReader;
import review.utility.LibraryAppUtil;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LibraryAppReusingRequestResponseSpec {

     /*

    We will use these 3 endpoints :
* GET /dashboard_stats
* GET /get_book_categories
* GET /get_all_users

We want to save the Request spec for
  * setting the X-LIBRARY-TOKEN in HEADER
  * ContentType Header
  * logging everything

We want to save the Response spec for
  * Status code of `200`
  * ContentType Header is JSON
  * log if validation fail

     */

    public static RequestSpecification requestSpecification;
    public static ResponseSpecification responseSpecification;

    private static String libraryToken;


    @BeforeAll
    public static void setup(){

        baseURI = ConfigurationReader.getProperty("library2.base_url");
        basePath = "/rest/v1";

        libraryToken = LibraryAppUtil.loginAndGetToken(
                ConfigurationReader.getProperty("library2.librarian_username"),
                ConfigurationReader.getProperty("library2.librarian_password"));


        requestSpecification = given().
                log().all().
                accept(ContentType.JSON).//we want json back
                header("x-library-token", libraryToken);

        responseSpecification = expect().statusCode(is(200))
                .contentType(ContentType.JSON).logDetail(LogDetail.ALL);



    }


    @DisplayName("GET /dashboard_stats with requestSpec and responseSpec")
    @Test
    public void testGet_dashboard_status_WithRequestResponseSpec(){

        given().
                spec(requestSpecification).
        when().
                get("/dashboard_stats").
        then().
                spec(responseSpecification);

    }

    @DisplayName("GET /get_book_categories with requestSpec and responseSpec")
    @Test
    public void testGET_book_categories_WithRequestResponseSpec(){


        given().
                spec(requestSpecification).
        when().
                get("/get_book_categories").
        then().
                spec(responseSpecification);

    }

    @DisplayName("GET /get_all_users with requestSpec and responseSpec")
    @Test
    public void testGet_all_users_WithRequestResponseSpec(){

        given().
                spec(requestSpecification).
        when().
                get("/get_all_users").
        then().
                spec(responseSpecification);

    }



    @AfterAll
    public static  void teardown(){

        RestAssured.reset();
    }



}
