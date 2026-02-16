package mx.com.asteci.service.mapper;

import mx.com.asteci.model.response.UserResponseData;
import mx.com.asteci.repository.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para GetAllUserServiceMapper")
class GetAllUserServiceMapperTest {

    private GetAllUserServiceMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new GetAllUserServiceMapper();
    }

    @Test
    @DisplayName("Debería mapear lista de usuarios correctamente")
    void shouldMapUserListToResponse() {
        // Arrange
        List<User> users = Arrays.asList(
                User.builder().id(1L).name("Chris").email("chris@gmail.com").password("pass1").build(),
                User.builder().id(2L).name("Alex").email("alex@gmail.com").password("pass2").build()
        );
        // Act
        UserResponseData result = mapper.user_entity_list_to_list_response(users);
        // Assert
        assertAll(
                () -> assertEquals("200", result.getStatus().getStatusCode()),
                () -> assertEquals(2, result.getData().size())
        );
    }

    @Test
    @DisplayName("Debería mapear lista vacía correctamente")
    void shouldMapEmptyList() {
        // Act
        UserResponseData result = mapper.user_entity_list_to_list_response(Collections.emptyList());
        // Assert
        assertTrue(result.getData().isEmpty());
    }
}
