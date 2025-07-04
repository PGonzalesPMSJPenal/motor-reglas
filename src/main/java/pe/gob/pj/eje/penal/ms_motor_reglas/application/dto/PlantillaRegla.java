package pe.gob.pj.eje.penal.ms_motor_reglas.application.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.CondicionRegla;

import java.util.ArrayList;
import java.util.List;
/**
 * Modelo que representa una plantilla de regla para generar DRL
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlantillaRegla {
    private String nombre;
    private Integer prioridad;
    private String estado;
    private String bandejaDestino;
    private String identificador;
    @Builder.Default
    private List<CondicionRegla> condicionesAdicionales = new ArrayList<>();
}