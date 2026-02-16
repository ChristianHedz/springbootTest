package mx.com.asteci.integration;

import mx.com.asteci.exception.ErrorResponse;
import mx.com.asteci.model.request.UserPostRequest;
import mx.com.asteci.model.request.UserPutRequest;
import mx.com.asteci.model.response.UserPostResponse;
import mx.com.asteci.model.response.UserPutResponse;
import mx.com.asteci.model.response.UserResponseData;
import mx.com.asteci.repository.UserRepository;
import mx.com.asteci.repository.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("Tests de integración con TestRestTemplate - Servidor HTTP real")
class UserRestTemplateIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /api/users - Crear usuario con servidor HTTP real")
    void shouldCreateUserSuccessfully() {
        // Arrange
        UserPostRequest request = UserPostRequest.builder()
                .name("Chris")
                .email("chris@gmail.com")
                .password("pass123")
                .build();

        // Act
        ResponseEntity<UserPostResponse> response = restTemplate.postForEntity(
                "/api/users", request, UserPostResponse.class);

        // Assert
        assertAll(
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals("201", response.getBody().getStatus().getStatusCode()),
                () -> assertEquals("Chris", response.getBody().getData().getName()),
                () -> assertEquals("chris@gmail.com", response.getBody().getData().getEmail()));
    }

    @Test
    @DisplayName("GET /api/users - Obtener todos los usuarios con servidor HTTP real")
    void shouldGetAllUsersSuccessfully() {
        // Arrange - Guardamos usuarios directamente en la BD
        userRepository.save(User.builder().name("Chris").email("chris@gmail.com").password("pass123").build());
        userRepository.save(User.builder().name("Alex").email("alex@gmail.com").password("pass456").build());

        // Act
        ResponseEntity<UserResponseData> response = restTemplate.getForEntity(
                "/api/users", UserResponseData.class);

        // Assert
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(2, response.getBody().getData().size()));
    }

    @Test
    @DisplayName("PUT /api/users/{id} - Actualizar usuario con servidor HTTP real")
    void shouldUpdateUserSuccessfully() {
        // Arrange
        User savedUser = userRepository.save(
                User.builder().name("Chris").email("chris@gmail.com").password("pass123").build());

        UserPutRequest updateRequest = UserPutRequest.builder()
                .name("Chris Actualizado")
                .email("chris.new@gmail.com")
                .password("newpass123")
                .build();

        // Act
        ResponseEntity<UserPutResponse> response = restTemplate.exchange(
                "/api/users/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(updateRequest),
                UserPutResponse.class,
                savedUser.getId());

        // Assert
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals("Chris Actualizado", response.getBody().getData().getName()),
                () -> assertEquals("chris.new@gmail.com", response.getBody().getData().getEmail()));
    }

    @Test
    @DisplayName("PUT /api/users/999 - Retorna 404 cuando el usuario no existe")
    void shouldReturn404WhenUserNotFound() {
        // Arrange
        UserPutRequest updateRequest = UserPutRequest.builder()
                .name("No Existe")
                .email("noexiste@gmail.com")
                .password("pass123")
                .build();

        // Act
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                "/api/users/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(updateRequest),
                ErrorResponse.class,
                999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
