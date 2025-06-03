package pe.gob.pj.eje.penal.ms_motor_reglas.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComboDTOResponseExpediente {
    private String numeroExpediente;
    private String tipoResolucion;
    private String actoProcesal;
    private String sumilla;
    private String bandejaOrigen;
    private String bandejaSalida;
    public ComboDTOResponseExpediente(String numeroExpediente,String tipoResolucion, String actoProcesal,String sumilla,String bandejaOrigen){
        this.numeroExpediente = numeroExpediente;
        this.tipoResolucion = tipoResolucion;
        this.actoProcesal = actoProcesal;
        this.sumilla = sumilla;
        this.bandejaOrigen = bandejaOrigen;
    }
}