package com.luv2code.springmvc;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {

    @Autowired
    StudentAndGradeService studentService;

    @Autowired
    StudentDao studentDao;

    /**
     *In spring boot, if an embedded database is listed as a dependency then spring boot wiil
     * auto-configure the database connection.
     * This project has a H2 as a dependency, that is why the code below is working without manual configuration of the db.
     */
    @Test
    public void createStudentService(){

        studentService.createStudent("Jeffry","Gonzalez","jgonzalez@j2gsoft.com");

        CollegeStudent student = studentDao.
                findByEmailAddress("jgonzalez@j2gsoft.com");

        assertEquals("jgonzalez@j2gsoft.com",
                student.getEmailAddress(),"find by email");
    }
}
