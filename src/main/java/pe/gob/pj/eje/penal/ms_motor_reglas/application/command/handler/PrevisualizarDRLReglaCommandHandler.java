package pe.gob.pj.eje.penal.ms_motor_reglas.application.command.handler;
import pe.gob.pj.core.cqrs.command.CommandHandler;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.command.PrevisualizarDRLReglaCommand;
import pe.gob.pj.eje.penal.ms_motor_reglas.domain.service.IConstructorReglaService;
public class PrevisualizarDRLReglaCommandHandler implements CommandHandler<PrevisualizarDRLReglaCommand,String> {
    private final IConstructorReglaService constructorReglaService;
    public PrevisualizarDRLReglaCommandHandler(IConstructorReglaService constructorReglaService) {
        this.constructorReglaService = constructorReglaService;
    }
    @Override
    public String handle(PrevisualizarDRLReglaCommand command) {
        return constructorReglaService.generarDRLDesdeBuilder(command);
    }
    @Override
    public Class<PrevisualizarDRLReglaCommand> getCommandClass() {
        return PrevisualizarDRLReglaCommand.class;
    }
}