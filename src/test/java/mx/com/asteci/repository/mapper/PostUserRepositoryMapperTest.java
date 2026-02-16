package mx.com.asteci.repository.mapper;

import mx.com.asteci.model.request.UserPostRequest;
import mx.com.asteci.repository.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para PostUserRepositoryMapper")
class PostUserRepositoryMapperTest {

    private PostUserRepositoryMapper mapper;
    private UserPostRequest request;

    @BeforeEach
    void setUp() {
        mapper = new PostUserRepositoryMapper();
        request = UserPostRequest.builder()
                .name("Chris")
                .email("chris@gmail.com")
                .password("pass123")
                .build();
    }

    @Test
    @DisplayName("Debería mapear UserPostRequest a User correctamente")
    void shouldMapUserPostRequestToUser() {
        // Act
        User result = mapper.user_post_request_to_entity(request);
        // Assert
        assertAll(
                () -> assertNull(result.getId(), "El ID debe ser null"),
                () -> assertEquals("Chris", result.getName()),
                () -> assertEquals("chris@gmail.com", result.getEmail()),
                () -> assertEquals("pass123", result.getPassword())
        );
    }
}
