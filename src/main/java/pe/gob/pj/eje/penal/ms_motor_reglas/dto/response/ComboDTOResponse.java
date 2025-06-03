package pe.gob.pj.eje.penal.ms_motor_reglas.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComboDTOResponse {
    private String codigo;
    private String nombre;
}