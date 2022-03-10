package com.demo.edwin.schoolregistration.controller;

import com.demo.edwin.schoolregistration.entity.Course;
import com.demo.edwin.schoolregistration.entity.Student;
import com.demo.edwin.schoolregistration.exception.CourseNotFoundException;
import com.demo.edwin.schoolregistration.exception.EnrollmentAlreadyExistException;
import com.demo.edwin.schoolregistration.exception.EnrollmentConstraintException;
import com.demo.edwin.schoolregistration.exception.StudentNotFoundException;
import com.demo.edwin.schoolregistration.request.EnrollDTO;
import com.demo.edwin.schoolregistration.service.EnrollmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("enrollment")
@Slf4j
public class EnrollmentController {

    @Autowired
    EnrollmentService enrollmentService;

    @PostMapping
    ResponseEntity<Void> enrollStudent(@RequestBody EnrollDTO enrollDTO, HttpServletResponse response) throws EnrollmentAlreadyExistException, CourseNotFoundException, StudentNotFoundException, EnrollmentConstraintException {
        enrollmentService.enrollStudent(enrollDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/course/id/{id}")
    ResponseEntity<List<Student>> findAllStudentsByCourse(@PathVariable Long id) {
        List<Student> studentList = enrollmentService.findAllStudentsByCourse(id);
        return ResponseEntity.ok(studentList);
    }

    @GetMapping("/student/id/{id}")
    ResponseEntity<List<Course>> findAllCoursesByStudent(@PathVariable Long id) {
        List<Course> courseList = enrollmentService.findAllCoursesByStudent(id);
        return ResponseEntity.ok(courseList);
    }

    @GetMapping("/courses-without-students")
    ResponseEntity<List<Course>> findAllCoursesWithoutStudents() {
        List<Course> courseList = enrollmentService.findAllCoursesWithStudentRegisteredSize(0);
        return ResponseEntity.ok(courseList);
    }

    @GetMapping("/students-without-courses")
    ResponseEntity<List<Student>> findAllStudentsWithoutCourses() {
        List<Student> studentList = enrollmentService.findAllStudentsWithCourseRegisteredSize(0);
        return ResponseEntity.ok(studentList);
    }

}
