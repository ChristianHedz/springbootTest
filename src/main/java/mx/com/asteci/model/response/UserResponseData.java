package mx.com.asteci.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Respuesta con la lista de todos los usuarios")
public class UserResponseData {

    @JsonProperty("status")
    @Schema(description = "Estado de la operación")
    private Status status;

    @JsonProperty("data")
    @Schema(description = "Lista de usuarios")
    private List<UserResponse> data;
}

