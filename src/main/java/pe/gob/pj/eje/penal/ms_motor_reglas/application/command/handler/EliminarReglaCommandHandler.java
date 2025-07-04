package pe.gob.pj.eje.penal.ms_motor_reglas.application.command.handler;
import pe.gob.pj.core.cqrs.command.CommandHandler;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.command.EliminarReglaCommand;
import pe.gob.pj.eje.penal.ms_motor_reglas.domain.service.IConstructorReglaService;
public class EliminarReglaCommandHandler implements CommandHandler<EliminarReglaCommand,Boolean> {
    private final IConstructorReglaService constructorReglaService;
    public EliminarReglaCommandHandler(IConstructorReglaService constructorReglaService) {
        this.constructorReglaService = constructorReglaService;
    }
    @Override
    public Boolean handle(EliminarReglaCommand command) {
        return constructorReglaService.eliminarRegla(command.getId());
    }
    @Override
    public Class<EliminarReglaCommand> getCommandClass() {
        return EliminarReglaCommand.class;
    }
}