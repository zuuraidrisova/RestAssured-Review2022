package review.day03_Post_Put_Patch_Delete;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ExtractingDataFromResponseUsingPathMethod {

    // provide your baseURI "http://www.omdbapi.com/"in the test
    // add query parameters
    // apikey 26aa5b74 here
    // t = Boss Baby
    // Save the response into response object

    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = "http://www.omdbapi.com/";

    }

    @DisplayName("extracting data from response using path() method")
    @Test
    public void testExtractingDataFromResponseUsingPath(){

        Response response =

        given().
                accept(ContentType.JSON).
                log().all().
                queryParam("apikey", "26aa5b74").
                queryParam("t", "Boss Baby").
        when().
                get();


        Assertions.assertEquals(response.statusCode(), 200);


        String title =  response.path("Title");

        System.out.println("title = " + title);



        String year = response.path("Year");

        System.out.println("year = " + year);



        String rating = response.path("Ratings[0].Value");

        System.out.println("rating = " + rating);



        String director = response.path("Director");

        System.out.println("director = " + director);


    }


}
