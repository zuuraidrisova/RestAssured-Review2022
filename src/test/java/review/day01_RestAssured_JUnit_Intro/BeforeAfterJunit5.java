package review.day01_RestAssured_JUnit_Intro;

import org.junit.jupiter.api.*;

public class BeforeAfterJunit5 {

    //beforeAll method run only once before entire test same as @BeforeClass
    //these are junit5 specific annotations
    @BeforeAll
    public static void setup1(){

        System.out.println("beforeAll runs once beforeAll tests");

    }

    @BeforeEach
    public void setup(){

        System.out.println("beforeEach runs beforeEach test method");
    }

    @Test
    public void test1(){

        System.out.println("test 1 is running");

    }

    @Test
    public void test2(){

        System.out.println("test 2 is running");

    }

    @Disabled//this is gonna ignore the test
    @Test
    public void test3(){

        System.out.println("test 3 is running");

    }

    @AfterEach
    public void teardown(){

        System.out.println("AfterEach runs after each tests");
    }

    @AfterAll
    public static void teardown1(){

        System.out.println("AfterAll runs once after all tests");
    }

}
