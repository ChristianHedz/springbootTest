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
@Schema(description = "Objeto de solicitud para actualizar un usuario existente")
public class UserPutRequest {

    @NotBlank(message = "{user.name.notblank}")
    @Size(min = 2, max = 50, message = "{user.name.size}")
    @JsonProperty("name")
    @Schema(description = "Nombre actualizado del usuario", example = "Juan Pérez Actualizado", required = true)
    private String name;

    @NotBlank(message = "{user.email.notblank}")
    @Email(message = "{user.email.format}")
    @JsonProperty("email")
    @Schema(description = "Correo electrónico actualizado del usuario", example = "juan.actualizado@example.com", required = true)
    private String email;

    @NotBlank(message = "{user.password.notblank}")
    @Size(min = 6, message = "{user.password.size}")
    @JsonProperty("password")
    @Schema(description = "Contraseña actualizada del usuario (mínimo 6 caracteres)", example = "newpassword123", required = true)
    private String password;
}

