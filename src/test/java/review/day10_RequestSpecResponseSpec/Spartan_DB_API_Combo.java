package review.day10_RequestSpecResponseSpec;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import review.utility.ConfigurationReader;
import review.utility.DB_Utility;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class Spartan_DB_API_Combo {

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
    public void testDataFromDB_verifyWithAPI(){

        DB_Utility.runQuery("select * from spartans order by spartan_id desc");

       // DB_Utility.displayAllData();

        int rowCount =  DB_Utility.getRowCount();

        System.out.println("rowCount = " + rowCount);

        Map<String, String> firstRowAsMap  = DB_Utility.getRowDataAsMap(1);

        System.out.println("firstRowAsMap = " + firstRowAsMap);

        //get the id, name, gender, phone out of this map

        int firstIDFromDB = Integer.parseInt(firstRowAsMap.get("SPARTAN_ID"));

        System.out.println("firstID = " + firstIDFromDB);

        String firstNameFromDB =firstRowAsMap.get("NAME");

        System.out.println("firstName = " + firstNameFromDB);

        String firstGenderFromDB = firstRowAsMap.get("GENDER");

        System.out.println("firstGender = " + firstGenderFromDB);

        long firstPhoneFromDB = Long.parseLong(firstRowAsMap.get("PHONE"));

        System.out.println("firstPhone = " + firstPhoneFromDB);


        given().
                accept(ContentType.JSON).
                log().all().
                pathParam("id", firstIDFromDB).
        when().
                get("/spartans/{id}").
        then().
                log().all().
                statusCode(is(200)).
                contentType(ContentType.JSON).
                body("id", is(firstIDFromDB)).
                body("name", is(equalTo(firstNameFromDB))).
                body("gender", is(equalTo(firstGenderFromDB))).
                body("phone", is(equalTo(firstPhoneFromDB)));



    }


    @DisplayName("Testing get /spartans/{id} by getting the id from DB randomly")
    @Test
    public void testDataFromDB_randomly(){

        DB_Utility.runQuery("select * from spartans");

        int rowCount =  DB_Utility.getRowCount();

        Faker faker = new Faker();

        int random = faker.number().numberBetween(1, rowCount);

        Map<String, String> randomRowDataAsMap = DB_Utility.getRowDataAsMap(random);

        System.out.println("rowDataAsMap = " + randomRowDataAsMap);

        System.out.println("random id = " + randomRowDataAsMap.get("SPARTAN_ID"));


    }

    @AfterAll
    public static void teardown(){

        RestAssured.reset();

        DB_Utility.destroy();

    }


}
