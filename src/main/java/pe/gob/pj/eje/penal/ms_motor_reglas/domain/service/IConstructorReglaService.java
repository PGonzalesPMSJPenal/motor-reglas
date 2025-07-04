package pe.gob.pj.eje.penal.ms_motor_reglas.domain.service;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.command.*;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.ResultadoSimulacionDTO;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.SimulacionReglaDTO;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.request.BuilderReglaDTORequest;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.request.ExpedienteDTORequest;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
/**
 * Servicio para la construcción visual de reglas
 */
public interface IConstructorReglaService {
    List<ComboDTOResponseExpediente> obtenerExpedientes() throws IOException;
    /**
     * Obtiene catálogos para el constructor de reglas
     */
    Map<String, List<String>> obtenerCatalogos();
    /**
     * Obtiene operadores disponibles
     */
    List<OperadorDTOResponse> obtenerOperadores();
    /**
     * Busca reglas según criterios
     */
    List<ReglaDroolsDTOResponse> buscarReglas(String nombre);
    /**
     * Lista todas las reglas activas
     */
    List<ReglaDroolsDTOResponse> listarReglasActivas();

    /**
     * Crea una regla Drools a partir del constructor visual
     */
    ReglaDroolsDTOResponse crearReglaDesdeBuilder(CrearReglaCommand builderRegla, String usuario);
    /**
     * Genera código DRL a partir de un builder de regla
     */
    String generarDRLDesdeBuilder(PrevisualizarDRLReglaCommand builderRegla);
    /**
     * Convierte una regla existente a formato builder para su edición
     */
    BuilderReglaDTOResponse convertirABuilder(Long idRegla);
    /**
     * Actualiza una regla existente usando el constructor
     */
    ReglaDroolsDTOResponse actualizarReglaDesdeBuilder(ActualizarReglaCommand builderRegla, String usuario);
    /**
     * Elimina una regla
     */
    Boolean eliminarRegla(Long id);
    /**
     * Activa o desactiva una regla
     */
    ReglaDroolsDTOResponse cambiarEstadoRegla(Long id, Boolean activo, String usuario);
    /**
     * Evalúa un expediente según las reglas configuradas
     */
    ResultadoEvaluacionDTOResponse evaluarExpediente(EvaluarExpedienteCommand expedienteDTO);
    /**
     * Simula la ejecución de una regla para ver su comportamiento
     */
    ResultadoSimulacionDTO simularRegla(SimularReglaCommand simulacion);
    /**
     * Importa reglas desde un archivo Excel
     *//*
    List<ReglaDroolsDTO> importarReglas(byte[] excelBytes, String usuario) throws IOException;*/
}