package com.luv2code.springmvc.service;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.repository.HistoryGradeDao;
import com.luv2code.springmvc.repository.MathGradesDao;
import com.luv2code.springmvc.repository.ScienceGradeDao;
import com.luv2code.springmvc.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {

    @Autowired
    StudentDao studentDao;

    @Autowired
    @Qualifier("mathGrades")
    private MathGrade mathGrade;

    @Autowired
    @Qualifier("scienceGrades")
    private ScienceGrade scienceGrade;

    @Autowired
    @Qualifier("historyGrades")
    private HistoryGrade historyGrade;

    @Autowired
    private MathGradesDao mathGradesDao;

    @Autowired
    private ScienceGradeDao scienceGradeDao;

    @Autowired
    private HistoryGradeDao historyGradeDao;

    @Autowired
    StudentGrades studentGrades;

    public void createStudent(String firstName, String lastName, String emailAddress){
        CollegeStudent student = new CollegeStudent(firstName,lastName,emailAddress);
        student.setId(0);
        studentDao.save(student);
    }

    public boolean checkIfStudentIsNull(int i) {
        Optional<CollegeStudent> student = studentDao.findById(i);
        if (student.isPresent()){
            return true;
        }
            return false;
    }

    public void deleteStudent(int i) {
        if (checkIfStudentIsNull(i)){
            studentDao.deleteById(i);
            mathGradesDao.deleteByStudentId(i);
            scienceGradeDao.deleteByStudentId(i);
            historyGradeDao.deleteByStudentId(i);
        }
    }

    public Iterable<CollegeStudent> getGradeBook() {
        Iterable<CollegeStudent> collegeStudents = studentDao.findAll();
        return collegeStudents;
    }

    public boolean createGrade(double grade, int studentId, String gradeType) {
        if(!checkIfStudentIsNull(studentId)){
            return false;
        }

        if(grade >= 0 && grade <= 100){
            if(gradeType.equals("math")){
                mathGrade.setId(0);
                mathGrade.setGrade(grade);
                mathGrade.setStudentId(studentId);
                mathGradesDao.save(mathGrade);
                return true;
            }
            if(gradeType.equals("science")){
                scienceGrade.setId(0);
                scienceGrade.setGrade(grade);
                scienceGrade.setStudentId(studentId);
                scienceGradeDao.save(scienceGrade);
                return true;
            }
            if(gradeType.equals("history")){
                historyGrade.setId(0);
                historyGrade.setGrade(grade);
                historyGrade.setStudentId(studentId);
                historyGradeDao.save(historyGrade);
                return true;
            }

        }
        return false;
    }

    public int deleteStudent(int i, String gradeType) {
        int studentId = 0;

        if(gradeType.equals("math")){
            Optional<MathGrade> grade = mathGradesDao.findById(i);
            if(!grade.isPresent()){
                return studentId;
            }
            studentId = grade.get().getStudentId();
            mathGradesDao.deleteById(i);
        }else if(gradeType.equals("science")){
            Optional<ScienceGrade> science = scienceGradeDao.findById(i);
            if(!science.isPresent()){
                return studentId;
            }
            studentId = science.get().getStudentId();
            scienceGradeDao.deleteById(i);
        }else if(gradeType.equals("history")){
            Optional<HistoryGrade> history = historyGradeDao.findById(i);
            if(!history.isPresent()){
                return studentId;
            }
            studentId = history.get().getStudentId();
            historyGradeDao.deleteById(i);
        }
        return studentId;
    }

    public GradebookCollegeStudent studentInformation(int id) {

        if (!checkIfStudentIsNull(0))
            return null;

        Optional<CollegeStudent> student = studentDao.findById(id);
        Iterable<MathGrade> mathGrades = mathGradesDao.findGradeByStudentId(id);
        Iterable<ScienceGrade> scienceGrades = scienceGradeDao.findGradeByStudentId(id);
        Iterable<HistoryGrade> historyGrades = historyGradeDao.findGradeByStudentId(id);

        //Convert a Iterable to a List
        List<Grade> mathGradeList = new ArrayList<>();
        mathGrades.forEach(mathGradeList::add);

        List<Grade> scienceGradeList = new ArrayList<>();
        scienceGrades.forEach(scienceGradeList::add);

        List<Grade> historyGradeList = new ArrayList<>();
        historyGrades.forEach(historyGradeList::add);

        studentGrades.setMathGradeResults(mathGradeList);
        studentGrades.setScienceGradeResults(scienceGradeList);
        studentGrades.setHistoryGradeResults(historyGradeList);

        GradebookCollegeStudent gradebookCollegeStudent = new GradebookCollegeStudent(student.get().getId(),student.get().getFirstname(),student.get().getLastname(),
        student.get().getEmailAddress(),studentGrades);

        return gradebookCollegeStudent;

    }
}
