package pe.gob.pj.eje.penal.ms_motor_reglas.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DTO para resultado de simulación de reglas
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoSimulacionDTO {
    private Boolean reglaAplicada;
    private String bandejaResultante;
    private String drlGenerado;
    private String mensaje;
    private Boolean tieneErrores;
    private String errores;
}