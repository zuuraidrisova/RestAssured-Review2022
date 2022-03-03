package review.day01_RestAssured_JUnit_Intro;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
//In order to use REST assured effectively it's recommended to statically
// import methods from the following classes:

import static io.restassured.RestAssured.*;

public class FirstRequest {

    // you may use your own IP
    // we are using spartan app that does not require password
    //http://54.236.150.168:8000/api/hello

    @BeforeAll
    public static void setUp(){

        System.out.println("we are starting rEstAssured API test...");

    }

    @Test
    public void testHelloEndpoint(){

        //RestAssured.get("URL HERE")
        // SINCE WE DID THE STATIC IMPORT
        // we can directly call the "get" method
        // after we send the request
        // we can save the result in to a type called Response
        // need this  import io.restassured.response.Response;

        Response response = get("http://54.236.150.168:8000/api/hello");

        //Response object stores all info about response we got like: status code, body, header

        //getting status code from response object
        int statusCode = response.statusCode();
        System.out.println("statusCode = " + statusCode);


        //this is another way of getting status code starting with HTTP/ 1.1
        String statusLine = response.statusLine();
        System.out.println("statusLine = " + statusLine);


        //getting header info of Date
        String dateHeader = response.header("Date");
        System.out.println("date = " + dateHeader);


        //getting content type
        String contentType = response.contentType();
        System.out.println("contentType = " + contentType);


        //getting content type
        String contentType2 = response.header("Content-Type");
        System.out.println("contentType2 = " + contentType2);


        //getting content length header
        String contentLength = response.getHeader("Content-Length");
        System.out.println("contentLength = " + contentLength);

        Assertions.assertEquals(statusCode, 200);

        Assertions.assertTrue(statusLine.contains("200"));

        Assertions.assertTrue(dateHeader.contains("Mon, 21 Feb"));

        Assertions.assertEquals(contentType, "text/plain;charset=UTF-8");

        Assertions.assertTrue(contentType2.contains("text/plain"));

        Assertions.assertTrue(contentLength.equals("17"));

//         post()
 //        put()
////       delete()
////       patch()
////       options()


    }

    @DisplayName("testing hello endpoint once again")
    @Test
    public void testHelloEndpoint1(){

       Response response = get("http://54.236.150.168:8000/api/hello");

        //testing the status code returns as expected
        Assertions.assertEquals(response.getStatusCode(), 200);

        //testing the content Type returns as expected
        Assertions.assertTrue(response.getContentType().equals("text/plain;charset=UTF-8"));

        //testing the getHeader() returns 17
        Assertions.assertEquals(response.getHeader("Content-Length"), "17");

    }

    @DisplayName("testing response body")
    @Test
    public void testResponseBody(){

       Response response =  get("http://54.236.150.168:8000/api/hello");

       response.body().equals("Hello from Sparta");

        //getting body as String using asString method of response object
        System.out.println("response.body asString = " + response.body().asString());

        //assert length is 17
        Assertions.assertEquals(response.body().asString().length(), 17);

        //assert the body is Hello from Sparta
        Assertions.assertEquals(response.body().asString(), "Hello from Sparta");


    }


    @DisplayName("printing response")
    @Test
    public void testPrintResponse(){

        Response response = get("http://54.236.150.168:8000/api/hello");

        //easy way to print the response body and return at the same time
        //return type is String, prints only body
        response.prettyPrint();

        System.out.println("================================");

        //prettyPeek() is another way of seeing the body quick
        //it prints the entire response
        //return type is Response, and it will return status code, header and body
        response.prettyPeek();

        System.out.println("=================================");

        //i want to see entire response and save status code into a variable in same statement

        int statusCode = response.prettyPeek().statusCode();

        Assertions.assertEquals(statusCode,200);

    }


}
