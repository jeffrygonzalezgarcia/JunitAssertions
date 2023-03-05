package com.j2gsoft.junitassertions;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class DemoUtilsTest {

    DemoUtils du;

    @BeforeEach
    void setupBeforeEach(){
      du  = new DemoUtils();
    //   System.out.println("@BeforeEach executes before the execution of each test method");
    }


    @Test
    @DisplayName("Equals and Not Equals")
    void equalsAndNotEquals(){

       assertEquals(6,du.add(2,4),"2+4 must be 6");
       assertNotEquals(6,du.add(2,5),"2+5 must not be 6");
    }

    @Test
    @DisplayName("Null and not Null")
    void NullAndNotNull(){

        String str1 = null;
        String str2 = "This is a test";

        assertNull(du.checkNull(str1), "Object should be null");
        assertNotNull(du.checkNull(str2), "Object should not be null");
    }

    @Test
    @DisplayName("Same And Not Same")
    void sameAndNotSame(){
        String str = "J2gsoft";
        assertSame(du.getAcademy(),du.getAcademyDuplicate(),"Object should refer to the same");
        assertNotEquals(str,du.getAcademy(),"Object should not refer to the same");
    }

    @Test
    @DisplayName("True And False")
    void trueFalse(){
        int grade1 = 10;
        int grade2 = 5;
        assertTrue(du.isGrater(grade1,grade2),"This should return true");
        assertFalse(du.isGrater(grade2,grade1),"This should return false");

    }

    @Test
    @DisplayName("Array Equals")
    void arrayEquals(){
        String[] stringArray = {"A", "B", "C"};

        assertArrayEquals(stringArray,du.getFirstThreeLettersOfAlphabet(), "Array should be the same");
    }

    @Test
    @DisplayName("Iterable Equals")
    void IterableEquals(){
        List<String> theList = List.of("j2g", "soft", "academy");
        assertIterableEquals(theList,du.getAcademyInList(), "Expected list should be as actual list");
    }

    @Test
    @DisplayName("Line Match")
    void lineMatch(){
        List<String> theList = List.of("j2g", "soft", "academy");
        assertLinesMatch(theList,du.getAcademyInList(), "Lines should match");
    }




/*
    @AfterEach
    void tearDownAfterEach(){
        System.out.println("@AfterEach executes after the execution of each test method");
    }

    @BeforeAll //this method should be static by defaul because of the anotation
    static void setupBeforeEachClass(){
        System.out.println("@BeforeAll executes only once before all test methods execution in the class");
    }

    @AfterAll //this method should be static by defaul because of the anotation
    static void setupAfterEachClass(){
        System.out.println("@AfterAll executes only once after all test methods execution in the class");
    }
    */
}
