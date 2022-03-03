package review.day02_Parameters_Query_Path;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SpartanJavaToJsonTestWithBeforeAll {


    @BeforeAll
    public static void setup(){


           RestAssured.baseURI = "http://54.236.150.168:8000";
           RestAssured.basePath = "/api";

    }

    @DisplayName("Test get all spartans endpoint")
    @Test
    public void getAllSpartans(){

        //http://54.236.150.168:8000/api/spartans

        given().
                //specifying that you want to get a json result back
                accept(ContentType.JSON).
         when().
                get("/spartans").//this is endpoint
         then().
                statusCode(equalTo(200));

    }


    @DisplayName("Test all get spartans endpoint using baseURI, basePath")
    @Test
    public void getAllSpartans2(){

        // send the same request specifying the accept header is application/json
        // use baseuri basepath , check status code 200 , contentType header is json
        //http://54.236.150.168:8000/api/spartans

        given().
                //specifying that you want to get a json result back
                accept(ContentType.JSON).
        when().
                get("/spartans").
        then().
                statusCode(equalTo(200)).
                contentType(ContentType.JSON);



    }


    //add another test for hello endpoint by reusing the baseURI , basePath above
    //specify that you want to get a text result back
    //check status code is 200
    //check content type is text
    //check the body is Hello from Sparta

    @DisplayName("testing hello endpoint")
    @Test
    public void testHelloEndpoint(){

        given().
                //specifying that you want to get a text result back
                accept(ContentType.TEXT).
        when().
                //sending request to baseuri basepath + /hello
                get("/hello").
        then().
                statusCode(200).//checking status code is 200
                contentType(ContentType.TEXT).//checking if content type u get is text
                body(equalTo("Hello from Sparta"));//checking the body


    }



    @DisplayName("Get 1 single spartan")
    @Test
    public void testSingleSpartan(){

        given().
                accept(ContentType.JSON).
                log().all().
        when().
                get("/spartans/979").
                prettyPeek().
        then().
                log().all().
                statusCode(200);

    }


}
