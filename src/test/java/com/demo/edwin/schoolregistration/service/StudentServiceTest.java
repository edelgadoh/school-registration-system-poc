package com.demo.edwin.schoolregistration.service;

import com.demo.edwin.schoolregistration.entity.Student;
import com.demo.edwin.schoolregistration.exception.StudentAlreadyExistException;
import com.demo.edwin.schoolregistration.exception.StudentInvalidDataException;
import com.demo.edwin.schoolregistration.repository.StudentRepository;
import com.demo.edwin.schoolregistration.request.StudentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Test
    void givenInvalidStudentName_thenThrownException() {

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(" ");

        when(studentRepository.findByNameIgnoreCase(studentDTO.getName())).thenReturn(Optional.empty());

        Exception exception = assertThrows(StudentInvalidDataException.class, () -> studentService.saveStudent(studentDTO));

        assertTrue(exception.getMessage().contains("Invalid Student data to save"));

    }

    @Test
    void givenIncompleteData_thenThrownException() {

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Any");

        when(studentRepository.findByNameIgnoreCase(studentDTO.getName())).thenReturn(Optional.empty());

        Exception exception = assertThrows(StudentInvalidDataException.class, () -> studentService.saveStudent(studentDTO));
        assertTrue(exception.getMessage().contains("Invalid Student data to save"));

    }

    @Test
    void givenInvalidAge_thenThrownException() {

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Any");
        studentDTO.setAge(-1);

        when(studentRepository.findByNameIgnoreCase(studentDTO.getName())).thenReturn(Optional.empty());

        Exception exception = assertThrows(StudentInvalidDataException.class, () -> studentService.saveStudent(studentDTO));
        assertTrue(exception.getMessage().contains("Invalid Student data to save"));

    }

    @Test
    void givenStudentAlreadyRegistered_thenThrownException() {

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Any student");

        when(studentRepository.findByNameIgnoreCase(studentDTO.getName())).thenReturn(Optional.of(new Student()));

        Exception exception = assertThrows(StudentAlreadyExistException.class, () -> studentService.saveStudent(studentDTO));

        assertEquals("Student already exists", exception.getMessage());

    }

}
