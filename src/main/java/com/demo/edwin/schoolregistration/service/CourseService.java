package com.demo.edwin.schoolregistration.service;

import com.demo.edwin.schoolregistration.entity.Course;
import com.demo.edwin.schoolregistration.exception.CourseAlreadyExistException;
import com.demo.edwin.schoolregistration.exception.CourseNotFoundException;
import com.demo.edwin.schoolregistration.exception.CourseInvalidDataException;
import com.demo.edwin.schoolregistration.repository.CourseRepository;
import com.demo.edwin.schoolregistration.request.CourseDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course getCourseBy(String name) throws CourseNotFoundException {
        return courseRepository.findByNameIgnoreCase(name).orElseThrow(() -> new CourseNotFoundException(name));
    }

    public Course getCourseBy(Long id) throws CourseNotFoundException {
        return courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
    }

    @Transactional
    public Course saveCourse(CourseDTO courseDTO) throws CourseInvalidDataException, CourseAlreadyExistException {
        if (courseRepository.findByNameIgnoreCase(courseDTO.getName()).isEmpty()) {
            if (isValidData(courseDTO)) {
                Course course = new Course();
                course.setName(courseDTO.getName());
                return courseRepository.save(course);
            } else {
                throw new CourseInvalidDataException("Invalid Course data to save: " + courseDTO);
            }
        } else {
            throw new CourseAlreadyExistException("Course already exists");
        }
    }

    @Transactional
    public Course updateCourse(Long id, CourseDTO courseDTO) throws CourseInvalidDataException, CourseNotFoundException {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            if (isValidData(courseDTO)) {
                course.setName(courseDTO.getName());
                return courseRepository.save(course);
            } else {
                throw new CourseInvalidDataException("Invalid Course data to update: " + courseDTO);
            }
        }
        throw new CourseNotFoundException(id);
    }

    private boolean isValidData(CourseDTO courseDTO) {
        return StringUtils.isNotBlank(courseDTO.getName());
    }

    @Transactional
    public void deleteCourse(Long id) throws CourseNotFoundException {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            courseRepository.delete(optionalCourse.get());
        } else {
            throw new CourseNotFoundException(id);
        }
    }


}
