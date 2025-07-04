package pe.gob.pj.eje.penal.ms_motor_reglas.web.commandController;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.pj.core.cqrs.command.CommandBus;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.command.*;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.SimulacionReglaDTO;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.request.BuilderReglaDTORequest;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.request.ExpedienteDTORequest;
import pj.eje.penal.data.abstraction.commondata.dto.Response;
@RestController
@RequestMapping("/api/reglas")
@Slf4j
public class ReglaCommandController {
    private final CommandBus commandBus;
    private final ObjectMapper objectMapper;
    public ReglaCommandController(CommandBus commandBus, ObjectMapper objectMapper) {
        this.commandBus = commandBus;
        this.objectMapper = objectMapper;
    }
    /**
     * Crea una nueva regla utilizando el constructor
     */
    @PostMapping
    public ResponseEntity<Response> crearRegla(
            @Valid @RequestBody BuilderReglaDTORequest builderRegla,
            @RequestParam(defaultValue = "sistema") String usuario) {

        log.info("Creando regla desde builder: {}", builderRegla.getNombre());
        log.info("PRUEBA DE DATOS DE ENTRADA: {}",builderRegla);
        CrearReglaCommand crearReglaCommand = objectMapper.convertValue(builderRegla, CrearReglaCommand.class);
        return Response.getResponseOk(commandBus.send(crearReglaCommand));
    }
    /**
     * Actualiza una regla existente usando el constructor
     */
    @PutMapping("/{id}")
    public ResponseEntity<Response> actualizarRegla(
            @PathVariable Long id,
            @Valid @RequestBody BuilderReglaDTORequest builderRegla,
            @RequestParam(defaultValue = "sistema") String usuario) {
        log.info("Actualizando regla desde builder: {}", id);
        ActualizarReglaCommand actualizarReglaCommand = objectMapper.convertValue(builderRegla, ActualizarReglaCommand.class);
        actualizarReglaCommand.setId(id);
        return Response.getResponseOk(commandBus.send(actualizarReglaCommand));
    }
    /**
     * Elimina una regla existente usando el constructor
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> eliminarRegla(@PathVariable Long id) {
        log.info("Eliminando regla con ID: {}", id);
        EliminarReglaCommand eliminarReglaCommand = new EliminarReglaCommand();
        eliminarReglaCommand.setId(id);
        commandBus.send(eliminarReglaCommand);
        return Response.getResponseOk("Regla eliminada con éxito");
    }
    @PutMapping("/{id}/estado")
    public ResponseEntity<Response> cambiarEstadoRegla(
            @PathVariable Long id,
            @RequestParam boolean activo,
            @RequestParam(defaultValue = "sistema") String usuario) {
        log.info("Cambiando estado de regla {}: activo={}", id, activo);
        CambiarEstadoReglaCommand cambiarEstadoReglaCommand = new CambiarEstadoReglaCommand();
        cambiarEstadoReglaCommand.setId(id);
        cambiarEstadoReglaCommand.setActivo(activo);
        return Response.getResponseOk(cambiarEstadoReglaCommand);
    }
    /**
     * Genera el DRL para una regla pero sin persistirla
     */
    @PostMapping("/previsualizar-drl")
    public ResponseEntity<Response> previsualizarDRL(
            @Valid @RequestBody BuilderReglaDTORequest builderRegla) {
        log.info("Previsualizando DRL para: {}", builderRegla.getNombre());
        PrevisualizarDRLReglaCommand previsualizarDRLReglaCommand = objectMapper.convertValue(builderRegla, PrevisualizarDRLReglaCommand.class);
        log.info("DROOL GENERADO: {}",previsualizarDRLReglaCommand);
        return Response.getResponseOk(commandBus.send(previsualizarDRLReglaCommand));
    }
    /**
     * Simula la aplicación de una regla para verificar su funcionamiento
     */
    @PostMapping("/simular")
    public ResponseEntity<Response> simularRegla(
            @Valid @RequestBody SimulacionReglaDTO simulacion) {
        SimularReglaCommand simularReglaCommand = objectMapper.convertValue(simulacion, SimularReglaCommand.class);
        return Response.getResponseOk(commandBus.send(simularReglaCommand));
    }
    /**
     * Cambia estado de una regla existente usando el constructor
     */
    @PostMapping("/evaluar")
    public ResponseEntity<Response> evaluarExpediente(@RequestBody ExpedienteDTORequest expedienteDTO){
        log.info(": {}",expedienteDTO);
        log.info("Regla aplicada al expediente: {}", expedienteDTO.getNumeroExpediente());
        EvaluarExpedienteCommand evaluarExpedienteCommand = objectMapper.convertValue(expedienteDTO, EvaluarExpedienteCommand.class);
        return Response.getResponseOk(commandBus.send(evaluarExpedienteCommand));
    }
}