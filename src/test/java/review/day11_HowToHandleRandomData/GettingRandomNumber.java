package review.day11_HowToHandleRandomData;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GettingRandomNumber {

    @Test
    public void testRandom9(){

        //Random class comes from Java.util package and can provide some random numbers
        // in different data type
        // first we need to create an object of the class
        Random random = new Random();

        int randomNumber = random.nextInt(10);//it will give u between 0 to 10

        List<String> names = new ArrayList<>();

        names.addAll(Arrays.asList("Anna", "Vincent", "Zuleyha","Emrah", "Natalya",
                "Zuura","Namik", "Hilal","Nurgul", "Isa"));

        System.out.println("names random = " + names.get(randomNumber));


    }

}
