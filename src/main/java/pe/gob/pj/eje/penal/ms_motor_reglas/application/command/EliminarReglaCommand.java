package pe.gob.pj.eje.penal.ms_motor_reglas.application.command;
import pe.gob.pj.core.cqrs.command.Command;
import pe.gob.pj.core.cqrs.query.Query;
public class EliminarReglaCommand implements Command<Boolean> {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;
}