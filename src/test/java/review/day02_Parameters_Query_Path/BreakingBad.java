package review.day02_Parameters_Query_Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BreakingBad {

    @DisplayName("test get Breaking bad all characters")
    @Test
    public void testBreakingBad(){

        //https://www.breakingbadapi.com/api/characters

        given().

        when().
                get("https://www.breakingbadapi.com/api/characters").
                prettyPeek().
        then().
                statusCode(equalTo(200))
                .header("Content-Type", is("application/json; charset=utf-8"));


    }



}
