package review.utility;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import review.pojo.Spartan;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class SpartanUtil {


    // CREATE A METHOD THAT POST A RANDOM SPARTAN TO THE SERVER
    // AND RETURN THE ID OF THAT SPARTAN , SO YOU CAN ALWAYS USE A DATA THAT EXISTS

    //below method is using POJO as body
    public static int createOneSpartan(){

        Faker faker = new Faker();

        String randomName = faker.name().firstName();
        String randomGender = faker.demographic().sex();

        Spartan spartanJavaToJson = new Spartan(randomName, randomGender, 81369186231l );

        Response response =

                given().
                        baseUri("http://54.236.150.168").
                        port(8000).
                        basePath("/api").
                        contentType(ContentType.JSON).
                        body(spartanJavaToJson).
                when().
                        post("/spartans");


        JsonPath jsonPath = response.jsonPath();

        int id = jsonPath.getInt("data.id");

        return  id;

    }

    //below method using map as body
    public static int postRandomSpartanGetID(String name, String gender,  long phone){

        Map<String, Object> dataMap = new LinkedHashMap<>();

        dataMap.put("name", name);
        dataMap.put("gender", gender);
        dataMap.put("phone", phone);

        RestAssured.baseURI = "http://54.236.150.168";
        RestAssured.port = 8000;
        RestAssured.basePath = "/api";

        Response responseJson =

                given().
                        contentType(ContentType.JSON).
                        body(dataMap).
                when().
                        post("/spartans");

        JsonPath jsonPath = responseJson.jsonPath();

        return  jsonPath.getInt("data.id");


    }


    public static int createRandomSpartanGetID(){

        Faker faker = new Faker();

        String name = faker.name().firstName();
        String gender = faker.demographic().sex();
        long phone = faker.number().numberBetween(1000000000l, 9999999999l);

        //pojo
        Spartan spartanJavaToJson = new Spartan(name, gender, phone);

        Response response =

                given().
                        auth().basic("admin", "admin").
                        contentType(ContentType.JSON).
                        body(spartanJavaToJson).
                 when().
                        post("/spartans");

        return response.path("data.id");

    }


    public static Spartan createRandomSpartanObject() {

        Faker faker = new Faker();

        String name   = faker.name().firstName();

        String gender = faker.demographic().sex();

        // always getting long number outside range of int to avoid errors

        long phone = faker.number().numberBetween(5000000000l,9999999999L);
        Spartan randomSpartanObject = new Spartan(name,gender,phone);

        System.out.println("Created Random Spartan Object : " + randomSpartanObject);
        return randomSpartanObject ;

    }

}

