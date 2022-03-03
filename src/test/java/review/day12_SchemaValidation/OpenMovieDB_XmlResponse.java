package review.day12_SchemaValidation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class OpenMovieDB_XmlResponse {



    //http://www.omdbapi.com/?t=Boss Baby&r=xml
    //apikey is 26aa5b74

    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = "http://www.omdbapi.com";

    }



    @DisplayName("send request to open db movie api and validate xml response")
    @Test
    public void testOpenMovieDB_xmlResponseValidation(){

        given().
                log().all().
                queryParam("apikey", "26aa5b74").
                queryParam("t", "Boss Baby").
                queryParam("r","xml").
        when().
                get("").
        then().
                log().all().
                statusCode(is(200)).
                contentType(ContentType.XML).
                //the result has only 2 elements, parent : root and child: movie
                //all the movie data is stored under movie attributes
                 body("root.movie.@title", equalTo("The Boss Baby")).
                body("root.movie.@year", equalTo("2017")).
                body("root.movie.@rated", equalTo("PG")).
                body("root.movie.@released", equalTo("31 Mar 2017")).
                body("root.movie.@runtime", equalTo("97 min")).
                body("root.movie.@genre", equalTo("Animation, Adventure, Comedy")).
                body("root.movie.@director", equalTo("Tom McGrath")).
                body("root.movie.@writer", equalTo("Michael McCullers, Marla Frazee")).
                body("root.movie.@actors", equalTo("Alec Baldwin, Steve Buscemi, Jimmy Kimmel")).
                body("root.movie.@plot", equalTo("A suit-wearing, briefcase-carrying baby pairs up with his 7-year old brother to stop the dastardly plot of the CEO of Puppy Co.")).
                body("root.movie.@language", equalTo("English, Spanish")).
                body("root.movie.@country", equalTo("United States")).
                body("root.movie.@poster", equalTo("https://m.media-amazon.com/images/M/MV5BMTg5MzUxNzgxNV5BMl5BanBnXkFtZTgwMTM2NzQ3MjI@._V1_SX300.jpg")).
                body("root.movie.@metascore", equalTo("50")).
                body("root.movie.@imdbRating", equalTo("6.3")).
                body("root.movie.@imdbVotes", equalTo("121,321")).
                body("root.movie.@imdbID", equalTo("tt3874544")).
                body("root.movie.@type", equalTo("movie"));



    }


    @AfterAll
    public static void teardown(){

        RestAssured.reset();
    }



}
