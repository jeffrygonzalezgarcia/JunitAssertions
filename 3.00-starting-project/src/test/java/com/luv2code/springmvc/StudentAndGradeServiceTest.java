package com.luv2code.springmvc;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {

    @Autowired
    StudentAndGradeService studentService;

    @Autowired
    StudentDao studentDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setupDataBase(){
        jdbcTemplate.execute("insert into student(id, firstname, lastname, email_address)" +
                "values (1, 'Eric', 'Roby', 'eric.roby@luv2code_school.com')" );
    }

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

    @Test
    public void isStudentNullCheck(){
        assertTrue(studentService.checkIfStudentIsNull(1));

        assertFalse(studentService.checkIfStudentIsNull(0));
    }

    @Test
    public void deleteStudentService(){
        Optional<CollegeStudent> deleteCollegeStudent = studentDao.findById(1);
        assertTrue(deleteCollegeStudent.isPresent(), "Return true");

        studentService.deleteStudent(1);
        deleteCollegeStudent = studentDao.findById(1);

        assertFalse(deleteCollegeStudent.isPresent(), "Return false");

    }

    @Sql("/insertData.sql") //This annotation allows me to insert multipls script from a sql file, first execute de beforeeach and then this file.
    @Test
    public void getGradeBookService(){
        Iterable<CollegeStudent> iterableCollegeStudent = studentService.getGradeBook();

        List<CollegeStudent> collegeStudents = new ArrayList<>();

        for (CollegeStudent collegeStudent : iterableCollegeStudent){
            collegeStudents.add(collegeStudent);
        }

        assertEquals(5, collegeStudents.size());
    }

    @AfterEach
    public void setupAfterTransaction(){
        jdbcTemplate.execute("DELETE FROM student");
    }
}
