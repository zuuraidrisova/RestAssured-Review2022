package review.day05_POJO_AuthenticationAuthorization;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import review.pojo.Department;

import java.util.List;

public class HR_ORDS_DepartmentTestWithPOJO {


    /*
        Here is the get request we need to make
            http://54.236.150.168:1000/ords/hr/regions/1
            baseURI = http://54.236.150.168
            port = 1000
            basePath = ords/hr
            request :  GET  /departments/{department_id}
       */

    @BeforeAll
    public static void setup(){

        baseURI = "http://54.236.150.168";
        port = 1000;
        basePath = "ords/hr";


    }


    @DisplayName("test get request  for /departments/{department_id} and save as pojo")
    @Test
    public void testDepartmentSaveAsPOJO(){

        Response response =

                given().
                        accept(ContentType.JSON).
                        pathParam("department_id", 100).
                when().
                        get("/departments/{department_id}");


        Department department = response.as(Department.class);

        System.out.println("department = " + department);

    }




    // send a get request to /departments endpoint to get all regions
    // save the regions json array into pojo List
    // you already have taken care of unknown properties so no extra action needed
    // just call the method of jsonPath object to get the list you want

    @DisplayName("test get /departments endpoint and save as List<Department>")
    @Test
    public void testGetRequestAndSaveAsLisTOfDepartments(){

        Response response = when().get("/departments");

        JsonPath jsonPath = response.jsonPath();

        List<Department> listOfDepartments = jsonPath.getList("items", Department.class);

        System.out.println("listOfDepartments = " + listOfDepartments);

        for (Department each  : listOfDepartments){

            System.out.println(each);
        }



    }


    @DisplayName("test get /departments endpoint and save as List<Department> and get single items")
    @Test
    public void testLocationGetSingleItemsFromListOfDepartment(){

        Response response = when().get("/departments");

        JsonPath jsonPath = response.jsonPath();

        String secondDepartment_id = jsonPath.getString("items[1].department_id");

        System.out.println("secondDepartment_id = " + secondDepartment_id);

        String secondDepartment_Name = jsonPath.getString("items[1].department_name");

        System.out.println("secondDepartment_Name = " + secondDepartment_Name);

        String firstDepartment_id = jsonPath.getString("items[0].department_id");

        System.out.println("firstDepartment_id = " + firstDepartment_id);

        String firstDepartment_name = jsonPath.getString("items[0].department_name");

        System.out.println("firstDepartment_name = " + firstDepartment_name);

        List<String> departmentNames = jsonPath.getList("items.department_name");

        System.out.println("departmentNames = " + departmentNames);

        List<Integer> departmentIds = jsonPath.getList("items.department_id");

        System.out.println("departmentIds = " + departmentIds);

        List<Integer> managerIds = jsonPath.getList("items.manager_id");

        System.out.println("managerIds = " + managerIds);

        List<Department> departmentList = jsonPath.getList("items", Department.class);

        System.out.println("departmentList = " + departmentList);

    }

}
