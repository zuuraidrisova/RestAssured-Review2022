package review.day03_Post_Put_Patch_Delete;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ExtractingDataFromResponseBodyUsingJsonPath {

    @BeforeAll
    public static void setup(){

        baseURI = "http://www.omdbapi.com/";

    }

    @DisplayName("getting data from response body")
    @Test
    public void testMovieAPIEndpoint(){

        given().
                accept(ContentType.JSON).
                log().all().
                queryParam("apikey","26aa5b74").
                queryParam("t", "Boss Baby").
                queryParam("plot", "full").
        when().
                get().
        then().
                log().all().
                contentType(ContentType.JSON).
                statusCode(equalTo(200));

    }

    // provide your baseURI "http://www.omdbapi.com/" in the test
    // add query parameters
    // apikey 26aa5b74 here
    // t = Boss Baby
    // Save the response into response object

    @DisplayName("extracting data from response body using Response, JsonPath")
    @Test
    public void testExtractingDataFromResponseBody(){

        Response response =

        given().
                accept(ContentType.JSON).
                log().all().
                queryParam("apikey","26aa5b74").
                queryParam("t", "Boss Baby").
                queryParam("plot", "full").
        when().
                get();


        response.prettyPeek();

        //jsonPath takes no parameters and returns an object called JsonPath

        //JsonPath is a class  coming from restAssured
        // that has lots of methods to get the body data
        // in different data types
        //we get this object by calling the method of Response object called .jsonPath();

        JsonPath jsonPath = response.jsonPath();

        //get title as string
        String title = jsonPath.getString("Title");

        System.out.println("title = " + title);


        //get year as an int
        int year = jsonPath.getInt("Year");

        System.out.println("year = " + year);



        //get director as string
        String director = jsonPath.getString("Director");

        System.out.println("director = " + director);



        //get the first rating source as string
        String rating = jsonPath.getString("Ratings[0].Value");

        System.out.println("rating = " + rating);


        //store the entire response as map :  Map< String, Object >
        //since our response is a json object  with key value pair
        //we can directly call getMap method and provide the path,
        // store the whole thing in map object
        Map< String, Object > responseAsMap = jsonPath.getMap("");

        System.out.println("responseAsMap = " + responseAsMap);

        System.out.println("responseAsMap.size() = " + responseAsMap.size());

        System.out.println(responseAsMap.get("Awards"));

        //store first rating json object into a map

        Map<String, String> firstRating = jsonPath.getMap("Ratings[0]");

        System.out.println(firstRating);


    }


}
