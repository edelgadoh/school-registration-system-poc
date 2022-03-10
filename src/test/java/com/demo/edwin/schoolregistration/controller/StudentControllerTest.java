package com.demo.edwin.schoolregistration.controller;

import com.demo.edwin.schoolregistration.AbstractFunctionalTest;
import com.demo.edwin.schoolregistration.entity.Student;
import com.demo.edwin.schoolregistration.request.StudentDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class StudentControllerTest extends AbstractFunctionalTest {

    @Test
    void givenPreCreatedStudents_validateGetById() {

        ResponseEntity<Student> responseEntity = restTemplate.getForEntity("/student/id/{id}", Student.class, 1);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Student student = responseEntity.getBody();
        Assertions.assertEquals("Student001", student.getName());

    }

    @Test
    void givenPreCreatedStudents_validateGetByName() {

        ResponseEntity<Student> responseEntity = restTemplate.getForEntity("/student/name/{name}", Student.class, "Student002");
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Student student = responseEntity.getBody();
        Assertions.assertEquals("Student002", student.getName());
        Assertions.assertEquals(24, student.getAge());

    }

    @Test
    void givenNewStudent_validateRegisterAndThenRetryToGetErrorStatus() {

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Student100");
        studentDTO.setAge(1);

        ResponseEntity<Student> responseEntity = restTemplate.postForEntity("/student", studentDTO, Student.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Student student = responseEntity.getBody();
        Assertions.assertEquals("Student100", student.getName());

        ResponseEntity<Student> responseEntityRetrieved = restTemplate.getForEntity("/student/id/{id}", Student.class, student.getId());
        Assertions.assertEquals(HttpStatus.OK, responseEntityRetrieved.getStatusCode());
        Student studentRetrieved = responseEntityRetrieved.getBody();
        Assertions.assertEquals(student.getName(), studentRetrieved.getName());
        Assertions.assertEquals(student.getAge(), studentRetrieved.getAge());

        ResponseEntity<Student> responseEntitySame = restTemplate.postForEntity("/student", studentDTO, Student.class);
        Assertions.assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntitySame.getStatusCode());

    }

    @Test
    void givenNewStudent_validateRegisterAndThenUpdateIt() {

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Edwin");
        studentDTO.setAge(17);

        ResponseEntity<Student> responseEntity = restTemplate.postForEntity("/student", studentDTO, Student.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Student student = responseEntity.getBody();
        Assertions.assertEquals("Edwin", student.getName());

        StudentDTO studentDtoUpdated = new StudentDTO();
        studentDtoUpdated.setName("EdwinDH");
        studentDtoUpdated.setAge(19);

        ResponseEntity<Student> responseEntityUpdated = restTemplate.exchange("/student/id/"+student.getId(), HttpMethod.PUT, new HttpEntity<>(studentDtoUpdated), Student.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntityUpdated.getStatusCode());

        ResponseEntity<Student> responseEntityRetrieved = restTemplate.getForEntity("/student/id/{id}", Student.class, student.getId());
        Assertions.assertEquals(HttpStatus.OK, responseEntityRetrieved.getStatusCode());
        Student studentRetrieved = responseEntityRetrieved.getBody();
        Assertions.assertEquals("EdwinDH", studentRetrieved.getName());
        Assertions.assertEquals(19, studentRetrieved.getAge());

    }

    @Test
    void givenNewStudent_validateRegisterAndThenRemoveIt() {

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Student101");
        studentDTO.setAge(1);

        ResponseEntity<Student> responseEntity = restTemplate.postForEntity("/student", studentDTO, Student.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Student student = responseEntity.getBody();
        Assertions.assertEquals("Student101", student.getName());

        restTemplate.delete("/student/id/{id}", student.getId());

        ResponseEntity<Student> responseEntityRetrieved = restTemplate.getForEntity("/student/id/{id}", Student.class, student.getId());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntityRetrieved.getStatusCode());

    }

}