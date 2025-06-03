package pe.gob.pj.eje.penal.ms_motor_reglas.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.pj.eje.penal.ms_motor_reglas.dto.request.BuilderReglaDTORequest;
import pe.gob.pj.eje.penal.ms_motor_reglas.dto.request.ExpedienteDTORequest;
/**
 * DTO para simulación de reglas
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimulacionReglaDTO {
    private BuilderReglaDTORequest builder;
    private ExpedienteDTORequest expedienteTest;
}