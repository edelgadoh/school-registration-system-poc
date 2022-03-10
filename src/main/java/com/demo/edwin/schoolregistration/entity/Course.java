package com.demo.edwin.schoolregistration.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String name;

    @Column(name = "registered_students")
    private Integer registeredStudents = 0;

    @JsonIgnore
    @ManyToMany(mappedBy = "enrolledCourses")
    private Set<Student> enrolledStudents;
}
