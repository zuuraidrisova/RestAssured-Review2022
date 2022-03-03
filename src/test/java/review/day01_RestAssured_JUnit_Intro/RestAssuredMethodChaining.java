package review.day01_RestAssured_JUnit_Intro;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//In order to use REST assured effectively it's recommended to statically
// import methods from the following classes:

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RestAssuredMethodChaining {

    @Test
    public void testMethodChaining(){

        //given().
        //some more information you want to provide other than request URL
        //like header, query parameter, path variable, body...

        //when().
        //you send the request GET POST PUT PATCH, or DELETE

        //then()
        //Validate something here.
        //this is where assertions start, u can chain multiple assertions using . dot

        //http://54.236.150.168:8000/api/hello

        when()
                .get("http://54.236.150.168:8000/api/hello").
                //here we are sending request to url

        then()
                //asserting status code is 200
                .statusCode(200)
                //asserting content length is 17
                .header("Content-Length", equalTo("17"));


        System.out.println("Success");

    }

    @DisplayName("testing body using matchers")
    @Test
    public void testBodyUsingMatcher(){

        when()
                .get("http://54.236.150.168:8000/api/hello")
                .prettyPeek()
        .then()
                .statusCode( is(200))
                //asserting whether body is Hello from Sparta
                .body(equalTo("Hello from Sparta"))
                .header("Content-Type", startsWith("text/plain"));

    }

    @DisplayName("test Get spartans endpoint using given()")
    @Test
    public void testGetSpartansEndpointUsingGiven(){

        //add all ur request specific info like header, query parameter, path var, body here

        given().
                header("Accept","application/xml").
        when().
                get("http://54.236.150.168:8000/api/spartans").
        then().
                statusCode(equalTo(200))
                .contentType(equalTo("application/xml"));

    }

}
