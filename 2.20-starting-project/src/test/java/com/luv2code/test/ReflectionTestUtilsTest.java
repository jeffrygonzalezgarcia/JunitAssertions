package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes= MvcTestingExampleApplication.class)
public class ReflectionTestUtilsTest {

    @Autowired
    CollegeStudent student;

    @Autowired
    StudentGrades studentGrades;

    @Autowired
    ApplicationContext context;

    @BeforeEach
    public void beforeEach() {

        student.setFirstname("Eric");
        student.setLastname("Roby");
        student.setEmailAddress("eric.roby@luv2code_school.com");
      //  studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.50, 91.75)));
        student.setStudentGrades(studentGrades);

        ReflectionTestUtils.setField(student,"id",1);
        ReflectionTestUtils.setField(student,"studentGrades", new StudentGrades(new ArrayList<>(Arrays.asList(
                100.0,85.0,76.50,91.75
        ))));
    }

    @Test
    public void getPrivateField(){
        assertEquals(1,ReflectionTestUtils.getField(student,"id"));
    }

    @Test
    public void getPrivateMethod(){
        assertEquals("Eric 1",ReflectionTestUtils.invokeMethod(student,"getFirstNameAndId"),"Fail private method not call");
    }


}
