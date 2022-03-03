package review.day07_DB_API_Combination;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;

public class NewsApi_Test {


    @BeforeAll
    public static void setUp(){

        RestAssured.baseURI = "https://newsapi.org";
        RestAssured.basePath = "/v2";

    }

    // query parameter always goes with  ? and it is used to filter the result
    //path parameter always followed by : and it is used to get a single source among all response


    @DisplayName("News api send request to /top-headlines endpoint and get all country with us")
    @Test
    public void testNews(){

        String token = "47995edfa3ee4079b8b677d9619aff84";

        given().
                log().all().
                header("Authorization", "Bearer "+token ).
                queryParam("country", "US").
        when().
                get("/top-headlines").
        then().
                statusCode(200);


    }



    @DisplayName("send request to /top-headlines and  get all author if the source is not null")
    @Test
    public void testNewsAPI(){

        String token = "47995edfa3ee4079b8b677d9619aff84";

        Response response =


        given().
                log().all().
                header("Authorization", "Bearer "+token ).
                queryParam("country", "US").
        when().
                get("/top-headlines");


        JsonPath jsonPath = response.jsonPath();

        List<String> authors = jsonPath.getList("articles.author");

        System.out.println("authors = " + authors);

        System.out.println("authors.size() = " + authors.size());

        //filter out the result by checking source's id fields not null

        List<String> authorsFiltered = jsonPath.getList("articles.findAll{it.source.id != null}.author");

        System.out.println("authorsFiltered = " + authorsFiltered);

        System.out.println("authorsFiltered.size() = " + authorsFiltered.size());


    }

}
