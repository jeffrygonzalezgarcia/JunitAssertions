package com.j2gsoft.tdd;

import java.util.List;

public class FizzBuzz {

    //This variables are just a test to see how the coverage increase if i keep adding code
    private String name;
    private Integer age;
    private String email;
    private Boolean Merried;
    private List<String> favoriteFood;

    public static String compute(int i){
        StringBuilder result = new StringBuilder();

        if(i % 3 == 0){
            result.append("Fizz");
        }
        if(i % 5 == 0){
            result.append("Buzz");
        }
        if(result.length() == 0){
            result.append(i);
        }
        return result.toString();
    }

//    public static String compute(int i) {
//
//        if((i % 3 == 0) && (i % 5 == 0)){
//            return "FizzBuzz";
//        } else if(i % 3 == 0){
//            return "Fizz";
//        }  else if(i % 5 == 0){
//            return "Buzz";
//        }else{
//            return Integer.toString(i);
//        }
//    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getMerried() {
        return Merried;
    }

    public void setMerried(Boolean merried) {
        Merried = merried;
    }

    public List<String> getFavoriteFood() {
        return favoriteFood;
    }

    public void setFavoriteFood(List<String> favoriteFood) {
        this.favoriteFood = favoriteFood;
    }
}
