package review.day08_DB_API_COMBO_DDT_CSV;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import review.pojo.Spartan;
import review.utility.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpartanCRUD_Operation {

    //we want id that generated  from post request accessible for all tests,
    // it must be static and at class level

    //we can make our POJO at class level

    //Spartan crud operation happy path ==> crud is create read update and delete
    // all should pass so we say happy path

    //i want exactly this order => 1 Create, 2 Read, 3Update, 4 Delete

    //http://54.236.150.168:8000

    static int newID;

    @BeforeAll
    public static void setup(){

        baseURI = ConfigurationReader.getProperty("spartan1.base_url");
        basePath = "/api";

    }


    @Order(1)
    @DisplayName("testing post spartan request")
    @Test
    public void testCreateSpartan(){

        Faker faker = new Faker();

        String randomName = faker.name().firstName();
        String randomGender = faker.demographic().sex();
        long randomPhone = faker.number().numberBetween(1000000000l, 9999999999l);

        Spartan spartan = new Spartan(randomName, randomGender, randomPhone);

        Response response =

        given().
                contentType(ContentType.JSON).
                log().all().
                body(spartan).
        when().
                post("/spartans").
                prettyPeek();

        Assertions.assertEquals(response.statusCode(), 201);

      Assertions.assertEquals  ( response.jsonPath().getString("success"), "A Spartan is Born!");
       newID =   response.jsonPath().getInt("data.id");

        System.out.println("newID = " + newID);

    }


    @Order(2)
    @DisplayName("testing get spartan request")
    @Test
    public void testGetSpartan(){


        given().
                accept(ContentType.JSON).
                log().all().
                pathParam("id", newID).
        when() .
                get("/spartans/{id}").
        then().
                statusCode(is(200));

    }

    @Order(3)
    @DisplayName("testing put spartan request")
    @Test
    public void testUpdateSpartan(){

        Map<String, Object> updatedBody = new HashMap<>();

        updatedBody.put("name", "Katy");
        updatedBody.put("gender", "Female");
        updatedBody.put("phone", 813762971221l);

        given().
                contentType(ContentType.JSON).
                log().all().
                pathParam("id", newID).
                body(updatedBody).
        when().
                put("/spartans/{id}").
        then().
                log().all().
                statusCode(is(204));

    }


    @Order(4)
    @DisplayName("testing delete spartan request")
    @Test
    public void testDeleteSpartan(){

        given().
                pathParam("id", newID).
                log().all().
        when().
                delete("/spartans/{id}").
        then().
                log().all().
                statusCode(is(204));

    }


    @AfterAll
    public static  void teardown(){

        RestAssured.reset();
    }


}
