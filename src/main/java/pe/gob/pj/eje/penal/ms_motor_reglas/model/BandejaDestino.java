package pe.gob.pj.eje.penal.ms_motor_reglas.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "bandeja", schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BandejaDestino {
    @Id
    @Column(name = "codigo")
    private String id;
    @NotNull
    @Column(name = "nombre")
    private String nombre;
    @NotNull
    @Column(name = "descripcion")
    private String descripcion;
    @NotNull
    @Column(name = "activo")
    private Boolean activo;
}