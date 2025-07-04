package pe.gob.pj.eje.penal.ms_motor_reglas.application.command.handler;
import pe.gob.pj.core.cqrs.command.CommandHandler;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.command.ActualizarReglaCommand;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response.ReglaDroolsDTOResponse;
import pe.gob.pj.eje.penal.ms_motor_reglas.domain.service.IConstructorReglaService;
public class ActualizarReglaCommandHandler implements CommandHandler<ActualizarReglaCommand, ReglaDroolsDTOResponse> {
    private final IConstructorReglaService constructorReglaService;

    public ActualizarReglaCommandHandler(IConstructorReglaService constructorReglaService) {
        this.constructorReglaService = constructorReglaService;
    }

    @Override
    public ReglaDroolsDTOResponse handle(ActualizarReglaCommand command) {
        return constructorReglaService.actualizarReglaDesdeBuilder(command,"sistema");
    }

    @Override
    public Class<ActualizarReglaCommand> getCommandClass() {
        return ActualizarReglaCommand.class;
    }
}
