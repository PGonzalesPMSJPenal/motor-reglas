package pe.gob.pj.eje.penal.ms_motor_reglas.application.command.handler;
import org.springframework.stereotype.Component;
import pe.gob.pj.core.cqrs.annotations.CommandHandlerComponent;
import pe.gob.pj.core.cqrs.command.CommandHandler;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.command.CambiarEstadoReglaCommand;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response.ReglaDroolsDTOResponse;
import pe.gob.pj.eje.penal.ms_motor_reglas.domain.service.IConstructorReglaService;
@CommandHandlerComponent
@Component
public class CambiarEstadoReglaCommandHandler implements CommandHandler<CambiarEstadoReglaCommand, ReglaDroolsDTOResponse> {
    private final IConstructorReglaService constructorReglaService;
    public CambiarEstadoReglaCommandHandler(IConstructorReglaService constructorReglaService) {
        this.constructorReglaService = constructorReglaService;
    }
    @Override
    public ReglaDroolsDTOResponse handle(CambiarEstadoReglaCommand command) {
        return constructorReglaService.cambiarEstadoRegla(command.getId(),command.getActivo(),"sistema");
    }
    @Override
    public Class<CambiarEstadoReglaCommand> getCommandClass() {
        return CambiarEstadoReglaCommand.class;
    }
}