package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    private StudentRepository studentRepository;

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }
    @Test
    public void testGetStudent() throws Exception{
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotNull();
    }
    @Test
    public void testCreateStudent() throws Exception{
        Student student = new Student();
        student.setId(1L);
        student.setName("DefaultStudent");
        student.setAge(30);
        Assertions.assertThat(this.restTemplate.
                postForObject("http://localhost:" + port + "/student",student, String.class)).isNotNull();
    }
    @Test
    public void testUpdateStudent() throws Exception{
        Student oldStudent = new Student();
        oldStudent.setId(1L);
        oldStudent.setName("OldStudent");
        oldStudent.setAge(50);
        Student newStudent = new Student();
        newStudent.setId(2L);
        newStudent.setAge(25);
        newStudent.setName("NewStudent");
        this.restTemplate.postForObject("http://localhost:" + port + "/student",oldStudent, String.class);
        this.restTemplate.put("http://localhost:" + port + "/student",newStudent);
        Assertions.assertThat((
                this.restTemplate.getForObject("http://localhost:" + port + "/student", Student.class))
                .equals(newStudent));
    }
    @Test
    public void testDeleteStudent() throws Exception{
        Student deletedStudent = new Student();
        deletedStudent.setAge(35);
        deletedStudent.setName("DeleteMe!");
        deletedStudent.setId(1L);
        this.restTemplate.postForObject("http://localhost:" + port + "/student", deletedStudent ,Student.class);
        this.restTemplate.delete("http://localhost:" + port + "/student" + "/34");
    }
    @Test
    void deleteStudent() {

        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange("/student/222", HttpMethod.DELETE, entity, String.class);
        Mockito.verify(studentRepository, Mockito.times(1)).deleteById(36L);
    }
}
