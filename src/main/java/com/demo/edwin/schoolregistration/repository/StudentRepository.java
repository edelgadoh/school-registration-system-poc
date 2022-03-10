package com.demo.edwin.schoolregistration.repository;

import com.demo.edwin.schoolregistration.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByNameIgnoreCase(String name);

    List<Student> findByRegisteredCourses(Integer value);
}
