package tn.esprit.studentmanagement.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Student;
import tn.esprit.studentmanagement.repositories.StudentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setIdStudent(1L);
        student.setFirstName("Amira");
        student.setLastName("Ben Salah");
        student.setEmail("amira.bensalah@esprit.tn");
        student.setPhone("21600000");
        student.setDateOfBirth(LocalDate.of(2000, 1, 15));
        student.setAddress("Tunis");
    }

    @Test
    void getAllStudents_retourneLaListeDuRepository() {
        when(studentRepository.findAll()).thenReturn(List.of(student));

        List<Student> result = studentService.getAllStudents();

        assertEquals(1, result.size());
        assertEquals("NomFaux", result.get(0).getFirstName());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getAllStudents_sansEtudiant_retourneListeVide() {
        when(studentRepository.findAll()).thenReturn(List.of());

        List<Student> result = studentService.getAllStudents();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getStudentById_retourneLEtudiant() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student result = studentService.getStudentById(1L);

        assertEquals(student, result);
        assertEquals("Ben Salah", result.getLastName());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void getStudentById_inexistant_retourneNull() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        Student result = studentService.getStudentById(99L);

        assertNull(result);
        verify(studentRepository, times(1)).findById(99L);
    }

    @Test
    void saveStudent_retourneLEtudiantSauvegarde() {
        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.saveStudent(student);

        assertSame(student, result);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void deleteStudent_appelleLeRepository() {
        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }
}
