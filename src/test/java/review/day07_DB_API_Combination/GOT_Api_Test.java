package review.day07_DB_API_Combination;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GOT_Api_Test {

    //https://api.got.show/api/book/characters

    @BeforeAll
    public static void setup(){

        baseURI = "https://api.got.show";
        basePath = "/api/book";
    }

    @DisplayName("test GOT API, send get request to /characters endpoint")
    @Test
    public void testGOT_API(){

        given().
                accept(ContentType.JSON).
                log().all().
        when().
                get("/characters").
        then().
               log().all().
                statusCode(200) ;

    }


     /*
    Make a request to GOT all characters endpoint :
GET https://api.got.show/api/book/characters
then store all the character name whose house value is House Stark
Hint :
The response is top level json array so you will not need to provide json path
The method will look like this getList(" findAll { condition here }.theFieldNameHere")
Assert the list size is 76
     */
     @DisplayName("send get request to /characters endpoint, and save names to list of String whose house value is House Stark")
     @Test
     public void testGOT_API_sendRequestSaveResultToList(){

         Response response =

         given().
                 accept(ContentType.JSON).
                 queryParam("house", "House Stark").
                 log().all().
         when().
                 get("/characters");

         JsonPath jsonPath = response.jsonPath();

        List<String> namesOfHouseStarkCharacters =  jsonPath.getList("findAll{ it.house == 'House Stark' }.name");

         System.out.println("namesOfHouseStarkCharacters.size() = " + namesOfHouseStarkCharacters.size());

         System.out.println(namesOfHouseStarkCharacters);


         assertThat(namesOfHouseStarkCharacters, hasSize(76));

         assertThat(namesOfHouseStarkCharacters, hasItem("Eddard Stark"));

      //   assertThat(namesOfHouseStarkCharacters, hasItems("Eddard Stark, Edderion Stark, Edrick Stark, Edwyle Stark, Edwyn Stark"));


     }


}

