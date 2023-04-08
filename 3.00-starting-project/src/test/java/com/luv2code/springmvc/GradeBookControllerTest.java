package com.luv2code.springmvc;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class GradeBookControllerTest {

    private static MockHttpServletRequest request;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Mock
    private StudentAndGradeService studentCreateServiceMock;

    @Autowired
    StudentDao studentDao;

    @BeforeAll //according to junit docs @beforeAll methods must be declared as static also must be public and return void.
    public static void setup(){
        request = new MockHttpServletRequest();
        request.setParameter("firstName", "Jeff");
        request.setParameter("lastName", "Gonzalez");
        request.setParameter("emailAddress", "jeff.gonzalez@j2gsoft_school.com");
    }

    @Value("${sql.scripts.create.student}")
    private String sqlAddStudent;

    @Value("${sql.scripts.create.math.grade}")
    private String sqlAddMathGrade;

    @Value("${sql.scripts.create.science.grade}")
    private String sqlAddScienceGrade;

    @Value("${sql.scripts.create.history.grade}")
    private String sqlAddHistoryGrade;

    @Value("${sql.script.delete.student}")
    private String sqlDeleteStudent;

    @Value("${sql.script.delete.math.grade}")
    private String sqlDeleteMathGrade;

    @Value("${sql.script.delete.science}")
    private String sqlDeleteScienceGrade;

    @Value("${sql.script.delete.history}")
    private String sqlDeleteHistoryGrade;

    @BeforeEach
    public void setupDataBase(){
        jdbcTemplate.execute(sqlAddStudent);
        jdbcTemplate.execute(sqlAddMathGrade);
        jdbcTemplate.execute(sqlAddScienceGrade);
        jdbcTemplate.execute(sqlAddHistoryGrade);
    }


    @Test
    public void getStudentsHttpRequest(){
        CollegeStudent studentOne = new CollegeStudent("Jeff", "Gonzalez",
                                            "jeff.gonzalez@j2gsoft_school.com");
        CollegeStudent studentTwo = new CollegeStudent("Vins", "Pachino",
                "jeff.gonzalez@j2gsoft_school.com");

        List<CollegeStudent> collegeStudents = new ArrayList<>(Arrays.asList(studentOne,studentTwo));
        when(studentCreateServiceMock.getGradeBook()).thenReturn(collegeStudents);

        assertIterableEquals(collegeStudents,studentCreateServiceMock.getGradeBook());

        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                    .andExpect(status().isOk()).andReturn();

            ModelAndView mav = mvcResult.getModelAndView();

            ModelAndViewAssert.assertViewName(mav, "index");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void createStudentHttpRequest(){

        CollegeStudent student = new CollegeStudent("Eric", "Roby", "eric.ruby@j2gsoft.com");

        List<CollegeStudent> collegeStudents = new ArrayList<>(Arrays.asList(student));
        when(studentCreateServiceMock.getGradeBook()).thenReturn(collegeStudents);
        assertIterableEquals(collegeStudents,studentCreateServiceMock.getGradeBook());

        try {
            MvcResult mvcResult = this.mockMvc.perform(post("/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("firstName", request.getParameterValues("firstName"))
                            .param("lastName", request.getParameterValues("lastName"))
                            .param("emailAddress", request.getParameterValues("emailAddress")))
                    .andExpect(status().isOk()).andReturn();

            ModelAndView mav = mvcResult.getModelAndView();
            ModelAndViewAssert.assertViewName(mav,"index");

            CollegeStudent verifyStudent = studentDao.findByEmailAddress("jeff.gonzalez@j2gsoft_school.com");
            assertNotNull(verifyStudent, "student should be found");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void deleteStudentHttpRequest(){
        assertTrue(studentDao.findById(1).isPresent());

        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                .get("/delete/student/{1}"))
                                .andExpect(status().isOk()).andReturn();


        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "index");

        assertFalse(studentDao.findById(1).isPresent());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteStudentHttpRequestErrorPage() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                            .get("/delete/student/{id}", 0))
                            .andExpect(status().isOk()).andReturn();
        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "error");

    }

    @Test
    public void studentInformationHttpRequest() throws Exception{
        assertTrue(studentDao.findById(1).isPresent());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}",1))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav,"studentInformation");
    }

    @Test
    public void studentInformationHttpStudentDoesNotExistRequest() throws Exception{
        assertFalse(studentDao.findById(0).isPresent());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}",0))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav,"error");
    }



    @AfterEach
    public void setupAfterTransaction(){
        jdbcTemplate.execute(sqlDeleteStudent);
        jdbcTemplate.execute(sqlDeleteMathGrade);
        jdbcTemplate.execute(sqlDeleteScienceGrade);
        jdbcTemplate.execute(sqlDeleteHistoryGrade);
    }

}
