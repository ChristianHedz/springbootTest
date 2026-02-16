package mx.com.asteci.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Respuesta al crear un nuevo usuario")
public class UserPostResponse {

    @JsonProperty("status")
    @Schema(description = "Estado de la operación")
    private Status status;

    @JsonProperty("data")
    @Schema(description = "Datos del usuario creado")
    private UserResponse data;
}

