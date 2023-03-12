package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class MockAnotationTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent collegeStudent;

    @Autowired
    StudentGrades studentGrades;

//    @Mock
//    private ApplicationDao applicationDao;
//
//    @InjectMocks  //call injected dependency mark with @mock or @spy, in this example i'm using the applicationDao mock variable.
//    private ApplicationService applicationService;

    //another way of doing what i have above but this work for every bean not just for the ones anotated with mock and spy
    @MockBean
    private ApplicationDao applicationDao;

    @Autowired
    private ApplicationService applicationService;

    @BeforeEach
    public void beforeEach(){
        collegeStudent.setFirstname("Jack");
        collegeStudent.setLastname("Roky");
        collegeStudent.setEmailAddress("jack.roky@j2gsoft.com");
        collegeStudent.setStudentGrades(studentGrades);
    }

    @DisplayName("When & Verify")
    @Test
    public void assertEqualsTestAddGrades(){
        when(applicationDao.addGradeResultsForSingleClass(studentGrades.getMathGradeResults())).thenReturn(100.00);

        assertEquals(100,applicationService.addGradeResultsForSingleClass(
                                 collegeStudent.getStudentGrades().getMathGradeResults()));

        verify(applicationDao).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
        verify(applicationDao,times(1)).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
    }

    @DisplayName("Find GPA")
    @Test
    public void assertEqualsTestFindGpa(){
        when(applicationDao.findGradePointAverage(studentGrades.getMathGradeResults())).thenReturn(88.31);

        assertEquals(88.31,applicationService.findGradePointAverage(
                collegeStudent.getStudentGrades().getMathGradeResults()));
    }

    @DisplayName("Not Null")
    @Test
    public void assertsNotNull(){
        when(applicationDao.checkNull(studentGrades.getMathGradeResults())).thenReturn(true);

        assertNotNull(applicationService.checkNull(
                collegeStudent.getStudentGrades().getMathGradeResults()),"Object should not be null");
    }

    @DisplayName("Throw runtime exception")
    @Test
    public void throwRuntimeError(){
       CollegeStudent nullStudent = (CollegeStudent) context.getBean("collegeStudent");

       doThrow(new RuntimeException()).when(applicationDao).checkNull(nullStudent); //this throws an exception

        assertThrows(RuntimeException.class, () -> {
            applicationService.checkNull(nullStudent);
        });

        verify(applicationDao, times(1)).checkNull(nullStudent);
    }

    @DisplayName("Multiple stubbing")
    @Test
    public void stubbingConsecutiveCalls(){
        CollegeStudent nullStudent = (CollegeStudent) context.getBean("collegeStudent");

        when(applicationDao.checkNull(nullStudent))
                .thenThrow(new RuntimeException())
                .thenReturn("Do not throw exception second time");

        assertThrows(RuntimeException.class, () ->{
            applicationService.checkNull(nullStudent);
        });

        assertEquals("Do not throw exception second time", applicationService.checkNull(nullStudent));

        verify(applicationDao, times(2)).checkNull(nullStudent);
    }
}
