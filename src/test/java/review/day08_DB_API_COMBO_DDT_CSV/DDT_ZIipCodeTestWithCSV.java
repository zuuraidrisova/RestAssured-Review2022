package review.day08_DB_API_COMBO_DDT_CSV;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class DDT_ZIipCodeTestWithCSV {

    /*
    --- Data Drive your GET http://api.zippopotam.us/us/:state/:city
    --- Create a csv file called state_city.csv
        add 3 column  state , city , numberOfZipcodes
                      VA ,  Fairfax , 9(send the request and prepare this number)
         assert the state , city  and number of zipcodes you got from the response

     */

    @BeforeAll
    public static void setup(){

        baseURI = "http://api.zippopotam.us";
        basePath = "/us";

    }

    @ParameterizedTest(name = "Iteration {index} : state: {0}, city {1}")
    @CsvFileSource(resources = "/zipCode.csv", numLinesToSkip = 1)
    public void testZipCode(String state, String city, int numberOfZipcodes){


        Response response =

        given().
                contentType(ContentType.JSON).
                log().all().
                pathParam("state",state).
                pathParam("city", city).
        when().
                get("/{state}/{city}");


        JsonPath jsonPath = response.jsonPath();

        //state/:city

        String place =  response.jsonPath().getString("places[0].'place name'");

        System.out.println("place = " + place);


    }


    @AfterAll
    public static void teardown(){

        RestAssured.reset();


    }
}
