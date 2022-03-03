package review.day09_DDT_withCSV;



import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import review.utility.ConfigurationReader;


public class SpartanRoleBasedAccessControlNegativeTest_ReUse {

    @BeforeAll
    public static void setup(){

        baseURI = ConfigurationReader.getProperty("spartan1.base_url");
        basePath = "/api";

    }

    @DisplayName("User should not be able to delete data")
    @Test
    public void testUserCannotDeleteData(){

        //building reusable request specification

        RequestSpecification requestSpecBuilder = given().
                auth().basic("admin", "admin").
                log().all().
                pathParam("id", 3540);

        // Extracting ResponseSpecification for all assertions so we can reuse
        // We will be using a class called ResponseSpecBuilder

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();

        // Getting the reusable ResponseSpecification object using the builder methods chaining

        ResponseSpecification responseSpecification = responseSpecBuilder.expectStatusCode(204).build();

        given().
                spec(requestSpecBuilder).
        when().
                delete("/spartans/{id}").
        then().
               spec(responseSpecification);


    }



    @AfterAll
    public static void teardown(){

        RestAssured.reset();
    }


}
