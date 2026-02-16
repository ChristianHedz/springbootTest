package mx.com.asteci.service;

import mx.com.asteci.model.request.UserPostRequest;
import mx.com.asteci.model.response.Status;
import mx.com.asteci.model.response.UserPostResponse;
import mx.com.asteci.model.response.UserResponse;
import mx.com.asteci.repository.UserRepository;
import mx.com.asteci.repository.entity.User;
import mx.com.asteci.repository.mapper.PostUserRepositoryMapper;
import mx.com.asteci.service.mapper.PostUserServiceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para PostUserService")
class PostUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostUserRepositoryMapper postUserRepositoryMapper;

    @Mock
    private PostUserServiceMapper postUserServiceMapper;

    @InjectMocks
    private PostUserService service;

    private UserPostRequest request;
    private User user;
    private User savedUser;
    private UserPostResponse response;

    @BeforeEach
    void setUp() {
        request = new UserPostRequest("Chris", "chris@gmail.com", "pass123");
        user = new User(null, "Chris", "chris@gmail.com", "pass123");
        savedUser = new User(1L, "Chris", "chris@gmail.com", "pass123");
        
        Status status = new Status("201", "Usuario creado exitosamente");
        UserResponse userResponse = new UserResponse(1L, "Chris", "chris@gmail.com");
        response = new UserPostResponse(status, userResponse);
    }

    @Test
    @DisplayName("Debería crear un usuario exitosamente")
    void shouldCreateUserSuccessfully() {
        // Arrange
        when(postUserRepositoryMapper.user_post_request_to_entity(request)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(postUserServiceMapper.user_entity_to_post_response(savedUser)).thenReturn(response);
        // Act
        ResponseEntity<UserPostResponse> result = service.post_user_service_handler(request);
        // Assert
        assertAll(
                () -> assertEquals(HttpStatus.CREATED, result.getStatusCode(), "El código de estado debe ser CREATED"),
                () -> assertNotNull(result.getBody(), "El cuerpo de la respuesta no debe ser null"),
                () -> assertEquals(response, result.getBody(), "La respuesta debe coincidir con la esperada")
        );
        verify(userRepository).save(user);
    }

}

