package review.day02_Parameters_Query_Path;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SpartanJavaToJsonTest {

    @DisplayName("Test get all spartans endpoint")
    @Test
    public void getAllSpartans(){

        //http://54.236.150.168:8000/api/spartans

        given().
                baseUri("http://54.236.150.168:8000").//this is uri
                basePath("/api").//this is path
        when().
                get("/spartans").//this is endpoint
        then().
                statusCode(equalTo(200));

    }


    @DisplayName("Test all get spartans endpoint using baseURI, basePath")
    @Test
    public void getAllSpartans2(){

        // send the same request specifying the accept header is application/json
        // use baseuri basepath , check status code 200 , contentType header is json
        //http://54.236.150.168:8000/api/spartans

        given().
                baseUri("http://54.236.150.168:8000")
                .basePath("/api").
                //accept("application/json").
                accept(ContentType.JSON).
        when().
                get("/spartans").
               // prettyPeek().
        then().
                statusCode(equalTo(200)).
                contentType(ContentType.JSON);



    }


}
