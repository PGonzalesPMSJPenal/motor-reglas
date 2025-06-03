package pe.gob.pj.eje.penal.ms_motor_reglas.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expediente {
    private String id;
    private String uuid;
    private String tipoResolucion;
    private String actoProcesal;
    private String sumilla;
    private String numeroExpediente;
    private String bandejaOrigen;
    // Resultado de la evaluación de reglas
    private String bandejaDestino;
    private Long reglaId;
    private String nombreRegla;
}