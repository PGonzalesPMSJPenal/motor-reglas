package pe.gob.pj.eje.penal.ms_motor_reglas.application.command;
import pe.gob.pj.core.cqrs.command.Command;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.ResultadoSimulacionDTO;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.request.BuilderReglaDTORequest;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.request.ExpedienteDTORequest;
public class SimularReglaCommand implements Command<ResultadoSimulacionDTO> {
    public BuilderReglaDTORequest getBuilder() {
        return builder;
    }

    public void setBuilder(BuilderReglaDTORequest builder) {
        this.builder = builder;
    }

    public ExpedienteDTORequest getExpedienteTest() {
        return expedienteTest;
    }

    public void setExpedienteTest(ExpedienteDTORequest expedienteTest) {
        this.expedienteTest = expedienteTest;
    }

    private BuilderReglaDTORequest builder;
    private ExpedienteDTORequest expedienteTest;
}