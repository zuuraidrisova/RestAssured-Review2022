package review.day08_DB_API_COMBO_DDT_CSV;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import review.utility.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DDT_LibraryApp_WithCSV {

    /*
    --- Create a csv file under resources folder called credentials.csv
    -- it has 2 column , username , password
    --- copy the library1 username password I shared under codenote to create this file
    ----
    We will write a parameterized test for POST /login endpoint
    if the username and password is valid
        you should simply get 200 and the token field should not be null
     */

    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = ConfigurationReader.getProperty("library2.base_url");
        RestAssured.basePath = "/rest/v1";

    }

    @ParameterizedTest(name = "Iteration {index}:  {0} {1} ")
    @CsvFileSource(resources = "/credentials.csv", numLinesToSkip = 1)
    public void testPrintUsernamePasswordFromCSV(String username, String password){

        System.out.println(username+" "+password);

    }

    @ParameterizedTest(name = "Iteration {index}:  {0} {1} ")
    @CsvFileSource(resources = "/credentials.csv", numLinesToSkip = 1)
    public void testLoginEndpointWithCSV_forDDT(String username, String password){

        given().
                contentType(ContentType.URLENC).
                formParam("email", username).
                formParam("password", password).
        when().
                post("/login").
        then().
                statusCode(200).
                body("token", is(notNullValue()));

    }


    @AfterAll
    public static void teardown(){

        RestAssured.reset();
    }

}
