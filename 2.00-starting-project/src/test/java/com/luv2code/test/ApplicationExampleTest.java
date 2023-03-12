package com.luv2code.test;

import com.luv2code.test.models.CollegeStudent;
import com.luv2code.test.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MvcTestingExampleApplication.class) //this is for adding this test class to the spring context because the package implementation is different from the one in java package this end with test and the other one with component
public class ApplicationExampleTest {

    private static  int count = 0;

    @Value("${info.app.name}")
    private String appInfo;

    @Value("${info.school.name}")
    private String schoolName;

    @Value("${info.app.description}")
    private String appDesc;

    @Value("${info.app.version}")
    private String appVersion;

    @Autowired
    CollegeStudent student;

    @Autowired
    StudentGrades studentGrades;

    @Autowired
    ApplicationContext context;

    @BeforeEach
    public void beforeEach(){
        count = count + 1;
        System.out.println("Testing: " + appInfo + " which is " + appDesc +
                "  Version: " + appVersion + ". Execution of test method " + count);
        student.setFirstname("Greg");
        student.setLastname("Smith");
        student.setEmailAddress("gsmith@j2gsoft-school.com");
        studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0,85.0,76.50,91.75)));
        student.setStudentGrades(studentGrades);
    }

    @Test
    @DisplayName("Add grade results for student grades")
    void addGradesResultsForStudentGrades(){
        double expected = 353.25;
        assertEquals(expected, studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));
    }

    @Test
    @DisplayName("Add grade results for student grades not equal")
    void addGradesResultsForStudentGradesNotEquals(){
        double expected = 0;
        assertNotEquals(expected, studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));
    }

    @Test
    @DisplayName("Is grade greater")
    void isGradeGreaterStudentGrade(){
        assertTrue(studentGrades.isGradeGreater(90,75),"failure - should be true");
    }

    @Test
    @DisplayName("Is grade greater false")
    void isGradeGreaterStudentGradeFalse(){
        assertFalse(studentGrades.isGradeGreater(55,75),"failure - should be true");
    }

    @Test
    @DisplayName("Check Null for student grades")
    void checkNullForStudentGrades(){
        assertNotNull(studentGrades.checkNull(student.getStudentGrades().getMathGradeResults()),"Object should not be null");
    }

    @Test
    @DisplayName("create Student without grade init")
    void createStudentWithoutGradeInit(){
        CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
        studentTwo.setFirstname("Jeff");
        studentTwo.setLastname("Gonzalez");
        studentTwo.setEmailAddress("jgonzalez@j2gsoft.com");
        /**Assertions**/
        assertNotNull(studentTwo.getFirstname());
        assertNotNull(studentTwo.getLastname());
        assertNotNull(studentTwo.getEmailAddress());
        assertNull(studentGrades.checkNull(studentTwo.getStudentGrades()));

    }

    @DisplayName("Verify students are prototypes")
    @Test
    public void verifyStudentsArePrototypes(){
        CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
        assertNotSame(student,studentTwo);
    }

    @DisplayName("Find grade point Average")
    @Test
    public void findGradePointAverage(){
   assertAll("Test all assertEquals",
           ()->assertEquals(353.25,studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults())),
           ()->assertEquals(88.31,studentGrades.findGradePointAverage(student.getStudentGrades().getMathGradeResults()))
         );
    }

}
