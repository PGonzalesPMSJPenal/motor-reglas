package pe.gob.pj.eje.penal.ms_motor_reglas.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Modelo que representa una condición para una regla
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CondicionRegla {
    private String atributo;
    private String operador; // ==, !=, in, not in, etc.
    private String valor;
}