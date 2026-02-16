package mx.com.asteci.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.com.asteci.model.request.UserPostRequest;
import mx.com.asteci.model.request.UserPutRequest;
import mx.com.asteci.exception.ResourceNotFoundException;
import mx.com.asteci.model.response.*;
import mx.com.asteci.service.GetAllUserService;
import mx.com.asteci.service.PostUserService;
import mx.com.asteci.service.PutUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserControllerImpl.class)
@DisplayName("Tests de integración para UserController")
class UserControllerImplTest {

        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private PostUserService postUserService;
        @MockBean
        private GetAllUserService getAllUserService;
        @MockBean
        private PutUserService putUserService;
        @Autowired
        private ObjectMapper objectMapper;
        private UserPostRequest postRequest;
        private UserPutRequest putRequest;

        @BeforeEach
        void setUp() {
                postRequest = UserPostRequest.builder()
                                .name("Chris")
                                .email("chris@gmail.com")
                                .password("pass123")
                                .build();

                putRequest = UserPutRequest.builder()
                                .name("Chris actualizado")
                                .email("chris.new@gmail.com")
                                .password("newpass")
                                .build();
        }

        @Test
        @DisplayName("POST /api/users - Debería crear un usuario exitosamente")
        void shouldCreateUserSuccessfully() throws Exception {
                // Arrange
                UserPostResponse response = new UserPostResponse(
                                new Status("201", "Usuario creado exitosamente"),
                                new UserResponse(1L, "Chris", "chris@gmail.com"));
                when(postUserService.post_user_service_handler(any()))
                                .thenReturn(ResponseEntity.status(201).body(response));
                // Act & Assert
                mockMvc.perform(post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postRequest)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.status.status_code").value("201"))
                                .andExpect(jsonPath("$.data.name").value("Chris"));
        }

        @Test
        @DisplayName("POST /api/users - Debería retornar 400 cuando el nombre está vacío")
        void shouldReturn400WhenNameIsBlank() throws Exception {
                // Arrange - Creamos un request con nombre vacío para que falle la validación
                // @NotBlank
                UserPostRequest invalidRequest = UserPostRequest.builder()
                                .name("")
                                .email("chris@gmail.com")
                                .password("pass123")
                                .build();
                // Act & Assert
                mockMvc.perform(post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidRequest)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("GET /api/users - Debería obtener todos los usuarios")
        void shouldGetAllUsersSuccessfully() throws Exception {
                // Arrange
                List<UserResponse> users = Arrays.asList(
                                new UserResponse(1L, "Chris", "chris@gmail.com"),
                                new UserResponse(2L, "Alex", "alex@gmail.com"));
                UserResponseData response = new UserResponseData(
                                new Status("200", "Usuarios obtenidos exitosamente"),
                                users);
                when(getAllUserService.get_all_user_service_handler()).thenReturn(ResponseEntity.ok(response));
                // Act & Assert
                mockMvc.perform(get("/api/users"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status.status_code").value("200"))
                                .andExpect(jsonPath("$.data").isArray());
        }

        @Test
        @DisplayName("PUT /api/users/{id} - Debería actualizar un usuario")
        void shouldUpdateUserSuccessfully() throws Exception {
                // Arrange
                UserPutResponse response = new UserPutResponse(
                                new Status("200", "Usuario actualizado exitosamente"),
                                new UserResponse(1L, "Chris actualizado", "chris.new@gmail.com"));
                when(putUserService.put_user_service_handler(eq(1L), any())).thenReturn(ResponseEntity.ok(response));
                // Act & Assert
                mockMvc.perform(put("/api/users/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(putRequest)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status.status_code").value("200"))
                                .andExpect(jsonPath("$.data.name").value("Chris actualizado"));
        }

        @Test
        @DisplayName("PUT /api/users/{id} - Debería retornar 404 cuando el usuario no existe")
        void shouldReturn404WhenUserNotFound() throws Exception {
                // Arrange - El servicio lanza ResourceNotFoundException cuando no encuentra el
                // usuario
                when(putUserService.put_user_service_handler(eq(999L), any()))
                                .thenThrow(new ResourceNotFoundException("Usuario no encontrado con id: 999"));
                // Act & Assert
                mockMvc.perform(put("/api/users/{id}", 999L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(putRequest)))
                                .andExpect(status().isNotFound());
        }
}
