package pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response;
import lombok.Data;
@Data
public class ComboDTOResponseExpediente {
    private Long resolucionId;
    private String tipoResolucion;
    private String actoProcesal;
    private String sumilla;
    private String numeroExpediente;
    private String bandejaOrigen;
    private String bandejaDestino;
    private String archivo;
    private String nombreArchivo;
    private String uuid;
}