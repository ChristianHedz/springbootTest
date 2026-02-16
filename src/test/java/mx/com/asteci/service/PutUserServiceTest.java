package mx.com.asteci.service;

import mx.com.asteci.exception.ResourceNotFoundException;
import mx.com.asteci.model.request.UserPutRequest;
import mx.com.asteci.model.response.Status;
import mx.com.asteci.model.response.UserPutResponse;
import mx.com.asteci.model.response.UserResponse;
import mx.com.asteci.repository.UserRepository;
import mx.com.asteci.repository.entity.User;
import mx.com.asteci.repository.mapper.PutUserRepositoryMapper;
import mx.com.asteci.service.mapper.PutUserServiceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para PutUserService")
class PutUserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PutUserRepositoryMapper putUserRepositoryMapper;
    @Mock
    private PutUserServiceMapper putUserServiceMapper;
    @InjectMocks
    private PutUserService service;

    private UserPutRequest request;
    private User userDetails;
    private User existingUser;
    private UserPutResponse response;

    @BeforeEach
    void setUp() {
        request = new UserPutRequest("Chris", "chris@gmail.com", "newpass");
        userDetails = new User(null, "Chris", "chris@gmail.com", "newpass");
        existingUser = new User(1L, "Chris", "chris@gmail.com", "oldpass");
        
        Status status = new Status("200", "Usuario actualizado exitosamente");
        UserResponse userResponse = new UserResponse(1L, "Chris", "chris@gmail.com");
        response = new UserPutResponse(status, userResponse);
    }

    @Test
    @DisplayName("Debería actualizar un usuario exitosamente")
    void shouldUpdateUserSuccessfully() {
        // Arrange
        when(putUserRepositoryMapper.user_put_request_to_entity(request)).thenReturn(userDetails);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);
        when(putUserServiceMapper.user_entity_to_put_response(existingUser)).thenReturn(response);
        // Act
        ResponseEntity<UserPutResponse> result = service.put_user_service_handler(1L, request);
        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(userRepository).save(existingUser);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el usuario no existe")
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(putUserRepositoryMapper.user_put_request_to_entity(request)).thenReturn(userDetails);
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, 
                () -> service.put_user_service_handler(999L, request));
        verify(userRepository, never()).save(any());
    }
}
