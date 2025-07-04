package pe.gob.pj.eje.penal.ms_motor_reglas.application.command;
import pe.gob.pj.core.cqrs.command.Command;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response.ResultadoEvaluacionDTOResponse;
public class EvaluarExpedienteCommand implements Command<ResultadoEvaluacionDTOResponse> {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroExpediente() {
        return numeroExpediente;
    }

    public void setNumeroExpediente(String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }

    public String getTipoResolucion() {
        return tipoResolucion;
    }

    public void setTipoResolucion(String tipoResolucion) {
        this.tipoResolucion = tipoResolucion;
    }

    public String getActoProcesal() {
        return actoProcesal;
    }

    public void setActoProcesal(String actoProcesal) {
        this.actoProcesal = actoProcesal;
    }

    public String getSumilla() {
        return sumilla;
    }

    public void setSumilla(String sumilla) {
        this.sumilla = sumilla;
    }

    public String getBandejaOrigen() {
        return bandejaOrigen;
    }

    public void setBandejaOrigen(String bandejaOrigen) {
        this.bandejaOrigen = bandejaOrigen;
    }

    public String getBandejaDestino() {
        return bandejaDestino;
    }

    public void setBandejaDestino(String bandejaDestino) {
        this.bandejaDestino = bandejaDestino;
    }

    private String numeroExpediente;
    private String tipoResolucion;
    private String actoProcesal;
    private String sumilla;
    private String bandejaOrigen;
    private String bandejaDestino;
}