package com.demo.edwin.schoolregistration.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "course_student_enrollment")
@Getter
@Setter
@ToString
public class Enrollment {

    @EmbeddedId
    EnrollmentKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    Course course;


}
