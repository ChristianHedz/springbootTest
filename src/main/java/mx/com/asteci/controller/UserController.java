package mx.com.asteci.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mx.com.asteci.model.request.UserPostRequest;
import mx.com.asteci.model.request.UserPutRequest;
import mx.com.asteci.model.response.UserPostResponse;
import mx.com.asteci.model.response.UserPutResponse;
import mx.com.asteci.model.response.UserResponse;
import mx.com.asteci.model.response.UserResponseData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Management", description = "API para la gestión de usuarios")
@RequestMapping("/api/users")
public interface UserController {

    @Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario en el sistema con los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserPostResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content(mediaType = "application/json"))})
    @PostMapping
    ResponseEntity<UserPostResponse> create(
            @Parameter(in = ParameterIn.DEFAULT, description = "Datos del usuario a crear", required = true, schema = @Schema(implementation = UserPostRequest.class))
            @Valid @RequestBody UserPostRequest request
    );


    @Operation(summary = "Obtener todos los usuarios", description = "Recupera una lista de todos los usuarios registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios recuperada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseData.class)))})
    @GetMapping
    ResponseEntity<UserResponseData> getAll();


    @Operation(summary = "Actualizar un usuario", description = "Actualiza los datos de un usuario existente utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserPutResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content(mediaType = "application/json"))})
    @PutMapping("/{id}")
    ResponseEntity<UserPutResponse> update(
            @Parameter(in = ParameterIn.PATH, description = "ID del usuario a actualizar", required = true, schema = @Schema(type = "integer", format = "int64"))
            @PathVariable Long id,
            @Parameter(in = ParameterIn.DEFAULT, description = "Datos actualizados del usuario", required = true, schema = @Schema(implementation = UserPutRequest.class))
            @Valid @RequestBody UserPutRequest request
    );

}
