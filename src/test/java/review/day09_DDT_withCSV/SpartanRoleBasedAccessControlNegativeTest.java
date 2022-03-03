package review.day09_DDT_withCSV;


import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import review.pojo.Spartan;
import review.utility.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class SpartanRoleBasedAccessControlNegativeTest {


      /*
    -------  We are doing a role based access control test
     -- for the Spartan app with username password
        for the credentials  user/user

       user should not be able to delete data
       user should not be able to post data
       user should not be able to update data

       all these 3 tests share same username and password
       and we can also add accept json result back
       and we want to log all the request

        all these test can share same response status as 403
        and all tests response content type is json
        and all test has Date header not null assertion
        and we want to see the log of all request
     */


      @BeforeAll
    public static void setup(){

          baseURI = ConfigurationReader.getProperty("spartan1.base_url");
          basePath = "/api";

      }


    @DisplayName("User should not be able to post data")
    @Test
    public void testUserCannotPostData(){

          Spartan spartan = new Spartan("Alicia", "Female", 2816398112l);

          given().
                  accept(ContentType.JSON).
                  auth().basic("user", "user").
                  contentType(ContentType.JSON).
                  log().all().
                  body(spartan).
          when().
                  post("/spartans").
          then().
                  statusCode(is(403)).
                  contentType(ContentType.JSON).
                  header("Date", is(notNullValue()));


     }



    @DisplayName("User should not be able to update data")
    @Test
    public void testUserCannotUpdateData(){

        Map<String, Object> updatedBody = new HashMap<>();

        updatedBody.put("name", "Zaya");
        updatedBody.put("gender", "Female");
        updatedBody.put("phone", 23817039612l);

        given().
                accept(ContentType.JSON).
                auth().basic("user", "user").
                contentType(ContentType.JSON).
                log().all().
                body(updatedBody).
        when().
                put("/spartans/{id}", 950).
        then().
                statusCode(is(403)).
                contentType(ContentType.JSON).
                header("Date", is(notNullValue()));
    }


    @DisplayName("User should not be able to delete data")
    @Test
    public void testUserCannotDeleteData(){

        given().
                accept(ContentType.JSON).
                auth().basic("user", "user").
                contentType(ContentType.JSON).
                log().all().
         when().
                delete("/spartans/{id}", 950).
         then().
                statusCode(is(403)).
                contentType(ContentType.JSON).
                header("Date", is(notNullValue()));

    }

    @AfterAll
    public static void teardown(){

          RestAssured.reset();
      }


}
