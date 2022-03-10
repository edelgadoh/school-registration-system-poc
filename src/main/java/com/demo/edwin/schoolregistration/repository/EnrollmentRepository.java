package com.demo.edwin.schoolregistration.repository;

import com.demo.edwin.schoolregistration.entity.Enrollment;
import com.demo.edwin.schoolregistration.entity.EnrollmentKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentKey> {

    List<Enrollment> findByStudentId(Long id);
    List<Enrollment> findByCourseId(Long id);

}
