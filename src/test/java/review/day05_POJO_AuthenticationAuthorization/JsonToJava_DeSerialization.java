package review.day05_POJO_AuthenticationAuthorization;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import review.day04_POJO_PostRequestWithPOJO_WithDifferentBodyTypes.GetOneSpartanJavaToJsonMethod;
import review.pojo.Spartan;
import review.pojo.Spartan2;

public class JsonToJava_DeSerialization {

    //json to java object == de-serialization

    //store the result of get spartans/{id} into pojo object

    /*
    {
    "id": 253,
    "name": "Mariette",
    "gender": "Female",
    "phone": 8339519234
        }
     */

    //since our existing spartan object does not have id field,
    // so we cant save id field there


    @BeforeAll
    public static void setup(){

        baseURI = "http://54.236.150.168";
        port = 8000;
        basePath = "/api";

    }

    @DisplayName("test json to java using spartans/id endpoint ")
    @Test
    public void testSpartanJsonToJava(){

        int newID = GetOneSpartanJavaToJsonMethod.createOneSpartan();

        Response response =

                given().
                        log().all().
                        auth().basic("admin","admin").
                        pathParam("id", newID).
                when().
                        get("/spartans/{id}").
                        prettyPeek();


        //as() method from response accepts a class type to define what type of object
        // u r trying to  store the response as
        //Spartan2 class we created has all the fields that match the json fields from response
        //so it will map all the json fields to the java fields and return Spartan2 POJO object

        // in a simple word it turns below json into Java object

        /**
         * {
         *     "id": 261,
         *     "name": "Elma",
         *     "gender": "Male",
         *     "phone": 9999999998
         * }
         *
         * into  new Spartan2(261,"Elma","Male",9999999998L) Java Object
         * so we can work with the data using java object directly
         */

        Spartan2 spartan2 = response.as(Spartan2.class);

        //above line is almost as if u r doing below line
        // Spartan2 spartan2 = new Spartan2(261, "Elma", "Male", 72316497812l);

        System.out.println("spartan2 = " + spartan2);


    }


    @DisplayName("test get request search spartan endpoint and save 1st result as Spartan2 pojo")
    @Test
    public void gettingNestedJsonAsPojo(){

        Response response =

        given().
                queryParam("gender", "Female").
                queryParam("nameContains", "li").
        when().
                get("/spartans/search");


        int status = response.getStatusCode();

        System.out.println(status);

        JsonPath jsonPath = response.jsonPath();

        int firstID = jsonPath.getInt("content[0].id");

        System.out.println("firstID = " + firstID);

        String firstName = jsonPath.getString("content[0].name");

        System.out.println("firstName = " + firstName);

        int secondID = jsonPath.getInt("content[1].id");

        System.out.println("secondID = " + secondID);

        String secondName = jsonPath.getString("content[1].name");

        System.out.println("secondName = " + secondName);

        //lets save the entire third json object in the array into Spartan2 pojo

        Spartan2 spartan2 = jsonPath.getObject("content[2]", Spartan2.class);

        System.out.println("spartan2 = " + spartan2);

        System.out.println("spartan2.getId() = " + spartan2.getId());
        System.out.println("spartan2.getName() = " + spartan2.getName());
        System.out.println("spartan2.getGender() = " + spartan2.getGender());
        System.out.println("spartan2.getPhone() = " + spartan2.getPhone());

    }


    //how can i store the entire jsonArray into the List<Spartan2> ?

    @DisplayName("test search spartan endpoint and store List<Spartan2>")
    @Test
    public void testSpartanSearchEndpointSaveToListOfSpartans(){


        Response response =

                given().
                        queryParam("gender", "Female").
                        queryParam("nameContains", "li").
                when().
                        get("/spartans/search");


        JsonPath jsonPath = response.jsonPath();



        //store the entire json as list of Spartan2 y
        List<Spartan2> listOfSpartans = jsonPath.getList("content");

        System.out.println("listOfSpartans = " + listOfSpartans);

        System.out.println("listOfSpartans.size() = " + listOfSpartans.size());




        //store all ids as list of Integers
        List<Integer> listOfSpartanIDs = jsonPath.getList("content.id");

        System.out.println("listOfSpartanIDs = " + listOfSpartanIDs);

        System.out.println("listOfSpartanIDs.size() = " + listOfSpartanIDs.size());




        //store all names as list of String
        List<String >listOfSpartanNames = jsonPath.getList("content.name");

        System.out.println("listOfSpartanNames = " + listOfSpartanNames);

        System.out.println("listOfSpartanNames.size() = " + listOfSpartanNames.size());


        System.out.println();

        System.out.println("************************************************************");

        System.out.println();


        //or we can store the entire json as list of Spartan2 this way
        List<Spartan2> spartan2List = jsonPath.getList("content", Spartan2.class);

        System.out.println("spartan2List = " + spartan2List);

        System.out.println("spartan2List.size() = " + spartan2List.size());


        for (Spartan2 each : spartan2List){

            System.out.println("each = " + each);
        }



        spartan2List.forEach(each -> System.out.println("each = " + each));

    }





}
