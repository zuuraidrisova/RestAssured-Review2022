package review.day08_DB_API_COMBO_DDT_CSV;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import review.utility.ConfigurationReader;
import review.utility.DB_Utility;
import io.restassured.RestAssured;


import java.util.List;

import static io.restassured.RestAssured.given;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Spartan_API_DB_Combo {

    /**
     * The dev just implemented the search endpoint
     * and want it to be tested to make sure it's actually
     * returning the correct result from the database
     *
     *    GET /spartans/search?gender=Female&nameContains=a
     *  we want to test the count of result is correct
     *  for numberOfElements field.
     *
     *  The Database query to get the count is :
     *  // all the females that have a in their name , case insensitive
     *   -- This is how we get the data with case insensitive manner
     *      SELECT * FROM SPARTANS
     *       WHERE LOWER(gender) = 'female'
     *       and LOWER(name) LIKE '%a%' ;
     *
     */

    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = ConfigurationReader.getProperty("spartan1.base_url");
        RestAssured.basePath = "/api";

        String spartanDBURL = ConfigurationReader.getProperty("sp.database.url");
        String spartanDB_Username = ConfigurationReader.getProperty("sp.database.username");
        String spartanDB_Password = ConfigurationReader.getProperty("sp.database.password");


        DB_Utility.createConnection(spartanDBURL,spartanDB_Username,spartanDB_Password);

    }

    @DisplayName("testing only spartan db for connection and query result")
    @Test
    public void testDB(){

        //run this query so we can use it for expected result

        DB_Utility.runQuery(" select * from spartans where gender= 'Female' and lower(name) like '%li%' ");

        DB_Utility.displayAllData();

        int expected = DB_Utility.getRowCount();

        System.out.println(expected);

    }

    @DisplayName("testing only spartan api connection, get /spartans/search endpoint")
    @Test
    public void testAPI(){

        // make a request to GET /spartans/search
        // using query parameter gender Female  nameContains a

        given().
                accept(ContentType.JSON).
                log().all().
                queryParam("gender", "Female").
                queryParam("nameContains", "li").
        when().
                get("/spartans/search").
        then().
                log().all().
                statusCode(is(200)).
                contentType(ContentType.JSON);

    }


    @DisplayName("Testing spartans/search endpoint and validate against DB")
    @Test
    public void testSpartanAPI_DB_Combo(){

        DB_Utility.runQuery(" select * from spartans where gender= 'Female' and lower(name) like '%li%' ");

        DB_Utility.displayAllData();

       int expectedResult =  DB_Utility.getRowCount();

       Response response =

        given().
                accept(ContentType.JSON).
                log().all().
                queryParam("gender", "Female").
                queryParam("nameContains", "li").
        when().
                get("/spartans/search").
               prettyPeek();

        JsonPath jsonPath = response.jsonPath();

       int actualResult =  jsonPath.getInt("totalElement");

        System.out.println("expectedResult = " + expectedResult);

        System.out.println("actualResult = " + actualResult);

        Assertions.assertEquals(actualResult, expectedResult);

    }

    @DisplayName("Testing spartans/search endpoint and validate against DB, verify all ids")
    @Test
    public void testSearchVerifyAllIDs(){

        //first api part
        Response  response =

                given().
                        queryParam("gender", "Female").
                        queryParam("nameContains", "li").
                when().
                        get("/spartans/search").
                        prettyPeek();


        // We want to store the id list as List<String> rather than List of Integer so we can compare easily
        // with the List<String> we got from DB Response , and no parsing needed
        // so we used the getList method that accept 2 parameters
        // the jsonpath to get the list and the Data type of the List you want ! -->> String.class

        List<String> idListFromResponse = response.jsonPath().getList("content.id",String.class );

        //here DB part starts

        String query = "SELECT * FROM SPARTANS     " +
                " WHERE LOWER(gender) = 'female'  " +
                " and LOWER(name) LIKE '%li%' ";

        DB_Utility.runQuery(query);

        List<String> idListFromDB = DB_Utility.getColumnDataAsList(1);

        System.out.println("idListFromDB.size() = " + idListFromDB.size());

        System.out.println("idListFromResponse.size() = " + idListFromResponse.size());

        System.out.println("idListFromDB = " + idListFromDB);

        System.out.println("idListFromResponse = " + idListFromResponse);

        assertThat(idListFromResponse.size(), equalTo(idListFromDB.size()));

        Assertions.assertEquals(idListFromDB, idListFromResponse);

    }


    @AfterAll
    public static void teardown(){

        DB_Utility.destroy();

        RestAssured.reset();

    }


}
