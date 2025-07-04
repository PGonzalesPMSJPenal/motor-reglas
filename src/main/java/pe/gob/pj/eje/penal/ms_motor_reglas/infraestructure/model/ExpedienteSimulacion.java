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
@Table(name = "mov_atencion_expediente", schema = "juspro_reglas")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpedienteSimulacion {
    @Id
    @Column(name = "codigo")
    private String id;
    @NotNull
    @Column(name = "uuid")
    private String uuid;
    @NotNull
    @Column(name = "x_tipo_resolucion")
    private String tipoResolucion;
    @NotNull
    @Column(name = "x_acto_procesal")
    private String actoProcesal;
    @NotNull
    @Column(name = "x_sumilla")
    private String sumilla;
    @NotNull
    @Column(name = "x_numero_expediente")
    private String numeroExpediente;
    @NotNull
    @Column(name = "x_bandeja_origen")
    private String bandejaOrigen;
    @NotNull
    @Column(name = "x_bandeja_destino")
    private String bandejaDestino;
}