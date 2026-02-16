package mx.com.asteci.service.mapper;

import mx.com.asteci.model.response.UserPutResponse;
import mx.com.asteci.repository.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para PutUserServiceMapper")
class PutUserServiceMapperTest {

    private PutUserServiceMapper mapper;
    private User user;

    @BeforeEach
    void setUp() {
        mapper = new PutUserServiceMapper();
        user = User.builder()
                .id(1L)
                .name("Chris")
                .email("chris@gmail.com")
                .password("newpass")
                .build();
    }

    @Test
    @DisplayName("Debería mapear User a UserPutResponse correctamente")
    void shouldMapUserToPutResponse() {
        // Act
        UserPutResponse result = mapper.user_entity_to_put_response(user);
        // Assert
        assertAll(
                () -> assertEquals("200", result.getStatus().getStatusCode()),
                () -> assertEquals(1L, result.getData().getId()),
                () -> assertEquals("Chris", result.getData().getName()),
                () -> assertEquals("chris@gmail.com", result.getData().getEmail())
        );
    }
}
