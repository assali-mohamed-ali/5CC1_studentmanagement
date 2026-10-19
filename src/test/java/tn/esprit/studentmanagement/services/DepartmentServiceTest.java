package tn.esprit.studentmanagement.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.repositories.DepartmentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setIdDepartment(1L);
        department.setName("Informatique");
        department.setLocation("Tunis");
        department.setPhone("71000000");
        department.setHead("M. Trabelsi");
    }

    @Test
    void getAllDepartments_retourneLaListeDuRepository() {
        when(departmentRepository.findAll()).thenReturn(List.of(department));

        List<Department> result = departmentService.getAllDepartments();

        assertEquals(1, result.size());
        assertEquals("Informatique", result.get(0).getName());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void getDepartmentById_retourneLeDepartement() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        Department result = departmentService.getDepartmentById(1L);

        assertEquals(department, result);
        assertEquals("Tunis", result.getLocation());
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    void getDepartmentById_inexistant_retourneNull() {
        when(departmentRepository.findById(99L)).thenReturn(Optional.empty());

        Department result = departmentService.getDepartmentById(99L);

        assertNull(result);
        verify(departmentRepository, times(1)).findById(99L);
    }

    @Test
    void saveDepartment_retourneLeDepartementSauvegarde() {
        when(departmentRepository.save(department)).thenReturn(department);

        Department result = departmentService.saveDepartment(department);

        assertSame(department, result);
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    void deleteDepartment_appelleLeRepository() {
        departmentService.deleteDepartment(1L);

        verify(departmentRepository, times(1)).deleteById(1L);
    }
}
