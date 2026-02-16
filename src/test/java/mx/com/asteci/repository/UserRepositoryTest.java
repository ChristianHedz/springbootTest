package mx.com.asteci.repository;

import mx.com.asteci.repository.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Tests de integración para UserRepository")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        testUser = User.builder()
                .name("Chris")
                .email("chris@gmail.com")
                .password("pass123")
                .build();
    }

    @Test
    @DisplayName("Debería encontrar un usuario por email cuando existe")
    void shouldFindUserByEmail() {
        // Arrange
        userRepository.save(testUser);
        // Act
        Optional<User> result = userRepository.findByEmail("chris@gmail.com");
        // Assert
        assertTrue(result.isPresent());
        assertEquals("chris@gmail.com", result.get().getEmail());
    }

    @Test
    @DisplayName("Debería retornar Optional vacío cuando el email no existe")
    void shouldReturnEmptyWhenEmailNotExists() {
        // Act
        Optional<User> result = userRepository.findByEmail("noexiste@gmail.com");
        // Assert
        assertFalse(result.isPresent());
    }
}
