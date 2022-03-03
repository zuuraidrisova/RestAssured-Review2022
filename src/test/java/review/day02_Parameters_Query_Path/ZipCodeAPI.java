package review.day02_Parameters_Query_Path;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZipCodeAPI {

    /*
     Please open up new class called ZipCode Test
	    Add baseURI as api.zippopotam.us
	    	basePath as /us/
	    under @BeforeAll

	    add
	    	path variable {zipcode} in given section
	    send Get request
	    then  check the status code 200
	    check the contentype header is json
	         body : post code -- the zipcode you entered
	         		country  United States
	         	    longitude  -- the expected value
	         	    state    --  the expected value

     */

    @BeforeAll
    public static void setup(){

        baseURI = "http://api.zippopotam.us";
        basePath = "/us";

    }

    @DisplayName("testing zip to city endpoint")
    @Test
    public void testZipToCityCode(){


        given().
                accept(ContentType.JSON).
                pathParam("zipCode", 44123).
                log().all().
        when().
                get("/{zipCode}").
        then().
                log().all().
                statusCode(equalTo(200)).
                contentType(ContentType.JSON).
                body("country",equalTo("United States")).
                body(" 'post code' ", equalTo("44123")).
                body(" 'country abbreviation' ", is("US")).
                body("places[0].state", equalTo("Ohio")).
                body("places[0].'place name'", equalTo("Euclid")).
                body("places[0].latitude", equalTo("41.6025")).
                body("places[0].longitude", equalTo("-81.5258"));


    }


    @DisplayName("testing city to zip endpoint")
    @Test
    public void testCityToZip(){

        given().
                accept(ContentType.JSON).
                pathParam("state", "Oh").
                pathParam("city", "Euclid").
                log().all().
        when().
                get("/{state}/{city}").
        then().
                log().all().
                statusCode(equalTo(200)).
                contentType(ContentType.JSON).
                body("country",equalTo("United States")).
                body(" places[1].'post code' ", equalTo("44123")).
                body(" 'country abbreviation' ", is("US")).
                body(" 'state abbreviation' ", is("OH")).
                body("state", equalTo("Ohio")).
                body(" 'place name'", equalTo("Euclid")).
                body("places[1].latitude", equalTo("41.6025")).
                body("places[1].longitude", equalTo("-81.5258"));



    }


    @DisplayName("testing city to zip endpoint alternative way")
    @Test
    public void testCityToZipAnotherWay(){

        given().
                accept(ContentType.JSON).
                log().all().
         when().
                get("/{state}/{city}", "OH","Euclid").
        then().
                log().all().
                statusCode(equalTo(200)).
                contentType(ContentType.JSON).
                body("country",equalTo("United States")).
                body(" places[1].'post code' ", equalTo("44123")).
                body(" 'country abbreviation' ", is("US")).
                body(" 'state abbreviation' ", is("OH")).
                body("state", equalTo("Ohio")).
                body(" 'place name'", equalTo("Euclid")).
                body("places[1].latitude", equalTo("41.6025")).
                body("places[1].longitude", equalTo("-81.5258"));



    }

}
