package com.demo.edwin.schoolregistration.controller;

import com.demo.edwin.schoolregistration.AbstractFunctionalTest;
import com.demo.edwin.schoolregistration.entity.Course;
import com.demo.edwin.schoolregistration.request.CourseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class CourseControllerTest extends AbstractFunctionalTest {

    @Test
    void givenPreCreatedCourses_validateGetById() {

        ResponseEntity<Course> responseEntity = restTemplate.getForEntity("/course/id/{id}", Course.class, 1);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Course course = responseEntity.getBody();
        Assertions.assertEquals("Java", course.getName());

    }

    @Test
    void givenPreCreatedCourses_validateGetByName() {

        ResponseEntity<Course> responseEntity = restTemplate.getForEntity("/course/name/{name}", Course.class, "terraform");
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Course course = responseEntity.getBody();
        Assertions.assertEquals("Terraform", course.getName());

    }

    @Test
    void givenNewCourse_validateRegisterAndThenRetryToGetErrorStatus() {

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName("NodeJS");

        ResponseEntity<Course> responseEntity = restTemplate.postForEntity("/course", courseDTO, Course.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Course course = responseEntity.getBody();
        Assertions.assertEquals("NodeJS", course.getName());

        ResponseEntity<Course> responseEntityRetrieved = restTemplate.getForEntity("/course/id/{id}", Course.class, course.getId());
        Assertions.assertEquals(HttpStatus.OK, responseEntityRetrieved.getStatusCode());
        Course courseRetrieved = responseEntityRetrieved.getBody();
        Assertions.assertEquals(course.getName(), courseRetrieved.getName());

        ResponseEntity<Course> responseEntitySame = restTemplate.postForEntity("/course", courseDTO, Course.class);
        Assertions.assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntitySame.getStatusCode());

    }

    @Test
    void givenNewCourse_validateRegisterAndThenUpdateIt() {

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName("JUnit");

        ResponseEntity<Course> responseEntity = restTemplate.postForEntity("/course", courseDTO, Course.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Course course = responseEntity.getBody();
        Assertions.assertEquals("JUnit", course.getName());

        CourseDTO courseDtoUpdated = new CourseDTO();
        courseDtoUpdated.setName("JUnit5");

        ResponseEntity<Course> responseEntityUpdated = restTemplate.exchange("/course/id/"+course.getId(), HttpMethod.PUT, new HttpEntity<>(courseDtoUpdated), Course.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntityUpdated.getStatusCode());

        ResponseEntity<Course> responseEntityRetrieved = restTemplate.getForEntity("/course/id/{id}", Course.class, course.getId());
        Assertions.assertEquals(HttpStatus.OK, responseEntityRetrieved.getStatusCode());
        Course courseRetrieved = responseEntityRetrieved.getBody();
        Assertions.assertEquals("JUnit5", courseRetrieved.getName());

    }

    @Test
    void givenNewCourse_validateRegisterAndThenRemoveIt() {

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName("Cucumber");

        ResponseEntity<Course> responseEntity = restTemplate.postForEntity("/course", courseDTO, Course.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Course course = responseEntity.getBody();
        Assertions.assertEquals("Cucumber", course.getName());

        restTemplate.delete("/course/id/{id}", course.getId());

        ResponseEntity<Course> responseEntityRetrieved = restTemplate.getForEntity("/course/id/{id}", Course.class, course.getId());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntityRetrieved.getStatusCode());

    }

}