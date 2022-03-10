package com.demo.edwin.schoolregistration.service;

import com.demo.edwin.schoolregistration.entity.Course;
import com.demo.edwin.schoolregistration.exception.CourseAlreadyExistException;
import com.demo.edwin.schoolregistration.exception.CourseInvalidDataException;
import com.demo.edwin.schoolregistration.repository.CourseRepository;
import com.demo.edwin.schoolregistration.request.CourseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Test
    void givenInvalidData_thenThrownException() {

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName(" ");

        when(courseRepository.findByNameIgnoreCase(courseDTO.getName())).thenReturn(Optional.empty());

        Exception exception = assertThrows(CourseInvalidDataException.class, () -> courseService.saveCourse(courseDTO));

        assertTrue(exception.getMessage().contains("Invalid Course data to save"));

    }

    @Test
    void givenCourseAlreadyRegistered_thenThrownException() {

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName("XP");

        when(courseRepository.findByNameIgnoreCase(courseDTO.getName())).thenReturn(Optional.of(new Course()));

        Exception exception = assertThrows(CourseAlreadyExistException.class, () -> courseService.saveCourse(courseDTO));

        assertEquals("Course already exists", exception.getMessage());

    }

}
