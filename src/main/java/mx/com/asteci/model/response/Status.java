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
@Schema(description = "Estado de la respuesta de la operación")
public class Status {

    @JsonProperty("status_code")
    @Schema(description = "Código de estado de la operación", example = "200")
    private String statusCode;

    @JsonProperty("description")
    @Schema(description = "Descripción del estado", example = "Usuario creado exitosamente")
    private String description;
}

