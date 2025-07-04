package pe.gob.pj.eje.penal.ms_motor_reglas.application.command;
import pe.gob.pj.core.cqrs.command.Command;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.request.CondicionReglaDTORequest;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response.ReglaDroolsDTOResponse;
import java.util.ArrayList;
import java.util.List;
public class CrearReglaCommand implements Command<ReglaDroolsDTOResponse> {
    private Long id;
    private String nombre;
    private String descripcion;
    private String documento;
    private Integer prioridad;
    private String bandejaDestino;
    private List<CondicionReglaDTORequest> condicionesAdicionales = new ArrayList<>();
    private Boolean usarDrlPersonalizado;
    private String drlPersonalizado;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDocumento() {
        return documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    public Integer getPrioridad() {
        return prioridad;
    }
    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }
    public String getBandejaDestino() {
        return bandejaDestino;
    }
    public void setBandejaDestino(String bandejaDestino) {
        this.bandejaDestino = bandejaDestino;
    }
    public List<CondicionReglaDTORequest> getCondicionesAdicionales() {
        return condicionesAdicionales;
    }
    public void setCondicionesAdicionales(List<CondicionReglaDTORequest> condicionesAdicionales) {
        this.condicionesAdicionales = condicionesAdicionales;
    }
    public Boolean getUsarDrlPersonalizado() {
        return usarDrlPersonalizado;
    }
    public void setUsarDrlPersonalizado(Boolean usarDrlPersonalizado) {
        this.usarDrlPersonalizado = usarDrlPersonalizado;
    }
    public String getDrlPersonalizado() {
        return drlPersonalizado;
    }
    public void setDrlPersonalizado(String drlPersonalizado) {
        this.drlPersonalizado = drlPersonalizado;
    }
}