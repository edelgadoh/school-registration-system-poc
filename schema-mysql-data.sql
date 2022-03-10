drop table if exists course_student_enrollment;
drop table if exists course;
drop table if exists student;

create table course (
    ID BIGINT NOT NULL AUTO_INCREMENT,
    name varchar(255) not null,
    registered_students int not null,
    PRIMARY KEY (ID)
);

INSERT INTO course (id, name, registered_students)  VALUES (1, 'Java', 0);
INSERT INTO course (id, name , registered_students) VALUES (2, 'Python', 0);
INSERT INTO course (id, name , registered_students) VALUES (3, 'Spring Boot', 0);
INSERT INTO course (id, name , registered_students) VALUES (4, 'AWS', 0);
INSERT INTO course (id, name , registered_students) VALUES (5, 'MongoDB', 0);
INSERT INTO course (id, name , registered_students) VALUES (6, 'Ansible', 0);
INSERT INTO course (id, name , registered_students) VALUES (7, 'Terraform', 0);


create table student (
    ID BIGINT NOT NULL AUTO_INCREMENT,
    name varchar(255) not null,
    age int not null,
    registered_courses int not null,
    PRIMARY KEY (ID)
);

INSERT INTO student (id, name, age, registered_courses) VALUES (1, 'Student001', 25, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (2, 'Student002', 24, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (3, 'Student003', 24, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (4, 'Student004', 24, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (5, 'Student005', 19, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (6, 'Student006', 35, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (7, 'Student007', 45, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (8, 'Student008', 15, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (9, 'Student009', 25, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (10, 'Student010', 17, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (11, 'Student011', 25, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (12, 'Student012', 24, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (13, 'Student013', 24, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (14, 'Student014', 24, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (15, 'Student015', 19, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (16, 'Student016', 35, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (17, 'Student017', 45, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (18, 'Student018', 15, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (19, 'Student019', 25, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (20, 'Student020', 17, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (21, 'Student021', 25, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (22, 'Student022', 24, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (23, 'Student023', 24, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (24, 'Student024', 24, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (25, 'Student025', 19, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (26, 'Student026', 35, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (27, 'Student027', 45, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (28, 'Student028', 15, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (29, 'Student029', 25, 0);
INSERT INTO student (id, name, age, registered_courses) VALUES (30, 'Student030', 17, 0);


create table if not exists course_student_enrollment (
    student_id BIGINT not null,
    course_id BIGINT not null,
    foreign key (student_id) references student(id),
    foreign key (course_id) references course(id),
    Constraint PK_Course_Student Primary Key (student_id, course_id)
);

