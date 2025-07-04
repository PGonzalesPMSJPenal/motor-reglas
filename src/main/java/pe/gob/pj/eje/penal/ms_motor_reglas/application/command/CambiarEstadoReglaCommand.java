package pe.gob.pj.eje.penal.ms_motor_reglas.application.command;
import pe.gob.pj.core.cqrs.command.Command;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response.ReglaDroolsDTOResponse;
public class CambiarEstadoReglaCommand implements Command<ReglaDroolsDTOResponse> {
    private Long id;
    private Boolean activo;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}