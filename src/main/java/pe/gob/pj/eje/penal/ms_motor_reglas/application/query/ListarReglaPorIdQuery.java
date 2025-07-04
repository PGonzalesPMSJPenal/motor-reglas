package pe.gob.pj.eje.penal.ms_motor_reglas.application.query;
import pe.gob.pj.core.cqrs.query.Query;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response.BuilderReglaDTOResponse;
public class ListarReglaPorIdQuery implements Query<BuilderReglaDTOResponse> {
    public ListarReglaPorIdQuery(Long id) {
        this.id = id;
    }
    private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}