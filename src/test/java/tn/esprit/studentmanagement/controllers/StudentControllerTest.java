package tn.esprit.studentmanagement.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.studentmanagement.entities.Student;
import tn.esprit.studentmanagement.services.IStudentService;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IStudentService studentService;

    private Student student() {
        Student student = new Student();
        student.setIdStudent(1L);
        student.setFirstName("Amira");
        student.setLastName("Ben Salah");
        student.setEmail("amira@esprit.tn");
        student.setPhone("21600000");
        student.setDateOfBirth(LocalDate.of(2000, 1, 15));
        student.setAddress("Tunis");
        return student;
    }

    @Test
    void getAllStudents_retourneLaListe() throws Exception {
        when(studentService.getAllStudents()).thenReturn(List.of(student()));

        mockMvc.perform(get("/students/getAllStudents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Amira"))
                .andExpect(jsonPath("$[0].email").value("amira@esprit.tn"));
    }

    @Test
    void getStudent_retourneLEtudiant() throws Exception {
        when(studentService.getStudentById(1L)).thenReturn(student());

        mockMvc.perform(get("/students/getStudent/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Ben Salah"));
    }

    @Test
    void getStudentByEmail_retourneLEtudiant() throws Exception {
        when(studentService.getStudentByEmail("amira@esprit.tn")).thenReturn(student());

        mockMvc.perform(get("/students/getStudentByEmail/{email}", "amira@esprit.tn"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Amira"));
    }

    @Test
    void createStudent_retourneLEtudiantCree() throws Exception {
        when(studentService.saveStudent(any(Student.class))).thenReturn(student());

        mockMvc.perform(post("/students/createStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName": "Amira",
                                  "lastName": "Ben Salah",
                                  "email": "amira@esprit.tn",
                                  "phone": "21600000",
                                  "dateOfBirth": "2000-01-15",
                                  "address": "Tunis"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("amira@esprit.tn"));
    }

    @Test
    void deleteStudent_retourneOk() throws Exception {
        mockMvc.perform(delete("/students/deleteStudent/1"))
                .andExpect(status().isOk());
    }
}
