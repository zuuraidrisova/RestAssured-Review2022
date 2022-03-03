package review.day05_POJO_AuthenticationAuthorization;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import review.pojo.Region;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class HR_ORDS_RegionsTestWithPOJO {

    /*
          Here is the get request we need to make
              http://54.236.150.168:1000/ords/hr/regions/1
              baseURI = http://54.236.150.168
              port = 1000
              basePath = ords/hr
              request :  GET /regions/{region_id}
         */

    @BeforeAll
    public static void setup(){

        baseURI = "http://54.236.150.168";
        port = 1000;
        basePath = "ords/hr";

    }

    @DisplayName("test get request /regions/{region_id} and save as pojo")
    @Test
    public void testRegionSaveAsPOJO(){


        Response response =

                given().
                        accept(ContentType.JSON).
                        pathParam("id", 1).
                when().
                        get("/regions/{id}");


        Region region = response.as(Region.class);

        System.out.println("region = " + region);


    }



    // send a get request to /regions endpoint to get all regions
    // save the regions json array into pojo List
    // you already have taken care of unknown properties so no extra action needed
    // just call the method of jsonPath object to get the list you want

    @DisplayName("test get /regions endpoint and save as List<Region>")
    @Test
    public void testGetRequestAndSaveAsLisTOfRegions(){

        Response response = when().get("/regions");

        JsonPath jsonPath = response.jsonPath();

        List<Region> regionList = jsonPath.getList("items", Region.class);

        System.out.println("regionList = " + regionList);

        System.out.println("regionList.size() = " + regionList.size());



    }


    @DisplayName("test get /regions endpoint and save as List<Region> and get single items")
    @Test
    public void testRegionGetSingleItemsFromListOfRegions(){

        Response response = when().get("/regions");

        JsonPath jsonPath =  response.jsonPath();

        int firstID =  jsonPath.getInt("items[0].region_id");

        System.out.println("firstID = " + firstID);

        String firstName = jsonPath.getString("items[0].region_name");

        System.out.println("firstName = " + firstName);

        int lastID =  jsonPath.getInt("items[-1].region_id");

        System.out.println("lastID = " + lastID);

        String lastName = jsonPath.getString("items[-1].region_name");

        System.out.println("lastName = " + lastName);

        // get the list of region name from the response and save it to List<String>
        List<String> regionNames = jsonPath.getList("items.region_name");

        System.out.println("regionNames = " + regionNames);

        System.out.println("regionNames.size() = " + regionNames.size());

        // get the list of region id from the response and save it to List<Integer>
        List<Integer> regionIds = jsonPath.getList("items.region_id");

        System.out.println("regionIds = " + regionIds);

        System.out.println("regionIds.size() = " + regionIds.size());

        // get a List<Region> from the response json
        List<Region> regions = jsonPath.getList("items", Region.class);

        System.out.println("regions = " + regions);


    }


}
