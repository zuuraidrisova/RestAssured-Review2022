package review.day06_GroovyMagic_Authentication_Authorization;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import review.pojo.Category;
import review.utility.LibraryAppUtil;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import static io.restassured.RestAssured.given;

public class LibraryApp_Category_POJO_AsLibrarian {

    private static String libraryToken;

    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = "https://library2.cybertekschool.com";
        RestAssured.basePath = "/rest/v1";

        libraryToken = LibraryAppUtil.loginAndGetToken("librarian123455@library", "QoyNEHxI");

        // libraryApp should have authorization in header section
        // as token "x-library-token"  and longToken
        // in order to be able to send any  get requests

    }

    @DisplayName("test /get_book_categories endpoint and save as POJO")
    @Test
    public void testGetAllUsersEndpointSaveAsPOJO(){

        Response response =

                given().
                        log().all().
                        header("x-library-token", libraryToken).
                when().
                        get("/get_book_categories");


        List<Category> listOfCategories = response.jsonPath().getList("");

        System.out.println("listOfCategories.size() = " + listOfCategories.size());

        System.out.println("listOfCategories = " + listOfCategories);

        List<Integer> ids = response.jsonPath().getList("id");

        System.out.println("ids = " + ids);

        List<String> names = response.jsonPath().getList("name");

        System.out.println("names = " + names);

    }




}
