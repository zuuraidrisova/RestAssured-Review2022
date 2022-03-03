package review.day12_SchemaValidation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class OpenMovieDB_XmlPath {


    //http://www.omdbapi.com/?t=Boss Baby&r=xml
    //apikey is 26aa5b74
    //getting many movies and extracting title attribute to the list

    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = "http://www.omdbapi.com";

    }


    @DisplayName("getting many movies and extracting title attribute to the list")
    @Test
    public void testGettingAttributeAsList(){

        Response response =

        given().
                log().all().
                queryParam("r", "xml").
                queryParam("apikey", "26aa5b74").
                queryParam("s","Superman").
        when().
                get("");

        //response.statusCode(is(200));

        XmlPath xmlPath = response.xmlPath();

        List<String> allTitles = xmlPath.getList("root.result.@title");

        System.out.println("allTitles = " + allTitles);

        System.out.println("allTitles.size() = " + allTitles.size());

    }



    @AfterAll
    public static void teardown(){

        RestAssured.reset();
    }



}
