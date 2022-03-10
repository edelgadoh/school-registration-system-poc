package com.demo.edwin.schoolregistration.repository;

import com.demo.edwin.schoolregistration.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByNameIgnoreCase(String name);
    List<Course> findByRegisteredStudents(Integer value);
}
