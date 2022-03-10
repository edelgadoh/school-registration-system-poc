package com.demo.edwin.schoolregistration.repository;

import com.demo.edwin.schoolregistration.AbstractFunctionalTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CourseRepositoryTest extends AbstractFunctionalTest {

    @Autowired
    CourseRepository courseRepository;

    @Test
    void givenPreCreatedCourses_validateFindByNameIgnoreCase() {
        Assertions.assertTrue(courseRepository.findByNameIgnoreCase("java").isPresent());
        Assertions.assertTrue(courseRepository.findByNameIgnoreCase("terraform").isPresent());
        Assertions.assertTrue(courseRepository.findByNameIgnoreCase("mongodb").isPresent());
        Assertions.assertTrue(courseRepository.findByNameIgnoreCase("php").isEmpty());
        Assertions.assertTrue(courseRepository.findByNameIgnoreCase("scala").isEmpty());
    }

}
