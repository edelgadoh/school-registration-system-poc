package com.demo.edwin.schoolregistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class AbstractFunctionalTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Value("${app.maxEnrollment.studentsByCourse}")
    protected int maxStudentsAllowedByCourse;

    @Value("${app.maxEnrollment.coursesByStudent}")
    protected int maxCoursesAllowedByStudent;

}
