package mx.com.asteci.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto de solicitud para crear un nuevo usuario")
public class UserPostRequest {

    @NotBlank(message = "{user.name.notblank}")
    @Size(min = 2, max = 50, message = "{user.name.size}")
    @JsonProperty("name")
    @Schema(description = "Nombre del usuario", example = "Juan Pérez", required = true)
    private String name;

    @NotBlank(message = "{user.email.notblank}")
    @Email(message = "{user.email.format}")
    @JsonProperty("email")
    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@example.com", required = true)
    private String email;

    @NotBlank(message = "{user.password.notblank}")
    @Size(min = 6, message = "{user.password.size}")
    @JsonProperty("password")
    @Schema(description = "Contraseña del usuario (mínimo 6 caracteres)", example = "password123", required = true)
    private String password;
}

