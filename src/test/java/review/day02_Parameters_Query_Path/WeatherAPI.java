package review.day02_Parameters_Query_Path;

import org.junit.jupiter.api.BeforeAll;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class WeatherAPI {


    @BeforeAll
    public static void setup() {

        //https://api.weatherapi.com/v1/forecast.json?key=3d1b0a80057744debeb202455212801&q=Cleveland&days=1

        baseURI = "https://api.weatherapi.com/v1";
        basePath = "/forecast.json";

    }

    @DisplayName("testing weather api")
    @Test
    public void testWeatherAPI(){

        given().
                accept(ContentType.JSON).
                queryParam("key", "3d1b0a80057744debeb202455212801")
                .queryParam("q","Cleveland").
                queryParam("days", 1).
                log().all().
        when().
                get().
        then().
                log().all().
                contentType(ContentType.JSON).
                statusCode(equalTo(200));



    }

}