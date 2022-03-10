package com.demo.edwin.schoolregistration.service;

import com.demo.edwin.schoolregistration.entity.Student;
import com.demo.edwin.schoolregistration.exception.StudentAlreadyExistException;
import com.demo.edwin.schoolregistration.exception.StudentInvalidDataException;
import com.demo.edwin.schoolregistration.exception.StudentNotFoundException;
import com.demo.edwin.schoolregistration.repository.StudentRepository;
import com.demo.edwin.schoolregistration.request.StudentDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student getStudentBy(String name) throws StudentNotFoundException {
        return studentRepository.findByNameIgnoreCase(name).orElseThrow(() -> new StudentNotFoundException(name));
    }

    public Student getStudentBy(Long id) throws StudentNotFoundException {
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Transactional
    public Student saveStudent(StudentDTO studentDTO) throws StudentInvalidDataException, StudentAlreadyExistException {
        if (studentRepository.findByNameIgnoreCase(studentDTO.getName()).isEmpty()) {
            if (isValidData(studentDTO)) {
                Student student = new Student();
                student.setName(studentDTO.getName());
                student.setAge(studentDTO.getAge());
                return studentRepository.save(student);
            } else {
                throw new StudentInvalidDataException("Invalid Student data to save: " + studentDTO);
            }
        } else {
            throw new StudentAlreadyExistException("Student already exists");
        }
    }

    @Transactional
    public Student updateStudent(Long id, StudentDTO studentDTO) throws StudentNotFoundException, StudentInvalidDataException {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            if (isValidData(studentDTO)) {
                student.setName(studentDTO.getName().trim());
                student.setAge(studentDTO.getAge());
                return studentRepository.save(student);
            } else {
                throw new StudentInvalidDataException("Invalid Student data to update: " + studentDTO);
            }
        }
        throw new StudentNotFoundException(id);
    }

    private boolean isValidData(StudentDTO studentDTO) {
        return StringUtils.isNotBlank(studentDTO.getName()) &&
                (studentDTO.getAge() != null && studentDTO.getAge() > 0);
    }

    @Transactional
    public void deleteStudent(Long id) throws StudentNotFoundException {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            studentRepository.delete(optionalStudent.get());
        } else {
            throw new StudentNotFoundException(id);
        }
    }


}
