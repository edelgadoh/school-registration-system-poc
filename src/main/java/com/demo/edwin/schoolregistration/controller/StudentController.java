package com.demo.edwin.schoolregistration.controller;

import com.demo.edwin.schoolregistration.entity.Student;
import com.demo.edwin.schoolregistration.exception.StudentAlreadyExistException;
import com.demo.edwin.schoolregistration.exception.StudentInvalidDataException;
import com.demo.edwin.schoolregistration.exception.StudentNotFoundException;
import com.demo.edwin.schoolregistration.request.StudentDTO;
import com.demo.edwin.schoolregistration.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("student")
@Slf4j
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/name/{name}")
    ResponseEntity<Student> getStudentByName(@PathVariable String name) throws StudentNotFoundException {
        return ResponseEntity.ok(studentService.getStudentBy(name));
    }

    @GetMapping("/id/{id}")
    ResponseEntity<Student> getStudentById(@PathVariable Long id) throws StudentNotFoundException {
        return ResponseEntity.ok(studentService.getStudentBy(id));
    }

    @PostMapping
    ResponseEntity<Student> saveStudent(@RequestBody StudentDTO studentDTO, HttpServletResponse response) throws StudentAlreadyExistException, StudentInvalidDataException {
        return ResponseEntity.ok(studentService.saveStudent(studentDTO));
    }

    @PutMapping("/id/{id}")
    ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO, HttpServletResponse response) throws StudentInvalidDataException, StudentNotFoundException {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDTO));
    }

    @DeleteMapping("/id/{id}")
    ResponseEntity<Void> deleteStudent(@PathVariable Long id) throws StudentNotFoundException {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
