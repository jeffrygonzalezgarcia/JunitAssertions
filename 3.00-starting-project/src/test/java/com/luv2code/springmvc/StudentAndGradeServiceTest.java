package com.luv2code.springmvc;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.repository.HistoryGradeDao;
import com.luv2code.springmvc.repository.MathGradesDao;
import com.luv2code.springmvc.repository.ScienceGradeDao;
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
import java.util.Collection;
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

    @Autowired
    MathGradesDao mathGradesDao;

    @Autowired
    ScienceGradeDao scienceGradeDao;

    @Autowired
    HistoryGradeDao historyGradeDao;

    @BeforeEach
    public void setupDataBase(){
        jdbcTemplate.execute("insert into student(id, firstname, lastname, email_address)" +
                "values (1, 'Eric', 'Roby', 'eric.roby@luv2code_school.com')" );

        jdbcTemplate.execute("insert into math_grade(id, student_id, grade)" +
                "values (1, 1, 100.00)" );
        jdbcTemplate.execute("insert into science_grade(id, student_id, grade)" +
                "values (1, 1, 100.00)" );
        jdbcTemplate.execute("insert into history_grade(id, student_id, grade)" +
                "values (1, 1, 100.00)" );

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
        Optional<MathGrade> mathGrade = mathGradesDao.findById(1);
        Optional<ScienceGrade> scienceGrade = scienceGradeDao.findById(1);
        Optional<HistoryGrade> historyGrade = historyGradeDao.findById(1);

        assertTrue(deleteCollegeStudent.isPresent(), "Return true");
        assertTrue(mathGrade.isPresent());
        assertTrue(scienceGrade.isPresent());
        assertTrue(historyGrade.isPresent());

        studentService.deleteStudent(1);
        deleteCollegeStudent = studentDao.findById(1);
        mathGrade = mathGradesDao.findById(1);
        scienceGrade = scienceGradeDao.findById(1);
        historyGrade = historyGradeDao.findById(1);

        assertFalse(deleteCollegeStudent.isPresent(), "Return false");
        assertFalse(mathGrade.isPresent(), "Return false");
        assertFalse(scienceGrade.isPresent(), "Return false");
        assertFalse(historyGrade.isPresent(), "Return false");


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

    @Test
    public void createGradeService(){
        //create the grade
        assertTrue(studentService.createGrade(80.50,1,"math"));
        assertTrue(studentService.createGrade(80.50,1,"science"));
        assertTrue(studentService.createGrade(80.50,1,"history"));

        //get all grades with studentId
        Iterable<MathGrade> mathGrades = mathGradesDao.findGradeByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradeDao.findGradeByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradeDao.findGradeByStudentId(1);
        //verify there is grades
        assertTrue(((Collection<MathGrade>) mathGrades).size() == 2,
                    "Student has math grades");
        assertTrue(((Collection<ScienceGrade>) scienceGrades).size() == 2,
                "Student has science grades");
        assertTrue(historyGrades.iterator().hasNext(), "Student has history grades");
    }

    @Test
    public void createGradeServiceReturnFalse(){
        assertFalse(studentService.createGrade(105,1,"math"));
        assertFalse(studentService.createGrade(-5,1,"math"));
        assertFalse(studentService.createGrade(80.50,2,"math"));
        assertFalse(studentService.createGrade(80.50,1,"literature"));
    }

    @Test
    public void deleteGradeService(){
        assertEquals(1, studentService.deleteStudent(1,"math"),"Returns student id after delete");
        assertEquals(1, studentService.deleteStudent(1,"science"),"Returns student id after delete");
        assertEquals(1, studentService.deleteStudent(1,"history"),"Returns student id after delete");
    }


    @Test
    public void deleteGradeServiceReturnStudentIdOfZero(){
        assertEquals(0, studentService.deleteStudent(0,"science"),"No student should have 0 id");
        assertEquals(0, studentService.deleteStudent(1,"literature"),"No student should have a literature class");
    }

    @Test
    public void studentInformation(){
        GradebookCollegeStudent gradebookCollegeStudent = studentService.studentInformation(1);
        assertNotNull(gradebookCollegeStudent);
        assertEquals(1,gradebookCollegeStudent.getId());
        assertEquals("Eric",gradebookCollegeStudent.getFirstname());
        assertEquals("Roby",gradebookCollegeStudent.getLastname());
        assertEquals("eric.roby@luv2code_school.com",gradebookCollegeStudent.getEmailAddress());
        assertTrue(gradebookCollegeStudent.getStudentGrades().getMathGradeResults().size() == 1);
        assertTrue(gradebookCollegeStudent.getStudentGrades().getScienceGradeResults().size() == 1);
        assertTrue(gradebookCollegeStudent.getStudentGrades().getHistoryGradeResults().size() == 1);
    }

    @Test
    public void studentIdInformationServiceReturnNull(){
        GradebookCollegeStudent gradebookCollegeStudent = studentService.studentInformation(0);
        assertNull(gradebookCollegeStudent);
    }


    @AfterEach
    public void setupAfterTransaction(){
        jdbcTemplate.execute("DELETE FROM student");
        jdbcTemplate.execute("DELETE FROM math_grade");
        jdbcTemplate.execute("DELETE FROM science_grade");
        jdbcTemplate.execute("DELETE FROM history_grade");
    }


}
