package review.day05_POJO_AuthenticationAuthorization;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import review.pojo.Employee;

import java.util.List;

public class HR_ORDS_EmployeeTestWithPOJO {

     /*
        Here is the get request we need to make
            http://54.236.150.168:1000/ords/hr/regions/1
            baseURI = http://54.236.150.168
            port = 1000
            basePath = ords/hr
            request :  GET  /employees/{employee_id}
       */


    @BeforeAll
    public static void setup(){

        baseURI = "http://54.236.150.168";
        port = 1000;
        basePath = "ords/hr";


    }


    //  get single resource and save as single POJO ,
    @DisplayName("testing the /employees/{employee_id} endpoints")
    @Test
    public void testEmployees() {

        Response response =

                given().
                        log().all().
                        contentType(ContentType.JSON).
                        pathParam("employee_id", 100).
                when().
                        get("/employees/{employee_id}");

        Employee employees = response.as(Employee.class);
        //saving a single response as single pojo

        System.out.println("employees = " + employees);


    }

    // send a request to /employees endpoint to get all countries
    // save the employees  json array into pojo List
    // you already have taken care of unknown properties so no extra action needed
    // just call the method of jsonPath object to get the list you want

    //get all resources save it it as list of pojo ,
    @DisplayName("testing the /employees  endpoint")
    @Test
    public void testEmployeesJsonToPojoList(){

        Response response =

                given().
                        log().all().
                        contentType(ContentType.JSON).
                when().
                        get("/employees");


        JsonPath jsonPath = response.jsonPath();

        List<Employee> employeesList = jsonPath.getList("items", Employee.class);

        System.out.println("employeesList = " + employeesList);


        for(Employee employees : employeesList){

            System.out.println("each employee = " + employees);
        }


    }



    @Test
    public void testEmployeesGetSingleItem(){

        Response response = get("/employees");

        JsonPath jsonPath = response.jsonPath();

        // Get first employee_id from the response using jsonPath
        System.out.println("first employee_id from response json = " + jsonPath.getInt("items[0].employee_id"));

        // Get first first_name from the response using jsonPath
        System.out.println("first first_name from response json  = " + jsonPath.getString("items[0].first_name"));

        // Get first last_name from the response using jsonPath
        System.out.println("first last_name from response json  = " + jsonPath.getString("items[0].last_name"));

        // Get first email from the response using jsonPath
        System.out.println("first email from response json  = " + jsonPath.getString("items[0].email"));

        // Get first phone_number from the response using jsonPath
        System.out.println("first phone_number from response json  = " + jsonPath.getString("items[0].phone_number"));

        // Get first hire_date from the response using jsonPath
        System.out.println("first hire_date from response json  = " + jsonPath.getString("items[0].hire_date"));

        // Get first job_id from the response using jsonPath
        System.out.println("first job_id from response json  = " + jsonPath.getString("items[0].job_id"));

        // Get first salary from the response using jsonPath
        System.out.println("first salary from response json  = " + jsonPath.getInt("items[0].salary"));

        System.out.println("=======================================================");

        // Get last employee_id from the response using jsonPath
        System.out.println("last employee_id from response json  = " + jsonPath.getInt("items[-1].employee_id"));

        // Get last first_name from the response using jsonPath
        System.out.println("last first_name from response json  = " + jsonPath.getString("items[-1].first_name"));

        // Get last last_name from the response using jsonPath
        System.out.println("last last_name from response json  = " + jsonPath.getString("items[-1].last_name"));

        // Get last email from the response using jsonPath
        System.out.println("last email from response json  = " + jsonPath.getString("items[-1].email"));

        // Get last phone_number from the response using jsonPath
        System.out.println("last phone_number from response json  = " + jsonPath.getString("items[-1].phone_number"));

        // Get last hire_date from the response using jsonPath
        System.out.println("last hire_date from response json  = " + jsonPath.getString("items[-1].hire_date"));

        // Get last job_id from the response using jsonPath
        System.out.println("last job_id from response json  = " + jsonPath.getString("items[-1].job_id"));

        // Get last salary from the response using jsonPath
        System.out.println("last salary from response json  = " + jsonPath.getInt("items[-1].salary"));


        // get the list of first_name from the response and save it to List<String>
        List<String> listOfFirstNames = jsonPath.getList("items.first_name");

        for(String each : listOfFirstNames){

            System.out.println(each);
        }

        // get a List<Employees> from the response json
        List<Employee> employeesList = jsonPath.getList("items", Employee.class);

        for(Employee employees : employeesList){

            System.out.println(employees);

        }

    }



}
