package com.demo.edwin.schoolregistration.exception;

public class StudentNotFoundException extends Exception {
    public StudentNotFoundException(Long id) {
        super("Student id not found: " + id);
    }

    public StudentNotFoundException(String name) {
        super("Student name not found: " + name);
    }
}
