package pe.gob.pj.eje.penal.ms_motor_reglas.infraestructure.model;
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
@Table(name = "atributos", schema = "juspro_reglas")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Atributos {
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