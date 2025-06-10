package pe.gob.pj.eje.penal.ms_motor_reglas.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.pj.eje.penal.ms_motor_reglas.dto.ResultadoSimulacionDTO;
import pe.gob.pj.eje.penal.ms_motor_reglas.dto.SimulacionReglaDTO;
import pe.gob.pj.eje.penal.ms_motor_reglas.dto.request.BuilderReglaDTORequest;
import pe.gob.pj.eje.penal.ms_motor_reglas.dto.request.ExpedienteDTORequest;
import pe.gob.pj.eje.penal.ms_motor_reglas.dto.response.*;
import pe.gob.pj.eje.penal.ms_motor_reglas.service.ConstructorReglaService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
/**
 * Controlador para la interfaz de usuario de creación de reglas
 */
@RestController
@RequestMapping("/api/reglas")
@RequiredArgsConstructor
@Slf4j
public class ConstructorReglaController {
    private final ConstructorReglaService constructorService;
    /**
     * Obtiene catálogos para construir reglas (operadores, bandejas, etc.)
     */
    @GetMapping("/catalogos")
    public ResponseEntity<Map<String, List<String>>> obtenerCatalogos() {
        Map<String, List<String>> catalogos = constructorService.obtenerCatalogos();
        return ResponseEntity.ok(catalogos);
    }
    @GetMapping("/expediente")
    public ResponseEntity<List<ComboDTOResponseExpediente>> obtenerExpedientes() throws IOException {
        List<ComboDTOResponseExpediente> catalogos = constructorService.obtenerExpedientes();
        return ResponseEntity.ok(catalogos);
    }
    /**
     * Obtiene tipos de operadores disponibles
     */
    @GetMapping("/operadores")
    public ResponseEntity<List<OperadorDTOResponse>> obtenerOperadores() {
        List<OperadorDTOResponse> operadores = constructorService.obtenerOperadores();
        return ResponseEntity.ok(operadores);
    }
    /**
     * Obtiene todas las reglas
     */
    @GetMapping
    public ResponseEntity<List<ReglaDroolsDTOResponse>> obtenerReglas(
            @RequestParam(required = false) String nombre) {
        log.info("Buscando reglas con filtros: nombre={}",
                nombre);
        List<ReglaDroolsDTOResponse> reglas = constructorService.buscarReglas(nombre);
        return ResponseEntity.ok(reglas);
    }
    /**
     * Obtiene todas las reglas activas
     */
    @GetMapping("/activas")
    public ResponseEntity<List<ReglaDroolsDTOResponse>> obtenerReglasActivas() {
        log.info("Obteniendo todas las reglas activas");
        List<ReglaDroolsDTOResponse> reglas = constructorService.listarReglasActivas();
        return ResponseEntity.ok(reglas);
    }
    /**
     * Crea una nueva regla utilizando el constructor
     */
    @PostMapping
    public ResponseEntity<ReglaDroolsDTOResponse> crearRegla(
            @Valid @RequestBody BuilderReglaDTORequest builderRegla,
            @RequestParam(defaultValue = "sistema") String usuario) {

        log.info("Creando regla desde builder: {}", builderRegla.getNombre());
        log.info("PRUEBA DE DATOS DE ENTRADA: {}",builderRegla);
        ReglaDroolsDTOResponse regla = constructorService.crearReglaDesdeBuilder(builderRegla, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(regla);
    }
    /**
     * Genera el DRL para una regla pero sin persistirla
     */
    @PostMapping("/previsualizar-drl")
    public ResponseEntity<String> previsualizarDRL(
            @Valid @RequestBody BuilderReglaDTORequest builderRegla) {
        log.info("Previsualizando DRL para: {}", builderRegla.getNombre());
        String drl = constructorService.generarDRLDesdeBuilder(builderRegla);
        log.info("DROOL GENERADO: {}",drl);
        return ResponseEntity.ok(drl);
    }
    /**
     * Obtiene una regla existente en formato builder para edición
     */
    @GetMapping("/{id}")
    public ResponseEntity<BuilderReglaDTOResponse> obtenerReglaParaEdicion(@PathVariable Long id) {
        log.info("Obteniendo regla para edición: {}", id);
        BuilderReglaDTOResponse builderRegla = constructorService.convertirABuilder(id);
        return ResponseEntity.ok(builderRegla);
    }
    /**
     * Actualiza una regla existente usando el constructor
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReglaDroolsDTOResponse> actualizarRegla(
            @PathVariable Long id,
            @Valid @RequestBody BuilderReglaDTORequest builderRegla,
            @RequestParam(defaultValue = "sistema") String usuario) {
        log.info("Actualizando regla desde builder: {}", id);
        builderRegla.setId(id);
        ReglaDroolsDTOResponse reglaActualizada = constructorService.actualizarReglaDesdeBuilder(builderRegla, usuario);
        return ResponseEntity.ok(reglaActualizada);
    }
    /**
     * Elimina una regla existente usando el constructor
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegla(@PathVariable Long id) {
        log.info("Eliminando regla con ID: {}", id);
        constructorService.eliminarRegla(id);
        return ResponseEntity.noContent().build();
    }
    /**
     * Cambia estado de una regla existente usando el constructor
     */
    @PutMapping("/{id}/estado")
    public ResponseEntity<ReglaDroolsDTOResponse> cambiarEstadoRegla(
            @PathVariable Long id,
            @RequestParam boolean activo,
            @RequestParam(defaultValue = "sistema") String usuario) {
        log.info("Cambiando estado de regla {}: activo={}", id, activo);
        ReglaDroolsDTOResponse regla = constructorService.cambiarEstadoRegla(id, activo, usuario);
        return ResponseEntity.ok(regla);
    }
    @PostMapping("/evaluar")
    public ResponseEntity<ResultadoEvaluacionDTOResponse> evaluarExpediente(@RequestBody ExpedienteDTORequest expedienteDTO){
        log.info(": {}",expedienteDTO);
        log.info("Regla aplicada al expediente: {}", expedienteDTO.getNumeroExpediente());
        ResultadoEvaluacionDTOResponse resultado = constructorService.evaluarExpediente(expedienteDTO);
        return ResponseEntity.ok(resultado);
    }
    /**
     * Simula la aplicación de una regla para verificar su funcionamiento
     */
    @PostMapping("/simular")
    public ResponseEntity<ResultadoSimulacionDTO> simularRegla(
            @Valid @RequestBody SimulacionReglaDTO simulacion) {
        log.info("Simulando regla: {}", simulacion.getBuilder().getNombre());
        ResultadoSimulacionDTO resultado = constructorService.simularRegla(simulacion);
        return ResponseEntity.ok(resultado);
    }
}