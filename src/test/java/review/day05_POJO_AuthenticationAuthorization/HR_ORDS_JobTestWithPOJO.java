package review.day05_POJO_AuthenticationAuthorization;


import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import review.pojo.Job;

import java.util.List;
public class HR_ORDS_JobTestWithPOJO {

    /*
        Here is the get request we need to make
            http://54.236.150.168:1000/ords/hr/regions/1
            baseURI = http://54.236.150.168
            port = 1000
            basePath = ords/hr
            request :  GET  /jobs/{job_id}
       */


    @BeforeAll
    public static  void setup(){

        baseURI = "http://54.236.150.168";
        port = 1000;
        basePath = "ords/hr";

    }

    @DisplayName("test get request  for /jobs/{job_id} and save as pojo")
    @Test
    public void testJobSaveAsPOJO(){

        Response response =

                given().
                        accept(ContentType.JSON).
                        pathParam("job_id", "AC_ACCOUNT").
                when().
                        get("/jobs/{job_id}");

        Job job = response.as(Job.class);

        System.out.println("job = " + job);

    }


    // send a get request to /jobs endpoint to get all regions
    // save the regions json array into pojo List
    // you already have taken care of unknown properties so no extra action needed
    // just call the method of jsonPath object to get the list you want

    @DisplayName("test get /jobs endpoint and save as List<Job>")
    @Test
    public void testGetRequestAndSaveAsLisTOfJobs(){

        Response response = when().get("/jobs");

        JsonPath jsonPath = response.jsonPath();

        List<Job> listOfJobs = jsonPath.getList("items", Job.class);

        System.out.println("listOfJobs = " + listOfJobs);

        System.out.println("listOfJobs.size() = " + listOfJobs.size());

    }


    @DisplayName("test get /jobs endpoint and save as List<Job> and get single items")
    @Test
    public void testLocationGetSingleItemsFromListOfJob(){

        Response response = when().get("/jobs");

        JsonPath jsonPath =response.jsonPath();

        String firstJobTitle = jsonPath.getString("items[0].job_title");

        System.out.println("firstJobTitle = " + firstJobTitle);

        String firstJobId = jsonPath.getString("items[0].job_id");

        System.out.println("firstJobId = " + firstJobId);

        String lastJobTitle = jsonPath.getString("items[-1].job_title");

        System.out.println("lastJobTitle = " + lastJobTitle);

        String lastJobId = jsonPath.getString("items[-1].job_id");

        System.out.println("lastJobId = " + lastJobId);

        List<String> jobTitles = jsonPath.getList("items.job_title");

        System.out.println("jobTitles = " + jobTitles);

        List<String> jobIds = jsonPath.getList("items.job_id");

        System.out.println("jobIds = " + jobIds);

        List<Job> jobList = jsonPath.getList("items", Job.class);

        System.out.println("jobList = " + jobList);


    }


}
