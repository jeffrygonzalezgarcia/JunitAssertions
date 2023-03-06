package com.j2gsoft.junitassertions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoUtils {

    private String academy = "J2gsoft Academy";
    private String academyDuplicate = academy;
    private String[] firstThreeLettersOfAlphabet = {"A", "B", "C"};
    private List<String> academyInList = List.of("j2g", "soft", "academy");



    public int add(int a, int b){
        return a+b;
    }

    public Object checkNull(Object obj){
        if (obj != null){
            return obj;
        }
        return null;
    }

    public String getAcademy() {
        return academy;
    }

    public String getAcademyDuplicate() {
        return academyDuplicate;
    }

    public boolean isGrater(int n1, int n2){
        return n1 > n2;
    }

    public String[] getFirstThreeLettersOfAlphabet() {
        return firstThreeLettersOfAlphabet;
    }

    public List<String> getAcademyInList() {
        return academyInList;
    }

    public String throwException(int a) throws Exception{
        if(a < 0)
            throw new Exception("Value should be greater than or equal to 0");
        return "Value is greater than or equal to 0";
    }

    public void checkTimeout() throws InterruptedException{
        System.out.println("I'm going to sleep");
        Thread.sleep(2000);
        System.out.println("Sleeping over");
    }

    public int multiply(int a, int b){
        return a * b;
    }
}
