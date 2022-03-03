package review.day11_HowToHandleRandomData;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import review.pojo.Spartan2;
import review.utility.ConfigurationReader;

import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Spartan_GettingDataFromOtherRequest {


/*
***If you do not have access to DB but have access to API
			Send the request to the endpoint that return your data and get
			*  the fields that you want using jsonPath.
 */

  @BeforeAll
    public static void setup(){

      RestAssured.baseURI = ConfigurationReader.getProperty("spartan1.base_url");
      RestAssured.basePath = "/api";

  }

    /**
     * We want to test GET /Spartans/{id}
     * and the ID is dynamic , and also different in different environments
     * we want to always work with available data
     * without dealing with 404 issue because data does not exists
     *
     * in order to do above we need to do below
     *
     *  Action Items
     *      *  1. Send a GET /spartans endpoint
     *      *  2. store the result as List of pojo
     *      *  3. initially just the first data and use it for GET /Spartans/{id} request
     *      *      and use the name , gender , phone for body validation
     *      *  4 , eventually randomize the way you get the ID from List of Pojo
     */


  @DisplayName("test Getting Data From Other Request And Use It In Another Request")
  @Test
  public void testGetDataFromOtherRequestAndUseItTowardsAnotherRequest(){

      Response response =

      given().
              accept(ContentType.JSON).
              log().all().
      when().
              get("/spartans");


      JsonPath jsonPath = response.jsonPath();

      //we need to use Spartan2 class since it stores all fields including id, name, gender, phone

      List<Spartan2> listOfSpartans = jsonPath.getList("", Spartan2.class);

      System.out.println("listOfSpartans = " + listOfSpartans);

      System.out.println("listOfSpartans.size() = " + listOfSpartans.size());

      // get the first spartan id so we can send below request :
      // we are calling list method get(0) to get first Spartan2 Object
      // then we called getter method getId() from Spartan2 to get the value

      String firstName = listOfSpartans.get(0).getName();
      String firstGender = listOfSpartans.get(0).getGender();
      long firstPhone = listOfSpartans.get(0).getPhone();

      //or we can get first id as  below:

      List<Integer> listOfIds = jsonPath.getList("id");

      System.out.println("listOfIds = " + listOfIds);

      System.out.println("listOfIds.size() = " + listOfIds.size());

      //now we can use below id for another get request
      int firstId = listOfIds.get(0);


//         *  Action Items
//    1. Send a GET /spartans endpoint
//     2. store the result as List of pojo
//     3. initially just the first data and use it for GET /Spartans/{id} request
//   and use the name , gender , phone for body validation
//    4 , eventually randomize the way you get the ID from List of Pojo
//
  given().
              accept(ContentType.JSON).
              log().all().
              pathParam("id", firstId).
      when().
              get("/spartans/{id}").
      then().
            statusCode(is(200)).
            contentType(ContentType.JSON).
            body("id", equalTo(firstId)).
            body("name", equalTo(firstName)).
            body("gender", equalTo(firstGender)).
            body("phone", equalTo(firstPhone));



  }



    //can i repeat certain test n number of times in Junit5
    //use @RepeatedTest instead of @Test
    @DisplayName("test getting random id and use it for another test")
    @RepeatedTest(3)
    public void gettingRandomIdAndNameForEachTest(){

      Response response =

              given().
                      accept(ContentType.JSON).
                      log().all().
              when().
                      get("/spartans");

      JsonPath jsonPath = response.jsonPath();

      List<Spartan2> listOfSpartans = jsonPath.getList("", Spartan2.class);

      System.out.println("listOfSpartans = " + listOfSpartans);


        //create random object
        Random random = new Random();

        //and use nextInt() method to get any id from size
        int randomIndex = random.nextInt(listOfSpartans.size());

        Spartan2 randomSpartanObject = listOfSpartans.get(randomIndex);

        System.out.println("spartan2 = " + randomSpartanObject);

        //now use randomIndex to get another get request

        given().
                accept(ContentType.JSON).
                log().all().
                pathParam("id", randomSpartanObject.getId()).
        when().
                get("/spartans/{id}").
        then().
                statusCode(is(200)).
                contentType(ContentType.JSON).
                body("id", equalTo(randomSpartanObject.getId())).
                body("name", equalTo(randomSpartanObject.getName())).
                body("gender", equalTo(randomSpartanObject.getGender()));
               // body("phone", equalTo(randomSpartanObject.getPhone()));


    }

  @AfterAll
    public static void teardown(){

      RestAssured.reset();

  }


}
