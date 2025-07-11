package pe.gob.pj.eje.penal.ms_motor_reglas.web.queryController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.pj.core.cqrs.query.QueryBus;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response.BuilderReglaDTOResponse;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response.ComboDTOResponseExpediente;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response.OperadorDTOResponse;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response.ReglaDroolsDTOResponse;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.query.*;
import pj.eje.penal.data.abstraction.commondata.dto.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/reglas")
@Slf4j
public class ReglaQueryController {
    private final QueryBus queryBus;
    public ReglaQueryController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }
    /**
     * Obtiene catálogos para construir reglas (operadores, bandejas, etc.)
     */
    @GetMapping("/catalogos")
    public ResponseEntity<Map<String, List<String>>> obtenerCatalogos() {
        Map<String, List<String>> catalogos = queryBus.send(new ListarCatalogoQuery());
        return ResponseEntity.ok(catalogos);
    }
    @GetMapping("/expediente")
    public ResponseEntity<List<ComboDTOResponseExpediente>> obtenerExpedientes() throws IOException {
        List<ComboDTOResponseExpediente> catalogos = queryBus.send(new ListarExpedienteQuery());
        return ResponseEntity.ok(catalogos);
    }
    /**
     * Obtiene tipos de operadores disponibles
     */
    @GetMapping("/operadores")
    public ResponseEntity<List<OperadorDTOResponse>> obtenerOperadores() {
        List<OperadorDTOResponse> operadores = queryBus.send(new ListarOperadoresQuery());
        return ResponseEntity.ok(operadores);
    }
    /**
     * Obtiene todas las reglas
     */
    @GetMapping
    public ResponseEntity<Response> obtenerReglas(
            @RequestParam(required = false) String nombre) {
        log.info("Buscando reglas con filtros: nombre={}",
                nombre);
        List<ReglaDroolsDTOResponse> reglas = queryBus.send(new ListarReglasQuery(nombre));
        return Response.getResponseOk(reglas);
    }
    /**
     * Obtiene todas las reglas activas
     */
    @GetMapping("/activas")
    public ResponseEntity<Response> obtenerReglasActivas() {
        log.info("Obteniendo todas las reglas activas");
        List<ReglaDroolsDTOResponse> reglas = queryBus.send(new ListarReglasActivasQuery());
        return Response.getResponseOk(reglas);
    }
    /**
     * Obtiene una regla existente en formato builder para edición
     */
    @GetMapping("/{id}")
    public ResponseEntity<Response> obtenerReglaParaEdicion(@PathVariable Long id) {
        log.info("Obteniendo regla para edición: {}", id);
        BuilderReglaDTOResponse builderRegla = queryBus.send(new ListarReglaPorIdQuery(id));
        return Response.getResponseOk(builderRegla);
    }
}