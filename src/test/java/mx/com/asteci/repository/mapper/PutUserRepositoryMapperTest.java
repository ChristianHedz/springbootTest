package mx.com.asteci.repository.mapper;

import mx.com.asteci.model.request.UserPutRequest;
import mx.com.asteci.repository.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para PutUserRepositoryMapper")
class PutUserRepositoryMapperTest {

    private PutUserRepositoryMapper mapper;
    private UserPutRequest request;

    @BeforeEach
    void setUp() {
        mapper = new PutUserRepositoryMapper();
        request = UserPutRequest.builder()
                .name("Chris")
                .email("chris@gmail.com")
                .password("newpass")
                .build();
    }

    @Test
    @DisplayName("Debería mapear UserPutRequest a User correctamente")
    void shouldMapUserPutRequestToUser() {
        // Act
        User result = mapper.user_put_request_to_entity(request);
        // Assert
        assertAll(
                () -> assertNull(result.getId(), "El ID debe ser null"),
                () -> assertEquals("Chris", result.getName()),
                () -> assertEquals("chris@gmail.com", result.getEmail()),
                () -> assertEquals("newpass", result.getPassword())
        );
    }
}
