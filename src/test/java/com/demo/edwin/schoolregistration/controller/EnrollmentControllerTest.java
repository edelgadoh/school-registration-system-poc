package com.demo.edwin.schoolregistration.controller;

import com.demo.edwin.schoolregistration.AbstractFunctionalTest;
import com.demo.edwin.schoolregistration.entity.Course;
import com.demo.edwin.schoolregistration.entity.Student;
import com.demo.edwin.schoolregistration.request.CourseDTO;
import com.demo.edwin.schoolregistration.request.EnrollDTO;
import com.demo.edwin.schoolregistration.request.StudentDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

class EnrollmentControllerTest extends AbstractFunctionalTest {

    @Test
    void givenPreCreatedStudentsAndCourses_validateStudentEnrollmentLimit() {

        EnrollDTO enrollmentDtoO1 = new EnrollDTO();
        enrollmentDtoO1.setStudentId(1L);
        for (long i = 1; i <= maxCoursesAllowedByStudent; i++) {
            enrollmentDtoO1.setCourseId(i);
            ResponseEntity<?> responseEntity = restTemplate.postForEntity("/enrollment", enrollmentDtoO1, null);
            Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }

        enrollmentDtoO1.setCourseId(maxCoursesAllowedByStudent + 1L);
        ResponseEntity<?> responseEntity = restTemplate.postForEntity("/enrollment", enrollmentDtoO1, null);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

    }

    @Test
    void givenPreCreatedStudentsAndCourses_validateCourseEnrollmentLimit() {

        EnrollDTO enrollmentDtoO1 = new EnrollDTO();
        enrollmentDtoO1.setCourseId(6L);
        for (long i = 1; i <= maxStudentsAllowedByCourse; i++) {
            enrollmentDtoO1.setStudentId(i + 20);
            ResponseEntity<?> responseEntity = restTemplate.postForEntity("/enrollment", enrollmentDtoO1, null);
            Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }

        enrollmentDtoO1.setStudentId(maxStudentsAllowedByCourse + 21L);
        ResponseEntity<?> responseEntity = restTemplate.postForEntity("/enrollment", enrollmentDtoO1, null);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

    }

    @Test
    void givenNewStudentAndCourses_validateStudentEnrollment() {

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Student200");
        studentDTO.setAge(15);

        ResponseEntity<Student> responseEntity = restTemplate.postForEntity("/student", studentDTO, Student.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Student student = responseEntity.getBody();

        List<Course> courses = new ArrayList<>();
        for (long i = 1; i <= maxCoursesAllowedByStudent; i++) {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setName("NodeJS-v" + i);

            ResponseEntity<Course> responseEntityCourse = restTemplate.postForEntity("/course", courseDTO, Course.class);
            Assertions.assertEquals(HttpStatus.OK, responseEntityCourse.getStatusCode());
            Course course = responseEntityCourse.getBody();
            courses.add(course);
        }

        EnrollDTO enrollmentDtoO1 = new EnrollDTO();
        enrollmentDtoO1.setStudentId(student.getId());
        for (Course course : courses) {
            enrollmentDtoO1.setCourseId(course.getId());
            ResponseEntity<?> responseEntityEnrollment = restTemplate.postForEntity("/enrollment", enrollmentDtoO1, null);
            Assertions.assertEquals(HttpStatus.OK, responseEntityEnrollment.getStatusCode());
        }

        enrollmentDtoO1.setCourseId(1L);
        ResponseEntity<?> responseEntityNotAllowed = restTemplate.postForEntity("/enrollment", enrollmentDtoO1, null);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntityNotAllowed.getStatusCode());

    }

    @Test
    void givenPreCreatedStudentAndNewCourse_enrollStudentsToCourse_validateGetRequest(){

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName("DataScience");
        ResponseEntity<Course> responseEntityCourse = restTemplate.postForEntity("/course", courseDTO, Course.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntityCourse.getStatusCode());
        Course course = responseEntityCourse.getBody();

        EnrollDTO enrollmentDtoO1 = new EnrollDTO();
        enrollmentDtoO1.setCourseId(course.getId());
        for (long i = 1; i <= maxStudentsAllowedByCourse; i++) {
            enrollmentDtoO1.setStudentId(i + 10);
            ResponseEntity<?> responseEntity = restTemplate.postForEntity("/enrollment", enrollmentDtoO1, null);
            Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }

        ResponseEntity<Student[]> responseEntity = restTemplate.getForEntity("/enrollment/course/id/{id}", Student[].class, enrollmentDtoO1.getCourseId());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Student[] students = responseEntity.getBody();
        Assertions.assertEquals(maxStudentsAllowedByCourse, students.length);
    }

    @Test
    void givenPreCreatedStudentAndNewCourses_enrollStudentToCourses_validateGetRequest(){

        List<Course> courses = new ArrayList<>();
        for (long i = 1; i <= maxCoursesAllowedByStudent; i++) {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setName("Spring-v-" + i);

            ResponseEntity<Course> responseEntityCourse = restTemplate.postForEntity("/course", courseDTO, Course.class);
            Assertions.assertEquals(HttpStatus.OK, responseEntityCourse.getStatusCode());
            Course course = responseEntityCourse.getBody();
            courses.add(course);
        }

        EnrollDTO enrollmentDtoO1 = new EnrollDTO();
        enrollmentDtoO1.setStudentId(30L);
        for (Course course : courses) {
            enrollmentDtoO1.setCourseId(course.getId());
            ResponseEntity<?> responseEntityEnrollment = restTemplate.postForEntity("/enrollment", enrollmentDtoO1, null);
            Assertions.assertEquals(HttpStatus.OK, responseEntityEnrollment.getStatusCode());
        }

        ResponseEntity<Course[]> responseEntity = restTemplate.getForEntity("/enrollment/student/id/{id}", Course[].class, enrollmentDtoO1.getStudentId());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Course[] students = responseEntity.getBody();
        Assertions.assertEquals(maxCoursesAllowedByStudent, students.length);
    }


}