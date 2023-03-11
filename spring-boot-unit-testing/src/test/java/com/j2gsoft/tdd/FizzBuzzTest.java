package com.j2gsoft.tdd;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FizzBuzzTest {

    //If number is divisible by 3, print Fizz
    //If number is divisible by 5, print Buzz
    //If number is divisible by 3 and 5, print FizzBuzz
    //If number is NOT  divisible by 3 and 5, then print the number

    @Test
    @DisplayName("Divisible by three")
    @Order(1)
    void forDivisibleByThree(){
      //  fail("fail"); may a fail

        String expected = "Fizz";
        assertEquals(expected, FizzBuzz.compute(3), "Should return Fizz");
    }

    @Test
    @DisplayName("Divisible by five")
    @Order(2)
    void forDivisibleByFive(){
        //  fail("fail"); may a fail

        String expected = "Buzz";
        assertEquals(expected, FizzBuzz.compute(5), "Should return Buzz");
    }

    @Test
    @DisplayName("Divisible by three and five")
    @Order(3)
    void forDivisibleByThreeAndFive(){
        //  fail("fail"); may a fail

        String expected = "FizzBuzz";
        assertEquals(expected, FizzBuzz.compute(15), "Should return FizzBuzz");
    }

    @Test
    @DisplayName("Not Divisible by three and five")
    @Order(4)
    void forNotDivisibleByThreeAndFive(){
        //  fail("fail"); may a fail

        String expected = "1";
        assertEquals(expected, FizzBuzz.compute(1), "Should return 1");
    }

    @ParameterizedTest(name = "value={0}, expected={1}")
    @DisplayName("Testing with some data file")
    @CsvFileSource(resources = "/small-test-data.csv")
    @Order(5)
    void smallDataFile(int value, String expected){
        assertEquals(expected,FizzBuzz.compute(value));
    }

    @ParameterizedTest(name = "value={0}, expected={1}")
    @DisplayName("Testing with medium data file")
    @CsvFileSource(resources = "/medium-test-data.csv")
    @Order(5)
    void medimDataFile(int value, String expected){
        assertEquals(expected,FizzBuzz.compute(value));
    }

    @ParameterizedTest(name = "value={0}, expected={1}")
    @DisplayName("Testing with large data file")
    @CsvFileSource(resources = "/large-test-data.csv")
    @Order(5)
    void largeDataFile(int value, String expected){
        assertEquals(expected,FizzBuzz.compute(value));
    }

}
