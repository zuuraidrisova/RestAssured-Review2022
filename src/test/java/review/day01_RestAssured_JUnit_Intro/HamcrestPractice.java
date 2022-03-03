package review.day01_RestAssured_JUnit_Intro;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class HamcrestPractice {

//hamcrest library is an assertion library, the aim of which is to make test more human readable
    //using lots of human readable matchers like something(somethingElse)
    //most importantly restAssured use hamcrest matcher when we chain multiple restAssured methods
    //hamcrest library already comes with RestAssured dependency

    @Test
    public void test1(){

        int num1 = 2;
        int num5 = 3;

        //we need these two imports to make this work
        //import static org.hamcrest.MatcherAssert.assertThat;
        //import static org.hamcrest.Matchers.*;

        //hamcrest library use the assertThat method for all assertions
        //hamcrest use built in matchers to do assertions
        //ex: is(some value),
        // equalTo(some value),
        // is(equalTo(some value)),


        // not(value) - negates the value
        // is( not (some value));
        // not(equalTo(some value));

        assertThat(num1+num5,is(5));

        assertThat(num1+num5, equalTo(5));

        assertThat(num1+num5, is(equalTo(5)));


        //negating the result
        assertThat(num1+num5, not(3));

        assertThat(num1+num5, is(not(4)));

        assertThat(num1+num5, not(equalTo(9)));


        //save ur first name and last name into 2 variables
        //test the concatenation result using hamcrest matcher

        String firstName = "Zuura";
        String lastName = "Idrisova";

        assertThat(firstName.concat(" ").concat(lastName), is("Zuura Idrisova"));

        assertThat(firstName.concat(" ").concat(lastName), equalTo("Zuura Idrisova"));

        assertThat(firstName.concat(" ").concat(lastName), is(equalTo("Zuura Idrisova")));

        assertThat(firstName.concat(lastName), not("Zuura Idrisova"));

        assertThat(firstName.concat(lastName), is(not("Zuura Idrisova")));

        assertThat(firstName.concat(lastName), not(equalTo("Zuura Idrisova")));


        //how to check in caseInsensitive manner
        assertThat(firstName, is(equalToIgnoringCase("zuura")));

        //how to ignore all whitespaces
        assertThat(firstName, is(equalToCompressingWhiteSpace("Zuura ")));


        //assert using startsWith, endsWith, containsString methods

        assertThat(firstName, containsString("uu"));

        assertThat(firstName, startsWith("Z"));

        assertThat(firstName, endsWith("a"));

        System.out.println("Success");

    }


    @DisplayName("support for collections")
    @Test
    public void test2(){

        List<Integer> list = new ArrayList<>(Arrays.asList(2,3,4,5,67,8,9,52));

        assertThat(list.size(), is(8));

        assertThat(list, hasSize(8));

        assertThat(list, hasItem(52));

        assertThat(list, hasItems(2,3));

        assertThat(list, containsInAnyOrder(2,3,4,5,67,8,9,52));

        assertThat(list, containsInRelativeOrder(5,67,8,9,52));

        assertThat(list, contains(2,3,4,5,67,8,9,52));//this works as equals method

        System.out.println("Success");

    }

}
