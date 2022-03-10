package com.demo.edwin.schoolregistration.exception;

public class CourseNotFoundException extends Exception {
    public CourseNotFoundException(Long id) {
        super("Course id not found: " + id);
    }

    public CourseNotFoundException(String name) {
        super("Course name not found: " + name);
    }
}
