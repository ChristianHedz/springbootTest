package mx.com.asteci.service.mapper;

import mx.com.asteci.model.response.UserPostResponse;
import mx.com.asteci.repository.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para PostUserServiceMapper")
class PostUserServiceMapperTest {

    private PostUserServiceMapper mapper;
    private User user;

    @BeforeEach
    void setUp() {
        mapper = new PostUserServiceMapper();
        user = User.builder()
                .id(1L)
                .name("Chris")
                .email("chris@gmail.com")
                .password("pass123")
                .build();
    }

    @Test
    @DisplayName("Debería mapear User a UserPostResponse correctamente")
    void shouldMapUserToPostResponse() {
        // Act
        UserPostResponse result = mapper.user_entity_to_post_response(user);
        // Assert
        assertAll(
                () -> assertEquals("201", result.getStatus().getStatusCode()),
                () -> assertEquals(1L, result.getData().getId()),
                () -> assertEquals("Chris", result.getData().getName()),
                () -> assertEquals("chris@gmail.com", result.getData().getEmail())
        );
    }
}
