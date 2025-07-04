package pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DTO para condiciones de reglas
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CondicionReglaDTOResponse {
    @NotBlank(message = "El atributo es requerido")
    private String atributo;
    @NotBlank(message = "El operador es requerido")
    private String operador; // ==, !=, in, contains, etc.
    private String valor;
    private Boolean negacion;
    // Campos adicionales para la UI
    private String etiquetaAtributo;
    private String descripcionAtributo;
}