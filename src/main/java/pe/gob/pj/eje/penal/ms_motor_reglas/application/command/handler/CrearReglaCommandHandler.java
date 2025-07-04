package pe.gob.pj.eje.penal.ms_motor_reglas.application.command.handler;

import pe.gob.pj.core.cqrs.command.CommandHandler;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.command.CrearReglaCommand;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response.ReglaDroolsDTOResponse;
import pe.gob.pj.eje.penal.ms_motor_reglas.domain.service.IConstructorReglaService;

public class CrearReglaCommandHandler implements CommandHandler<CrearReglaCommand, ReglaDroolsDTOResponse> {
    private final IConstructorReglaService constructorReglaService;

    public CrearReglaCommandHandler(IConstructorReglaService constructorReglaService) {
        this.constructorReglaService = constructorReglaService;
    }

    @Override
    public ReglaDroolsDTOResponse handle(CrearReglaCommand command) {
        return constructorReglaService.crearReglaDesdeBuilder(command, "sistema");
    }

    @Override
    public Class<CrearReglaCommand> getCommandClass() {
        return CrearReglaCommand.class;
    }
}
