package review.day09_DDT_withCSV;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class DDT_ZipCodeWithCSV {

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

    @ParameterizedTest(name = "Zip Code for DDT, assert state and city")
    @CsvFileSource(resources = "/zipCode.csv", numLinesToSkip = 1)
    public void testZipCodeForDDT(String state, String city){

       given().
               log().all().
               pathParam("state", state ).
               pathParam("city", city).
       when().
               get("/{state}/{city}").
       then().
               log().all().
               statusCode(200).
               body(" 'place name' ", equalTo(city)).
               body(" 'state abbreviation' ", equalTo(state));

    }

    @ParameterizedTest(name = "Zip Code for DDT and assert count of zipcodes ")
    @CsvFileSource(resources = "/zipCode.csv", numLinesToSkip = 1)
    public void testZipCodeForDDT1(String state, String city, int expectedNumberOfZipCodes){

        Response response =

        given().
                log().all().
                pathParam("state", state ).
                pathParam("city", city).
        when().
                get("/{state}/{city}");


        JsonPath jsonPath = response.jsonPath();

        List<String> listOfZip = jsonPath.getList("places.'post code' ");

        int actualNumberOfZipCode = listOfZip.size();

        System.out.println("actualNumberOfZipCode = " + actualNumberOfZipCode);

        assertThat(actualNumberOfZipCode, equalTo(expectedNumberOfZipCodes));

        assertThat(listOfZip, hasSize(expectedNumberOfZipCodes));

    }


    @AfterAll
    public static void teardown(){

            RestAssured.reset();
    }

}
