package review.day09_DDT_withCSV;


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

public class DDT_PostSpartanBodyAsJSON_WithCSV {

       /*

----- Data Drive your POST /api/spartans request
----  Create a csv file called allSpartans.csv under src/test/resources folder
            add 3 column name , gender , phone
            add 6 rows of valid data
            then try to send post request using these data

     */

       @BeforeAll
    public static void setup(){

        baseURI = ConfigurationReader.getProperty("spartan1.base_url");
        basePath = "/api";

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/AllSpartans.csv", numLinesToSkip = 1)
    public void testPostSpartanUsingCSWForDDT(String name, String gender, long phone){

        Spartan spartan = new Spartan(name, gender, phone);

        given().
                contentType(ContentType.JSON).
                log().all().
                body(spartan).
        when().
                post("/spartans").
        then().
                statusCode(is(201)).
                contentType(ContentType.JSON).
                body("success", is("A Spartan is Born!")).
                body("data.name", equalTo(name)).
                body("data.gender", equalTo(gender)).
                body("data.phone", equalTo(phone)).
                body("data.id", is(notNullValue()));


    }


    @AfterAll
    public static void teardown(){

           RestAssured.reset();
    }


}

