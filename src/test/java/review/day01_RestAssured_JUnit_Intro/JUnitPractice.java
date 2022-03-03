package review.day01_RestAssured_JUnit_Intro;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JUnitPractice {

    @Test
    public void calculate(){

        System.out.println("hello world");
        System.out.println(19+82);

        assertEquals(9, 3+6);

        assertTrue(true);

        assertEquals(3,3, "your assertion fails due to mis calculation");


    }

    @DisplayName("this is junit giving display name")
    @Test
    public void nameTest(){

        //write a simple assertion
        //concatenate ur first name and last name
        //and make assertion its equal to ur full name

        String firstName = "Zuura";
        String lastName = "Idrisova";

        assertEquals(firstName+" "+lastName, "Zuura Idrisova");

        assertEquals(firstName.concat(" ").concat(lastName), "Zuura Idrisova");

    }



}
