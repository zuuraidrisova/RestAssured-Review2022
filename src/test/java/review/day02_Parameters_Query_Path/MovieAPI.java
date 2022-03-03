package review.day02_Parameters_Query_Path;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class MovieAPI {

    /*
    // make a request
	      by adding few query parameters like
	      apikey =  26aa5b74
	      t =  the movie you want to search
	      plot =  full
	      then status code 200
	      		contentype is json
	      		body
	      			title is what you are searching for
	      			year is as you expected
	      			first rating value
	      			last rating value
     */

    @BeforeAll
    public static void setup(){

        baseURI = "http://www.omdbapi.com";

    }

    @DisplayName("test Movie API to practice query param")
    @Test
    public void testMovieEndpoint(){

        given().
                accept(ContentType.JSON).
                log().all().
                queryParam("apikey", "26aa5b74").
                queryParam("t","The next three days").
                queryParam("plot", "full").
        when().
                get().//what if my url already complete, then do nothing
        then().
                log().all().
                statusCode(equalTo(200)).
                contentType(ContentType.JSON).
                body("Title", is("The Next Three Days")).
                body("Year", equalTo("2010")).
                body("Ratings[0].Value", equalTo("7.3/10")).
                body("Actors", equalTo("Russell Crowe, Elizabeth Banks, Liam Neeson"));


    }


}
