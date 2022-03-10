package com.demo.edwin.schoolregistration.controller;

import com.demo.edwin.schoolregistration.entity.Course;
import com.demo.edwin.schoolregistration.exception.CourseAlreadyExistException;
import com.demo.edwin.schoolregistration.exception.CourseInvalidDataException;
import com.demo.edwin.schoolregistration.exception.CourseNotFoundException;
import com.demo.edwin.schoolregistration.request.CourseDTO;
import com.demo.edwin.schoolregistration.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping("/name/{name}")
    ResponseEntity<Course> getCourseByName(@PathVariable String name) throws CourseNotFoundException {
        return ResponseEntity.ok(courseService.getCourseBy(name));
    }

    @GetMapping("/id/{id}")
    ResponseEntity<Course> getCourseById(@PathVariable Long id) throws CourseNotFoundException {
        return ResponseEntity.ok(courseService.getCourseBy(id));
    }

    @PostMapping
    ResponseEntity<Course> saveCourse(@RequestBody CourseDTO courseDTO, HttpServletResponse response) throws CourseInvalidDataException, CourseAlreadyExistException {
        return ResponseEntity.ok(courseService.saveCourse(courseDTO));
    }

    @PutMapping("/id/{id}")
    ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO, HttpServletResponse response) throws CourseInvalidDataException, CourseNotFoundException {
        return ResponseEntity.ok(courseService.updateCourse(id, courseDTO));
    }

    @DeleteMapping("/id/{id}")
    ResponseEntity<Void> deleteCourse(@PathVariable Long id) throws CourseNotFoundException {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
