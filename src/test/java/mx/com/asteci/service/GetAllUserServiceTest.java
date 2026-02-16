package mx.com.asteci.service;

import mx.com.asteci.model.response.Status;
import mx.com.asteci.model.response.UserResponse;
import mx.com.asteci.model.response.UserResponseData;
import mx.com.asteci.repository.UserRepository;
import mx.com.asteci.repository.entity.User;
import mx.com.asteci.service.mapper.GetAllUserServiceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para GetAllUserService")
class GetAllUserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private GetAllUserServiceMapper getAllUserServiceMapper;
    @InjectMocks
    private GetAllUserService service;
    private List<User> users;
    private UserResponseData responseWithData;
    private UserResponseData responseEmpty;

    @BeforeEach
    void setUp() {
        User user1 = new User(1L, "Chris", "chris@gmail.com", "pass1");
        User user2 = new User(2L, "Alex", "alex@gmail.com", "pass2");
        users = Arrays.asList(user1, user2);

        Status status = new Status("200", "Usuarios obtenidos exitosamente");
        List<UserResponse> userResponses = Arrays.asList(
                new UserResponse(1L, "Chris", "chris@gmail.com"),
                new UserResponse(2L, "Alex", "alex@gmail.com")
        );
        responseWithData = new UserResponseData(status, userResponses);
        responseEmpty = new UserResponseData(status, Collections.emptyList());
    }

    @Test
    @DisplayName("Debería obtener todos los usuarios exitosamente")
    void shouldGetAllUsersSuccessfully() {
        // Arrange
        when(userRepository.findAll()).thenReturn(users);
        when(getAllUserServiceMapper.user_entity_list_to_list_response(users)).thenReturn(responseWithData);
        // Act
        ResponseEntity<UserResponseData> result = service.get_all_user_service_handler();
        // Assert
        assertAll(
                () -> assertEquals(HttpStatus.OK, result.getStatusCode(), "El código de estado debe ser OK"),
                () -> assertNotNull(result.getBody(), "El cuerpo de la respuesta no debe ser null"),
                () -> assertEquals(2, result.getBody().getData().size(), "Debe haber 2 usuarios en la respuesta")
        );
        verify(userRepository).findAll();
        verify(getAllUserServiceMapper).user_entity_list_to_list_response(users);
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no hay usuarios")
    void shouldReturnEmptyListWhenNoUsers() {
        // Arrange
        List<User> emptyList = Collections.emptyList();
        when(userRepository.findAll()).thenReturn(emptyList);
        when(getAllUserServiceMapper.user_entity_list_to_list_response(emptyList)).thenReturn(responseEmpty);
        // Act
        ResponseEntity<UserResponseData> result = service.get_all_user_service_handler();
        // Assert
        assertAll(
                () -> assertEquals(HttpStatus.OK, result.getStatusCode(), "El código de estado debe ser OK"),
                () -> assertNotNull(result.getBody(), "El cuerpo de la respuesta no debe ser null"),
                () -> assertTrue(result.getBody().getData().isEmpty(), "La lista debe estar vacía")
        );
        verify(userRepository).findAll();
    }
}

