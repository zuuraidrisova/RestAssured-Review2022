package review.day11_HowToHandleRandomData;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import review.utility.ConfigurationReader;
import review.utility.DB_Utility;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Spartan_GettingTestDataFromDB {

    /*
***If the data change often and you have access to DB
	Query the database to get up to date data and feed the data to your test.
 */

    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = ConfigurationReader.getProperty("spartan1.base_url");
        RestAssured.basePath = "/api";

        String urlDB = ConfigurationReader.getProperty("sp.database.url");
        String usernameDB = ConfigurationReader.getProperty("sp.database.username");
        String passwordDB = ConfigurationReader.getProperty("sp.database.password");

        DB_Utility.createConnection(urlDB, usernameDB, passwordDB);


    }


    @DisplayName("Testing get /spartans/{id} by getting the id from DB")
    @Test
    public void testAPI_DB_COMBO(){


        DB_Utility.runQuery("select distinct * from spartans order by spartan_id desc");

        int rowCount = DB_Utility.getRowCount();

        System.out.println("rowCount = " + rowCount);

        //getting and printing all ids from spartan db
        List<String> ids = DB_Utility.getEntireColumnDataAsList("SPARTAN_ID");

        System.out.println("ids = " + ids);

        System.out.println("ids.size() = " + ids.size());

        //getting and printing all names from spartan db
        List<String> names = DB_Utility.getColumnDataAsList("NAME");

        System.out.println("names = " + names);

        System.out.println("names.size() = " + names.size());

        //getting and printing all genders from spartan db
        List<String> genders = DB_Utility.getColumnDataAsList("gender");

        System.out.println("genders = " + genders);

        System.out.println("genders.size() = " + genders.size());

        int firstIdDB = Integer.parseInt(ids.get(0));

        String firstNameDB = names.get(0);

        String firstGenderDB = genders.get(0);

        //api starts below
        //use id u got from db to send get request to /spartans/id endpoint
        given().
                accept(ContentType.JSON).
                log().all().
                pathParam("id", firstIdDB).
        when().
                get("/spartans/{id}").
        then().
                statusCode(is(200)).
                contentType(ContentType.JSON).
                body("name", is(firstNameDB)).
                body("gender", is(firstGenderDB)).
                body("id", equalTo(firstIdDB));



    }



    @DisplayName("Testing get /spartans/{id} by getting the id from DB randomly")
    @RepeatedTest(1)
    public void testDataFromDB_randomly(){

        DB_Utility.runQuery("select distinct * from spartans order by spartan_id desc");

        List<String> ids = DB_Utility.getEntireColumnDataAsList("SPARTAN_ID");

        int originalSize = ids.size();

        System.out.println("ids = " + ids);

        System.out.println("original Size = " + originalSize);

        //storing only unique ids from query
        Set<String> idsUnique = new LinkedHashSet<>(ids);

        int uniqueSize = idsUnique.size();

        System.out.println("idsUnique = " + idsUnique);

        System.out.println("uniqueSize = " + uniqueSize);

        Random random = new Random();

        int randomIndex = random.nextInt(uniqueSize);

        System.out.println("randomIndex = " + randomIndex);

        //api starts below
        //use id u got from db to send get request to /spartans/id endpoint

        given().
                accept(ContentType.JSON).
                log().all().
                pathParam("id", randomIndex).
        when().
                get("/spartans/{id}").
        then().
                log().all().
                statusCode(is(200)).
                contentType(ContentType.JSON).
                body("id", equalTo(randomIndex));


    }


    @AfterAll
    public static void teardown(){

        RestAssured.reset();

        DB_Utility.destroy();

    }


}
