package com.j2gsoft.junitassertions;

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
}
