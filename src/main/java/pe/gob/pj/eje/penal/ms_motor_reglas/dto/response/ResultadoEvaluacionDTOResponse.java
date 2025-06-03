package pe.gob.pj.eje.penal.ms_motor_reglas.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoEvaluacionDTOResponse {
    private Boolean exito;
    private String bandejaDestino;
    private Long reglaId;
    private String nombreRegla;
    private String mensaje;
}