package review.day05_POJO_AuthenticationAuthorization;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import review.pojo.Location;

import java.util.List;


public class HR_ORDS_LocationsTestWithPOJO {

            /*
          Here is the get request we need to make
              http://54.236.150.168:1000/ords/hr/regions/1
              baseURI = http://54.236.150.168
              port = 1000
              basePath = ords/hr
              request :  GET /locations/{location_id}
         */


      @BeforeAll
    public static void setup(){

          RestAssured.baseURI = "http://54.236.150.168";
          RestAssured.port = 1000;
          RestAssured.basePath = "ords/hr";

      }


    @DisplayName("test get request /locations/{location_id} and save as pojo")
    @Test
    public void testLocationSaveAsPOJO(){

          Response response =

                  given().
                          accept(ContentType.JSON).
                          pathParam("location_id", 2000).
                  when().
                          get("/locations/{location_id}");


         Location location =  response.as(Location.class);

         System.out.println("location = " + location);

    }

    // send a get request to /locations endpoint to get all regions
    // save the regions json array into pojo List
    // you already have taken care of unknown properties so no extra action needed
    // just call the method of jsonPath object to get the list you want

    @DisplayName("test get /locations endpoint and save as List<Location>")
    @Test
    public void testGetRequestAndSaveAsLisTOfLocations(){

        Response response = when().get("/locations");

        JsonPath jsonPath = response.jsonPath();

        List<Location> listOfLocations = jsonPath.getList("items");

        System.out.println("listOfLocations = " + listOfLocations);

        System.out.println("listOfLocations.size() = " + listOfLocations.size());


    }

    @DisplayName("test get /locations endpoint and save as List<Location> and get single items")
    @Test
    public void testLocationGetSingleItemsFromListOfLocation(){

          Response response = when().get("/locations");

          JsonPath jsonPath = response.jsonPath();

          String secondCity = jsonPath.getString("items[2].city");

        System.out.println("secondCity = " + secondCity);

        int secondLocation = jsonPath.getInt("items[2].location_id");

        System.out.println("secondLocation = " + secondLocation);

        String secondStreet = jsonPath.getString("items[2].street_address");

        System.out.println("secondStreet = " + secondStreet);

        List<Integer> listLocationIds = jsonPath.getList("items.location_id");

        System.out.println("listLocationIds = " + listLocationIds);

        List<String> listCities = jsonPath.getList("items.city");

        System.out.println("listCities = " + listCities);

        List<Location> locations = jsonPath.getList("items", Location.class);

        System.out.println("locations = " + locations);

        //groovy magic with findAll with country_id of US and save it to list of Location

        List<Location> listOfLocationsWithUS = jsonPath.getList("items.findAll{ it.country_id == 'US'}", Location.class);

        listOfLocationsWithUS.forEach( eachLocation -> System.out.println("eachLocation = " + eachLocation));

      }



}
