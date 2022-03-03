package review.utility;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class LibraryAppUtil {

    //posting to library app
    // body is not json , it's x-www-urlencoded-form-data

    //https://library2.cybertekschool.com/rest/v1/login
    // baseURI  is https://library2.cybertekschool.com
    // basePath is /rest/v1
    // we are working on POST /login

    // Post body , type x-www-urlencoded-form-data
    //email : librarian123455@library
    //password : QoyNEHxI


    /**
     * A static utility method to get the token by providing username and password
     * as Post request to /Login endpoint and capture the token field from response json
     * @param username
     * @param password
     * @return the token as String from the response json
     */
    public static String loginAndGetToken(String username, String password){

        String token = "";

        Response response =

        given().
                contentType(ContentType.URLENC).
                formParam("email", username).
                formParam("password",password).
        when().
                post("/login");


        JsonPath jsonPath = response.jsonPath();

        token =  jsonPath.getString("token");

        return token;


    }


    public static String setUpRestAssureAndDB_forEnv(String env){

        baseURI = ConfigurationReader.getProperty(env + ".base_url");

        basePath = "/rest/v1";

        DB_Utility.createConnection(env);

        // We want to return this token out of the method so the next class can use it

        return LibraryAppUtil.loginAndGetToken(ConfigurationReader.getProperty(env + ".librarian_username")

                , ConfigurationReader.getProperty(env + ".librarian_password"));


    }

}

