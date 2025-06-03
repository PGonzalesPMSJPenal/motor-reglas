package pe.gob.pj.eje.penal.ms_motor_reglas.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpedienteDTOResponse {
    private Long id;
    private String numeroExpediente;
    private String tipoResolucion;
    private String actoProcesal;
    private String sumilla;
    private String bandejaOrigen;
    private String bandejaDestino;
}