package pe.gob.pj.eje.penal.ms_motor_reglas.application.command.handler;
import org.springframework.stereotype.Component;
import pe.gob.pj.core.cqrs.annotations.CommandHandlerComponent;
import pe.gob.pj.core.cqrs.command.CommandHandler;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.command.EvaluarExpedienteCommand;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response.ResultadoEvaluacionDTOResponse;
import pe.gob.pj.eje.penal.ms_motor_reglas.domain.service.IConstructorReglaService;
@CommandHandlerComponent
@Component
public class EvaluarExpedienteCommandHandler implements CommandHandler<EvaluarExpedienteCommand, ResultadoEvaluacionDTOResponse> {
    private final IConstructorReglaService constructorReglaService;

    public EvaluarExpedienteCommandHandler(IConstructorReglaService constructorReglaService) {
        this.constructorReglaService = constructorReglaService;
    }

    @Override
    public ResultadoEvaluacionDTOResponse handle(EvaluarExpedienteCommand command) {
        return constructorReglaService.evaluarExpediente(command);
    }
    @Override
    public Class<EvaluarExpedienteCommand> getCommandClass() {
        return EvaluarExpedienteCommand.class;
    }
}