package pe.gob.pj.eje.penal.ms_motor_reglas.application.command.handler;
import org.springframework.stereotype.Component;
import pe.gob.pj.core.cqrs.annotations.CommandHandlerComponent;
import pe.gob.pj.core.cqrs.command.CommandHandler;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.command.SimularReglaCommand;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.ResultadoSimulacionDTO;
import pe.gob.pj.eje.penal.ms_motor_reglas.domain.service.IConstructorReglaService;
@CommandHandlerComponent
@Component
public class SimularReglaCommandHandler implements CommandHandler<SimularReglaCommand, ResultadoSimulacionDTO> {
    private final IConstructorReglaService constructorReglaService;

    public SimularReglaCommandHandler(IConstructorReglaService constructorReglaService) {
        this.constructorReglaService = constructorReglaService;
    }

    @Override
    public ResultadoSimulacionDTO handle(SimularReglaCommand command) {
        return constructorReglaService.simularRegla(command);
    }

    @Override
    public Class<SimularReglaCommand> getCommandClass() {
        return SimularReglaCommand.class;
    }
}