package tn.esprit.studentmanagement;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled("Test d'integration : necessite une base MySQL")
@SpringBootTest
class StudentManagementApplicationTests {

    @Test
    void contextLoads() {
        assertNotNull(StudentManagementApplication.class);
    }

}
