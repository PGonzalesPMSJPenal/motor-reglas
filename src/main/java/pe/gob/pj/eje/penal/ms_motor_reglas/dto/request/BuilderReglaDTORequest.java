package pe.gob.pj.eje.penal.ms_motor_reglas.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
/**
 * DTO para la construcción de reglas desde la interfaz de usuario
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuilderReglaDTORequest {
    private Long id;
    @NotBlank(message = "El nombre de la regla es requerido")
    private String nombre;
    private String descripcion;
    // Atributos principales
    private String documento;
    private Integer prioridad;
    private String bandejaDestino;
    @Builder.Default
    private List<CondicionReglaDTORequest> condicionesAdicionales = new ArrayList<>();
    // Configuración avanzada
    private Boolean usarDrlPersonalizado;
    private String drlPersonalizado;
}