package com.demo.edwin.schoolregistration.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EnrollDTO {
    private Long courseId;
    private Long studentId;
}
