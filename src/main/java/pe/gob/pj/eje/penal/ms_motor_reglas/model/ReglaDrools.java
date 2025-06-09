package pe.gob.pj.eje.penal.ms_motor_reglas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Table(name = "reglas_drools", schema = "juspro_reglas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReglaDrools {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name= "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "documento")
    private String documento;
    // Prioridad de la regla (salience en Drools)
    @Column(name = "prioridad")
    private Integer prioridad;
    // La regla completa en formato DRL (Drools Rule Language)
    @Lob
    @Column(name = "regla_drl")
    private String reglaDrl;
    // Datos de auditoría
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;
    @Column(name = "usuario_creacion")
    private String usuarioCreacion;
    @Column(name = "usuario_modificacion")
    private String usuarioModificacion;
    // Indica si la regla está activa
    @Column(name = "activo")
    private Boolean activo;
    // Versión de la regla
    @Column(name = "version")
    private Integer version;
}