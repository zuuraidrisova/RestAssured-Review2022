package review.day08_DB_API_COMBO_DDT_CSV;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import review.pojo.Spartan;
import review.utility.ConfigurationReader;
import static org.hamcrest.Matchers.*;


public class DDT_SpartanPostRequestWithCSV {

       /*

----- Data Drive your POST /api/spartans request
----  Create a csv file called allSpartans.csv under src/test/resources folder
            add 3 column name , gender , phone
            add 6 rows of valid data
            then try to send post request using these data

     */

       @BeforeAll
    public static  void setup(){

           RestAssured.baseURI = ConfigurationReader.getProperty("spartan1.base_url");
           RestAssured.basePath = "/api";

    }


    @ParameterizedTest(name = "iteration {index} | name : {0}, gender : {1}, phone : {2}")
    @CsvFileSource(resources = "/AllSpartans.csv", numLinesToSkip = 1)
    public void testSpartanPostRequestWithCSVFile_DDT(String name, String gender, long phone){

        Spartan spartan = new Spartan(name, gender, phone);

      //Data Drive your POST /api/spartans request

        given().
                contentType(ContentType.JSON).
                log().all().
                body(spartan).
        when().
                post("/spartans").
        then().
                log().all().
                statusCode(is(201)).
                body("success", equalTo("A Spartan is Born!"));


    }


    @AfterAll
    public static  void teardown(){

           RestAssured.reset();
    }


}
