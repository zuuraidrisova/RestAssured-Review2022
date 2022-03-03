package review.day12_SchemaValidation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import review.utility.ConfigurationReader;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SpartanXMLResponse {


        /*
    Practice XML Response using GET /Spartans
Create a class called SpartanXML_Test
Add @BeforeAll method to set up your baseURI and basePath
Create a Test and send GET /Spartans by specifying accept header as xml.
Verify you get xml as response content type.
Verify the first spartan name, id , gender .
     */


    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = ConfigurationReader.getProperty("spartan1.base_url");
        RestAssured.basePath = "/api";

    }


    @DisplayName("test get request to /spartans endpoint and validate xml response")
    @Test
    public void testSpartanXMLResponse(){

        given().
                accept(ContentType.XML).
                log().all().
        when().
                get("/spartans").
        then().
                log().all().
                statusCode(is(200)).
                contentType(ContentType.XML).
                body("List.item[0].id", is("920")).
                body("List.item[0].name", equalTo("Nisha")).
                body("List.item[0].gender", equalTo("Female")).
                body("List.item[0].phone", equalTo("6810316318"));

    }


    @DisplayName("test get request to /spartans endpoint and validate xml path")
    @Test
    public void testSpartanXMLPath(){

        Response response =

        given().
                accept(ContentType.XML).
                log().all().
        when().
                get("/spartans");

        XmlPath xmlPath = response.xmlPath();

        //get and print first id
        int firstId = xmlPath.getInt("List.item[0].id");

        System.out.println("firstId = " + firstId);

        //get and print first name
        String firstName = xmlPath.getString("List.item[0].name");

        System.out.println("firstName = " + firstName);

        //get and print first gender
        String firstGender = xmlPath.getString("List.item[0].gender");

        System.out.println("firstGender = " + firstGender);

        //get and print first phone
        long firstPhone = xmlPath.getLong("List.item[0].phone");

        System.out.println("firstPhone = " + firstPhone);

        //get and store all ids
        List<Integer> listOfIds = xmlPath.getList("List.item.id", Integer.class);

        System.out.println("listOfIds = " + listOfIds);

        System.out.println("listOfIds.size() = " + listOfIds.size());

        //get and store all names
        List<String> listNames = xmlPath.getList("List.item.name");

        System.out.println("listNames = " + listNames);

        System.out.println("listNames.size() = " + listNames.size());

        //store only unique names

        Set<String> uniqueNames = new LinkedHashSet<>(listNames);

        System.out.println("uniqueNames = " + uniqueNames);

        System.out.println("uniqueNames.size() = " + uniqueNames.size());

        Assertions.assertTrue(listNames != uniqueNames);

        //get and store all genders
        List<String> listGenders = xmlPath.getList("List.item.gender");

        System.out.println("listGenders = " + listGenders);

        System.out.println("listGenders.size() = " + listGenders.size());

        //assertions

        assertThat(listOfIds, hasSize(2012));

        assertThat(listNames, hasSize(2012));

        assertThat(listGenders, hasSize(2012));


        assertThat(listOfIds, hasItems(926, 927, 928, 929, 930));

        assertThat(listNames, hasItems("Nisha", "Annie", "Rosie", "New Body"));

        assertThat(listGenders, hasItems("Female", "Male"));


        // Get a List of Long from the phone numbers
        // first check the size is 231
        // check it has few phone numbers you specified

        List<Long> listPhones = xmlPath.getList("List.item.phone", Long.class);

        System.out.println("listPhones.size() = " + listPhones.size());

        System.out.println("listPhones = " + listPhones);

        assertThat(listPhones, hasSize(2012));

        assertThat(listPhones, hasItems(6810316318l, 1231231231l, 1231231231l , 5555555555l));

        // optionally --
        // check every item is greaterThan 1000000000

        assertThat(listPhones, everyItem(greaterThan(100000000l)));




    }


    @AfterAll
    public static void teardown(){

        RestAssured.reset();

    }

}
