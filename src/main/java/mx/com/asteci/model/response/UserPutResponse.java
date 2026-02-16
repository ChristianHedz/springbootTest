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
@Schema(description = "Respuesta al actualizar un usuario")
public class UserPutResponse {

    @JsonProperty("status")
    @Schema(description = "Estado de la operación")
    private Status status;

    @JsonProperty("data")
    @Schema(description = "Datos del usuario actualizado")
    private UserResponse data;
}

