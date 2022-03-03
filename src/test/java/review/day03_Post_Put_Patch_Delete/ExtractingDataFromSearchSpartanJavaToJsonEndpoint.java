package review.day03_Post_Put_Patch_Delete;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ExtractingDataFromSearchSpartanJavaToJsonEndpoint {

    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = "http://54.236.150.168:8000";
        RestAssured.basePath = "/api";
    }

    @DisplayName("extracting data from spartan search ednpoint")
    @Test
    public void testExtractingDataFromSpartanSearch(){

        Response response =

        given().
                accept(ContentType.JSON).
                queryParam("gender", "Female").
                queryParam("nameContains", "li").
        when().
                get("/spartans/search");


        JsonPath jsonPath = response.jsonPath();

        //working with first element

        String name = jsonPath.getString("content[0].name");

        System.out.println("name = " + name);

        int id = jsonPath.getInt("content[0].id");

        System.out.println("id = " + id);

        long phone = jsonPath.getLong("content[0].phone");

        System.out.println("phone = " + phone);

        String gender = jsonPath.getString("content[0].gender");

        System.out.println("gender = " + gender);

        int totalElement = jsonPath.getInt("totalElement");

        System.out.println("totalElement = " + totalElement);

        System.out.println("===========================================");

       // getting all info as list

        List<String> names = jsonPath.getList("content.name");

        System.out.println("names = " + names);

        List<Integer> ids = jsonPath.getList("content.id");

        System.out.println("ids = " + ids);

        List<String> genders = jsonPath.getList("content.gender");

        System.out.println("genders = " + genders);

        List<Long> phones = jsonPath.getList("content.phone");

        System.out.println("phones = " + phones);


    }

}
