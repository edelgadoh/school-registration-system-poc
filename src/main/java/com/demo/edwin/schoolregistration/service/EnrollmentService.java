package com.demo.edwin.schoolregistration.service;

import com.demo.edwin.schoolregistration.entity.Course;
import com.demo.edwin.schoolregistration.entity.Enrollment;
import com.demo.edwin.schoolregistration.entity.EnrollmentKey;
import com.demo.edwin.schoolregistration.entity.Student;
import com.demo.edwin.schoolregistration.exception.CourseNotFoundException;
import com.demo.edwin.schoolregistration.exception.EnrollmentAlreadyExistException;
import com.demo.edwin.schoolregistration.exception.EnrollmentConstraintException;
import com.demo.edwin.schoolregistration.exception.StudentNotFoundException;
import com.demo.edwin.schoolregistration.repository.CourseRepository;
import com.demo.edwin.schoolregistration.repository.EnrollmentRepository;
import com.demo.edwin.schoolregistration.repository.StudentRepository;
import com.demo.edwin.schoolregistration.request.EnrollDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Value("${app.maxEnrollment.studentsByCourse}")
    private int maxStudentsByCourse;

    @Value("${app.maxEnrollment.coursesByStudent}")
    private int maxCoursesByStudent;

    @Transactional
    public void enrollStudent(EnrollDTO enrollDTO) throws EnrollmentAlreadyExistException, EnrollmentConstraintException, StudentNotFoundException, CourseNotFoundException {
        Optional<Student> optionalStudent = studentRepository.findById(enrollDTO.getStudentId());
        Optional<Course> optionalCourse = courseRepository.findById(enrollDTO.getCourseId());

        if (optionalStudent.isPresent() && optionalCourse.isPresent()) {

            Student student = optionalStudent.get();
            Course course = optionalCourse.get();

            EnrollmentKey enrollmentKey = new EnrollmentKey();
            enrollmentKey.setStudentId(student.getId());
            enrollmentKey.setCourseId(course.getId());

            if (enrollmentRepository.existsById(enrollmentKey)) {
                throw new EnrollmentAlreadyExistException(
                        String.format("Enrollment error, student %d is already registered in Course %d", student.getId(), course.getId()));
            }
            if (student.getRegisteredCourses() < maxCoursesByStudent &&
                    course.getRegisteredStudents() < maxStudentsByCourse) {
                Enrollment enrollment = new Enrollment();

                enrollment.setId(enrollmentKey);
                enrollment.setStudent(student);
                enrollment.setCourse(course);
                enrollmentRepository.save(enrollment);

                student.setRegisteredCourses(student.getRegisteredCourses() + 1);
                course.setRegisteredStudents(course.getRegisteredStudents() + 1);

                studentRepository.save(student);
                courseRepository.save(course);
            } else {
                throw new EnrollmentConstraintException(String.format("Student %s is registered in %d courses. Course %s has %d students",
                        student.getName(), student.getRegisteredCourses(), course.getName(), course.getRegisteredStudents()));
            }
        } else {
            if (optionalStudent.isEmpty()) throw new StudentNotFoundException(enrollDTO.getStudentId());
            if (optionalCourse.isEmpty()) throw new CourseNotFoundException(enrollDTO.getCourseId());
        }
    }

    public List<Course> findAllCoursesByStudent(Long id) {
        return enrollmentRepository.findByStudentId(id).stream().map(Enrollment::getCourse).collect(Collectors.toList());
    }

    public List<Student> findAllStudentsByCourse(Long id) {
        return enrollmentRepository.findByCourseId(id).stream().map(Enrollment::getStudent).collect(Collectors.toList());
    }

    public List<Course> findAllCoursesWithStudentRegisteredSize(Integer registeredStudents) {
        return courseRepository.findByRegisteredStudents(registeredStudents).stream().collect(Collectors.toList());
    }

    public List<Student> findAllStudentsWithCourseRegisteredSize(Integer registeredCourses) {
        return studentRepository.findByRegisteredCourses(registeredCourses).stream().collect(Collectors.toList());
    }

}
