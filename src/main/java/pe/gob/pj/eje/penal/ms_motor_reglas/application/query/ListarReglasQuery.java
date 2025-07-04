package pe.gob.pj.eje.penal.ms_motor_reglas.application.query;
import pe.gob.pj.core.cqrs.query.Query;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response.ReglaDroolsDTOResponse;
import java.util.List;
public class ListarReglasQuery implements Query<List<ReglaDroolsDTOResponse>> {
    public ListarReglasQuery(String nombre) {
        this.nombre = nombre;
    }

    private String nombre;
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}