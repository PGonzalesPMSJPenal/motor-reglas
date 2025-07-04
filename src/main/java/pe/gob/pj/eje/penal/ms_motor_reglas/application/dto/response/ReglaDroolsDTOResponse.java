package pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReglaDroolsDTOResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private String documento;
    private Integer prioridad;
    private String reglaDrl; // Cambiar el tipo a Blob
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private String usuarioCreacion;
    private String usuarioModificacion;
    private Boolean activo;
    private Integer version;
    public ReglaDroolsDTOResponse(Long id, String nombre, String descripcion, String documento, Integer prioridad, String reglaDrl, String usuarioCreacion, LocalDateTime fechaCreacion, Boolean activo, Integer version) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.documento = documento;
        this.prioridad = prioridad;
        this.reglaDrl = reglaDrl;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.activo = activo;
        this.version = version;
    }
}