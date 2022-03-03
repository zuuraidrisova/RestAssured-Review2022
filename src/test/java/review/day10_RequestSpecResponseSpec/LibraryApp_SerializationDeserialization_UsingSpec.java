package review.day10_RequestSpecResponseSpec;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import review.pojo.Category;
import review.pojo.User;
import review.utility.ConfigurationReader;
import review.utility.LibraryAppUtil;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LibraryApp_SerializationDeserialization_UsingSpec {

        /*
    Practice the De-Serialization using the same test
get the Map<String,Integer> object out of the response of GET /dashboard_stats
get the List<Category> object from the response of GET /get_book_categories
get the List<User> object from the response of GET /get_all_users
hint : you will need to create 2 POJO class called Category , User;
     */

   @BeforeAll
    public static void setup(){

       RestAssured.baseURI = ConfigurationReader.getProperty("library2.base_url");
       RestAssured.basePath = "/rest/v1";

       String username = ConfigurationReader.getProperty("library2.librarian_username");
       String password = ConfigurationReader.getProperty("library2.librarian_password");

       String token = LibraryAppUtil.loginAndGetToken(username, password);

       RestAssured.requestSpecification = given().accept(ContentType.JSON).
               header("x-library-token", token).
               log().all();

       responseSpecification = expect().statusCode(is(200)).
               contentType(ContentType.JSON).logDetail(LogDetail.ALL);


   }



    @DisplayName("GET /dashboard_stats with requestSpec and responseSpec")
    @Test
    public void testGet_dashboard_status_WithRequestResponseSpec(){


      //  get the Map<String,Integer> object out of the response of GET /dashboard_stats

        Response response = when().get("/dashboard_stats");

        Map<String, Integer> dashboardStats = response.jsonPath().getMap("");

        System.out.println("dashboardStats = " + dashboardStats);

        System.out.println("dashboardStats.size() = " + dashboardStats.size());
    }


    @DisplayName("GET /get_book_categories with requestSpec and responseSpec")
    @Test
    public void testGET_book_categories_WithRequestResponseSpec(){

//get the List<Category> object from the response of GET /get_book_categories

        Response response = when().get("/get_book_categories");

        List<Category> listOfCategory = response.jsonPath().getList("",Category.class);

        System.out.println("listOfCategory = " + listOfCategory);

        List<Integer> listOfIds = response.jsonPath().getList("id");

        System.out.println("listOfIds = " + listOfIds);

        List<String> listOfNames = response.jsonPath().getList("name");

        System.out.println("listOfNames = " + listOfNames);

        System.out.println("listOfCategory.size() = " + listOfCategory.size());

        System.out.println("listOfIds.size() = " + listOfIds.size());

        System.out.println("listOfNames.size() = " + listOfNames.size());


    }


    @DisplayName("GET /get_all_users with requestSpec and responseSpec")
    @Test
    public void testGet_all_users_WithRequestResponseSpec(){

        Response response =when().get("/get_all_users");

        List<User> listOfUsers = response.jsonPath().getList("",User.class);

        System.out.println("listOfUsers = " + listOfUsers);

        List<Integer> listOfIds = response.jsonPath().getList("id");

        System.out.println("listOfIds = " + listOfIds);

        List<String> listOfNames = response.jsonPath().getList("name");

        System.out.println("listOfNames = " + listOfNames);

        System.out.println("listOfUsers.size() = " + listOfUsers.size());

        System.out.println("listOfIds.size() = " + listOfIds.size());

        System.out.println("listOfNames.size() = " + listOfNames.size());

    }


    @AfterAll
   public static void teardown(){

       RestAssured.reset();

   }



}
