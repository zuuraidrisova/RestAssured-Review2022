package review.day09_DDT_withCSV;

import static io.restassured.RestAssured.*;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import review.pojo.Spartan;
import review.utility.ConfigurationReader;
import static org.hamcrest.Matchers.*;

public class SpartanPostRequestWithRequestSpecResponseSpec {

       /*

 Practice the requestSpec and responseSpec with POST /Spartans endpoint
//   extract out the request specification for
            logging (all of them)
            contentType (json)
            body
//  extract out the responseSpec
            statusCode (201)
            Date (not null like previous class)
            body
                 "success": "A Spartan is Born!",
                 id is not null
                 name is the name we used to create the Spartan object
                 gender is the gender we used to create the Spartan object
                 phone is the phone we used to create the Spartan object
     */



    static RequestSpecification requestSpecification;
    static ResponseSpecification responseSpecification;

       @BeforeAll
    public static void setup(){

           baseURI = ConfigurationReader.getProperty("spartan1.base_url");
           basePath = "/api";

           //below lines are all to given() part
           Faker faker = new Faker();

           String randomName = faker.name().firstName();
           String randomGender = faker.demographic().sex();

           Spartan spartan = new Spartan(randomName, randomGender, 38179238619l);

           requestSpecification = given().
                   log().all().
                   accept(ContentType.JSON).
                   contentType(ContentType.JSON).
                   body(spartan);



           //below lines all belong to response part , then() part
           ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();

           responseSpecification = responseSpecBuilder.
                   log(LogDetail.ALL).
                   expectStatusCode(is(201)).
                   expectContentType(ContentType.JSON).
                   expectBody("success", equalTo("A Spartan is Born!")).
                   expectBody("data.id", is(notNullValue())).
                   expectBody("data.name", equalTo(randomName)).
                   expectBody("data.phone", equalTo(38179238619l)).
                   expectBody("data.gender", equalTo(randomGender)).build();

    }


    @DisplayName("Extracting the requestSpec and responseSpec practice")
    @Test
    public void test(){

           given().
                   spec(requestSpecification).
           when().
                   post("/spartans").
           then().
                   spec(responseSpecification);

    }



    @AfterAll
    public static void teardown(){

           RestAssured.reset();
    }


}
