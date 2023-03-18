package com.luv2code.springmvc.service;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {

    @Autowired
    StudentDao studentDao;

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
        }
    }

    public Iterable<CollegeStudent> getGradeBook() {
        Iterable<CollegeStudent> collegeStudents = studentDao.findAll();
        return collegeStudents;
    }
}
