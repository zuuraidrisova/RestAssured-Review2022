package review.day10_RequestSpecResponseSpec;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import review.utility.ConfigurationReader;
import review.utility.LibraryAppUtil;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class LibraryReusingRequestResponseSpec_shorter {



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

     @BeforeAll
    public static void setup(){

         RestAssured.baseURI = ConfigurationReader.getProperty("library2.base_url");
         RestAssured.basePath = "/rest/v1";

         String username = ConfigurationReader.getProperty("library2.librarian_username");
         String password = ConfigurationReader.getProperty("library2.librarian_password");

         String token = LibraryAppUtil.loginAndGetToken(username, password);

         //just like baseUri and basePath, we can use static fields of RestAssured
         //to set it at global level
         RestAssured.requestSpecification = given().accept(ContentType.JSON).
                 header("x-library-token", token).log().all();

         RestAssured.responseSpecification = expect().statusCode(is(200)).
                 contentType(ContentType.JSON).logDetail(LogDetail.ALL);


     }



    @DisplayName("GET /dashboard_stats with requestSpec and responseSpec")
    @Test
    public void testGet_dashboard_status_WithRequestResponseSpec(){


               when().get("/dashboard_stats");


    }


    @DisplayName("GET /get_book_categories with requestSpec and responseSpec")
    @Test
    public void testGET_book_categories_WithRequestResponseSpec(){


         when().get("/get_book_categories");

    }


    @DisplayName("GET /get_all_users with requestSpec and responseSpec")
    @Test
    public void testGet_all_users_WithRequestResponseSpec(){

         when().get("/get_all_users");

    }


     @AfterAll
    public static void teardown(){

         RestAssured.reset();

     }


}
