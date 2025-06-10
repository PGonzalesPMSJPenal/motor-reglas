package pe.gob.pj.eje.penal.ms_motor_reglas.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.gob.pj.eje.penal.ms_motor_reglas.dto.response.ComboDTOResponse;
import pe.gob.pj.eje.penal.ms_motor_reglas.dto.response.ReglaDroolsDTOResponse;
import pe.gob.pj.eje.penal.ms_motor_reglas.model.ReglaDrools;
import java.util.List;
@Repository
public interface ReglaDroolsRepository extends JpaRepository<ReglaDrools, Long> {
    @Query("SELECT new pe.gob.pj.eje.penal.ms_motor_reglas.dto.response.ComboDTOResponse(" +
            "x.id, " +
            "x.nombre) from BandejaDestino x")
    List<ComboDTOResponse> findAllTipoBandeja();
    @Query("SELECT new pe.gob.pj.eje.penal.ms_motor_reglas.dto.response.ComboDTOResponse(" +
            "x.id, " +
            "x.nombre) from Responsable x")
    List<ComboDTOResponse> findAllTipoResponsable();
    @Query("SELECT new pe.gob.pj.eje.penal.ms_motor_reglas.dto.response.ComboDTOResponse(" +
            "x.id, " +
            "x.nombre) from Atributos x")
    List<ComboDTOResponse> findAllAtributos();
    @Query("SELECT new pe.gob.pj.eje.penal.ms_motor_reglas.dto.response.ComboDTOResponse(" +
            "x.id, " +
            "x.nombre) from Especialidad x")
    List<ComboDTOResponse> findAllEspecialidad();
    /*@Query("SELECT new pe.gob.pj.eje.penal.ms_motor_reglas.dto.response.ComboDTOResponseExpediente(" +
            "x.numeroExpediente, " +
            "x.tipoResolucion, " +
            "x.actoProcesal, " +
            "x.sumilla, " +
            "x.bandejaOrigen) from ExpedienteSimulacion x")
    List<ComboDTOResponseExpediente> findAllExpediente();*/
    /**
     * Busca reglas que coincidan con los criterios de filtro
     */
    @Query("SELECT new pe.gob.pj.eje.penal.ms_motor_reglas.dto.response.ReglaDroolsDTOResponse(" +
            "r.id, " +
            "r.nombre," +
            "r.descripcion, " +
            "r.documento, " +
            "r.prioridad, " +
            "r.reglaDrl, " +
            "r.usuarioCreacion, " +
            "r.fechaCreacion, " +
            "r.activo, " +
            "r.version) FROM ReglaDrools r WHERE (:nombre IS NULL OR r.nombre = :nombre)")
    List<ReglaDroolsDTOResponse> findByFiltros(
            @Param("nombre") String nombre);
    /**
     * Busca reglas por estado activo
     */
    @Query("SELECT new pe.gob.pj.eje.penal.ms_motor_reglas.dto.response.ReglaDroolsDTOResponse(" +
            "r.id, " +
            "r.nombre," +
            "r.descripcion, " +
            "r.documento, " +
            "r.prioridad, " +
            "r.reglaDrl, " +
            "r.usuarioCreacion, " +
            "r.fechaCreacion, " +
            "r.activo, " +
            "r.version) FROM ReglaDrools r WHERE r.activo=TRUE")
    List<ReglaDroolsDTOResponse> findByAllActivo();
    List<ReglaDrools> findByActivoTrue();
    /**
     * Busca reglas por identificador
     *//*
    ReglaDrools findByIdentificador(String identificador);
    *//**
     * Busca reglas que coincidan con los criterios de filtro
     *//*
    @Query("SELECT r FROM ReglaDrools r WHERE " +
            "(:tipoProceso IS NULL OR r.tipoProceso = :tipoProceso) AND " +
            "(:subProceso IS NULL OR r.subProceso = :subProceso) AND " +
            "(:actoProcesalId IS NULL OR r.actoProcesalId = :actoProcesalId)")
    List<ReglaDrools> findByFiltros(
            @Param("tipoProceso") String tipoProceso,
            @Param("subProceso") String subProceso,
            @Param("actoProcesalId") String actoProcesalId);
    *//**
     * Busca reglas por nombre conteniendo texto (case insensitive)
     *//*
    List<ReglaDrools> findByNombreContainingIgnoreCase(String nombre);
    *//**
     * Busca reglas por bandeja origen
     *//*
    List<ReglaDrools> findByBandejaActual(String bandejaActual);
    *//**
     * Busca reglas por acto procesal
     *//*
    List<ReglaDrools> findByActoProcesalId(String actoProcesalId);
    *//**
     * Busca reglas por tipo de proceso y estado activo
     *//*
    List<ReglaDrools> findByTipoProcesoAndActivo(String tipoProceso, boolean activo);*/
}