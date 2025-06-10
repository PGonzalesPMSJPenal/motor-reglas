package pe.gob.pj.eje.penal.ms_motor_reglas.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DTO para información de operadores disponibles
 * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperadorDTOResponse {
    private String codigo; // EQUALS, NOT_EQUALS, IN, etc.
    private String simbolo; // ==, !=, in, etc.
    private String nombre; // Igual a, Diferente de, Contenido en, etc.
    private String descripcion;
    private Boolean requiereValor;
    private String tipoValor; // texto, numérico, lista, expresión, etc.
    private Boolean permiteNegacion;
}