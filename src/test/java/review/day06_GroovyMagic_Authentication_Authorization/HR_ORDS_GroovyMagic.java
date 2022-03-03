package review.day06_GroovyMagic_Authentication_Authorization;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;

public class HR_ORDS_GroovyMagic {

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


    @DisplayName("Testing the  /employees endpoint")
    @Test
    public void testEmployees(){

        Response response =

                given().
                        accept(ContentType.JSON).
                when().
                        get("/employees");

        JsonPath jsonPath = response.jsonPath();

        //get all ids and save into list and print them out
        List<Integer> employeeIds = jsonPath.getList("items.employee_id");

        System.out.println("employeeIds = " + employeeIds);

        System.out.println("===========================================");

        //print first id
        System.out.println("first id = " + jsonPath.getInt("items[0].employee_id"));

        System.out.println("===========================================");

        //print last id
        System.out.println("last id = " + jsonPath.getInt("items[-1].employee_id"));

        System.out.println("===========================================");

        //print first five ids
        for (int i = 0; i < 5; i++){

           int id =  employeeIds.get(i);

            System.out.print(id+ " ");
        }

        System.out.println();

        System.out.println("===========================================");

        //Groovy magic starts below :

        System.out.println("first five ids = " + jsonPath.getList("items[0..4].employee_id"));

        System.out.println("===========================================");

        //get all last names from 10 to 15

        System.out.println("last names from 10 to 15  = "+ jsonPath.getList("items[10..15].last_name"));

        System.out.println("===========================================");


        //get the employee last_name with employee id of 105
        //find and find all where you specify the criteria to restrict the result
        //find method will return single value that fall into criteria compared to findAll will return a list
        //find { it.employee_id == 105 }
        //<it> means ==> each item in ur json array

        System.out.println("first name = " + jsonPath.getString("items[0].first_name"));

        System.out.println("===========================================");

        System.out.println("last_name of employee with id 105 "+

        jsonPath.getString("items.find{ it.employee_id == 105}.last_name"));

        System.out.println("===========================================");

        // using above example : find the salary of employee with email value LDEHAAN

        System.out.println("salary of employee with email LDEHAAN "+

        jsonPath.getString("items.find{ it.email == 'LDEHAAN'}.salary"));

        System.out.println("===========================================");

        //findAll will get all result that match the criteria and return a list
        //save all the first_name of employees with salary more that 15000

        List<String> richPeople = jsonPath.getList("items.findAll { it.salary > 15000}.first_name");

        System.out.println("richPeople = " + richPeople);

        System.out.println("===========================================");

        //find all the phone_number in department_id == 90
        List<Long> phoneNumbersOfDepartment90 = jsonPath.getList("items.findAll{ it.department_id == 90}.phone_number");

        System.out.println("phonesOfDepartment90 = " + phoneNumbersOfDepartment90);

        System.out.println("===========================================");

        //max and min methods

        // find max salary
        int maxSalary = jsonPath.getInt("items.max{it.salary}.salary");

        System.out.println("maxSalary = " + maxSalary);

        System.out.println("===========================================");

        // find first name of person who makes max salary
        String richestGuy  = jsonPath.getString("items.max{it.salary}.first_name");

        System.out.println("richestGuy = " + richestGuy);

        System.out.println("===========================================");


        // find min salary
        int minSalary = jsonPath.getInt("items.min{it.salary}.salary");

        System.out.println("minSalary = " + minSalary);

        System.out.println("===========================================");

        // find first name of person who makes max salary
        String poorestGuy  = jsonPath.getString("items.min{it.salary}.first_name");

        System.out.println("poorestGuy = " + poorestGuy);

    }

}
