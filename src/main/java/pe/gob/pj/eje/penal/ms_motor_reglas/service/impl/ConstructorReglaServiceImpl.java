package pe.gob.pj.eje.penal.ms_motor_reglas.service.impl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.pj.eje.penal.ms_motor_reglas.core.DroolsRuleGenerator;
import pe.gob.pj.eje.penal.ms_motor_reglas.dto.ResultadoSimulacionDTO;
import pe.gob.pj.eje.penal.ms_motor_reglas.dto.SimulacionReglaDTO;
import pe.gob.pj.eje.penal.ms_motor_reglas.dto.request.BuilderReglaDTORequest;
import pe.gob.pj.eje.penal.ms_motor_reglas.dto.request.CondicionReglaDTORequest;
import pe.gob.pj.eje.penal.ms_motor_reglas.dto.request.ExpedienteDTORequest;
import pe.gob.pj.eje.penal.ms_motor_reglas.dto.response.*;
import pe.gob.pj.eje.penal.ms_motor_reglas.model.Expediente;
import pe.gob.pj.eje.penal.ms_motor_reglas.model.PlantillaRegla;
import pe.gob.pj.eje.penal.ms_motor_reglas.model.ReglaDrools;
import pe.gob.pj.eje.penal.ms_motor_reglas.repository.ReglaDroolsRepository;
import pe.gob.pj.eje.penal.ms_motor_reglas.service.ConstructorReglaService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Slf4j
public class ConstructorReglaServiceImpl implements ConstructorReglaService {
    private final ReglaDroolsRepository reglaDroolsRepository;
    private final DroolsRuleGenerator droolsRuleGenerator;
    private final DroolsEngineServiceImpl droolsEngineService;
    @Override
    public Map<String, List<String>> obtenerCatalogos() {
        Map<String, List<String>> catalogos = new HashMap<>();
        catalogos.put("atributos", reglaDroolsRepository.findAllAtributos()
                .stream()
                .map(ComboDTOResponse::getNombre)
                .collect(Collectors.toList()));
        catalogos.put("bandejaDestino", reglaDroolsRepository.findAllTipoBandeja()
                .stream()
                .map(ComboDTOResponse::getNombre)
                .collect(Collectors.toList()));
        catalogos.put("especialidad", reglaDroolsRepository.findAllEspecialidad()
                .stream()
                .map(ComboDTOResponse::getNombre)
                .collect(Collectors.toList()));
        catalogos.put("responsable", reglaDroolsRepository.findAllTipoResponsable()
                .stream()
                .map(ComboDTOResponse::getNombre)
                .collect(Collectors.toList()));
        return catalogos;
    }
    @Override
    public List<ComboDTOResponseExpediente> obtenerExpedientes(){
        return reglaDroolsRepository.findAllExpediente();
    }
    @Override
    public List<OperadorDTOResponse> obtenerOperadores() {
        List<OperadorDTOResponse> operadores = new ArrayList<>();
        // Operadores para strings
        operadores.add(OperadorDTOResponse.builder()
                .codigo("EQUALS")
                .simbolo("==")
                .nombre("Igual a")
                .descripcion("Compara si el valor es exactamente igual")
                .requiereValor(true)
                .tipoValor("texto")
                .permiteNegacion(true)
                .build());
        operadores.add(OperadorDTOResponse.builder()
                .codigo("NOT_EQUALS")
                .simbolo("!=")
                .nombre("Diferente de")
                .descripcion("Compara si el valor es diferente")
                .requiereValor(true)
                .tipoValor("texto")
                .permiteNegacion(false)
                .build());
        operadores.add(OperadorDTOResponse.builder()
                .codigo("IN")
                .simbolo("in")
                .nombre("Contenido en lista")
                .descripcion("Verifica si el valor está en una lista de valores separados por coma")
                .requiereValor(true)
                .tipoValor("lista")
                .permiteNegacion(true)
                .build());
        operadores.add(OperadorDTOResponse.builder()
                .codigo("LIKE")
                .simbolo("matches")
                .nombre("Contiene texto")
                .descripcion("Verifica si el texto contiene un patrón (comodines: % para cualquier secuencia, _ para un carácter)")
                .requiereValor(true)
                .tipoValor("texto")
                .permiteNegacion(true)
                .build());
        // Operadores para números
        operadores.add(OperadorDTOResponse.builder()
                .codigo("GREATER_THAN")
                .simbolo(">")
                .nombre("Mayor que")
                .descripcion("Compara si el valor es mayor")
                .requiereValor(true)
                .tipoValor("numerico")
                .permiteNegacion(false)
                .build());
        operadores.add(OperadorDTOResponse.builder()
                .codigo("LESS_THAN")
                .simbolo("<")
                .nombre("Menor que")
                .descripcion("Compara si el valor es menor")
                .requiereValor(true)
                .tipoValor("numerico")
                .permiteNegacion(false)
                .build());
        // Operadores para nulos
        operadores.add(OperadorDTOResponse.builder()
                .codigo("IS_NULL")
                .simbolo("== null")
                .nombre("Es nulo")
                .descripcion("Verifica si el valor es nulo")
                .requiereValor(false)
                .tipoValor(null)
                .permiteNegacion(true)
                .build());
        // Operadores avanzados
        operadores.add(OperadorDTOResponse.builder()
                .codigo("SPEL")
                .simbolo("eval()")
                .nombre("Expresión SpEL")
                .descripcion("Evalúa una expresión SpEL personalizada")
                .requiereValor(true)
                .tipoValor("expresion")
                .permiteNegacion(false)
                .build());
        return operadores;
    }
    @Override
    public List<ReglaDroolsDTOResponse> buscarReglas(String nombre) {
        log.debug("Buscando reglas con filtros: tipoProceso={}",
                nombre);
        return reglaDroolsRepository.findByFiltros(nombre);
    }
    @Override
    @Transactional(readOnly = true)
    public List<ReglaDroolsDTOResponse> listarReglasActivas() {
        log.debug("Listando todas las reglas activas");
        return reglaDroolsRepository.findByAllActivo();
    }
    @Override
    @Transactional
    public ReglaDroolsDTOResponse crearReglaDesdeBuilder(BuilderReglaDTORequest builderRegla, String usuario) {
        // Generar identificador único
        String identificador = "rule_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        // Generar DRL
        String drl;
        if (Boolean.TRUE.equals(builderRegla.getUsarDrlPersonalizado()) &&
                builderRegla.getDrlPersonalizado() != null &&
                !builderRegla.getDrlPersonalizado().isEmpty()) {
            // Usar DRL personalizado
            drl = builderRegla.getDrlPersonalizado();
            log.info("PRUEBA: {}",drl);
            // Validar que el DRL tenga el formato correcto
            validarDrlPersonalizado(drl);
        } else {
            // Generar DRL a partir del builder
            drl = generarDRLDesdeBuilder(builderRegla);
        }
        // Crear entidad ReglaDrools
        ReglaDrools regla = ReglaDrools.builder()
                .nombre(builderRegla.getNombre())
                .descripcion(builderRegla.getDescripcion())
                .documento(builderRegla.getDocumento())
                .prioridad(builderRegla.getPrioridad() != null ? builderRegla.getPrioridad() : 100)
                .reglaDrl(drl)
                .fechaCreacion(LocalDateTime.now())
                .usuarioCreacion(usuario)
                .activo(true)
                .version(1)
                .build();
        log.info("PRUEBA DE DATOS:{}",regla);
        // Guardar en base de datos
        ReglaDrools reglaSaved = reglaDroolsRepository.save(regla);
        return ReglaDroolsDTOResponse.builder()
                .id(reglaSaved.getId())
                .nombre(reglaSaved.getNombre())
                .descripcion(reglaSaved.getDescripcion())
                .documento(reglaSaved.getDocumento())
                .prioridad(reglaSaved.getPrioridad())
                .reglaDrl(reglaSaved.getReglaDrl())
                .fechaCreacion(reglaSaved.getFechaCreacion())
                .usuarioCreacion(reglaSaved.getUsuarioCreacion())
                .activo(reglaSaved.getActivo())
                .version(reglaSaved.getVersion())
                .build();
    }
    /**
     * Valida que un DRL personalizado tenga el formato correcto
     */
    private void validarDrlPersonalizado(String drl) {
        // Verificar que contenga elementos esenciales
        if (!drl.contains("package rules") ||
                !drl.contains("import pe.gob.pj.juspro.ms_motor_reglas.model.Expediente") ||
                !drl.contains("rule") ||
                !drl.contains("when") ||
                !drl.contains("then") ||
                !drl.contains("end")) {
            throw new IllegalArgumentException("El DRL personalizado no tiene el formato correcto");
        }
        // Verificar que contenga al menos una asignación de bandeja destino
        if (!drl.contains("setBandejaDestino")) {
            throw new IllegalArgumentException("El DRL debe contener una asignación de bandeja destino");
        }
    }
    @Override
    public String generarDRLDesdeBuilder(BuilderReglaDTORequest builderRegla) {
        // Convertir builderRegla a plantilla
        PlantillaRegla plantilla = convertirBuilderAPlantilla(builderRegla);
        // Generar DRL usando el generador existente
        return droolsRuleGenerator.generarDRLDesdePlantilla(plantilla);
    }
    /**
     * Convierte un BuilderReglaDTO a una PlantillaRegla
     */
    private PlantillaRegla convertirBuilderAPlantilla(BuilderReglaDTORequest builder) {
        // Generar identificador si es necesario
        String identificador = builder.getId() != null ?
                "rule_" + builder.getId() :
                "rule_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        // Convertir condiciones adicionales
        List<pe.gob.pj.eje.penal.ms_motor_reglas.model.CondicionRegla> condiciones =
                builder.getCondicionesAdicionales().stream()
                        .map(this::convertirCondicion)
                        .collect(Collectors.toList());
        return PlantillaRegla.builder()
                .nombre(builder.getNombre())
                .prioridad(builder.getPrioridad() != null ? builder.getPrioridad() : 100)
                .bandejaDestino(builder.getBandejaDestino())
                .identificador(identificador)
                .estado("FIRMADO") // Por defecto, asumimos este estado
                .condicionesAdicionales(condiciones)
                .build();
    }
    /**
     * Convierte una CondicionReglaDTO a CondicionRegla
     */
    private pe.gob.pj.eje.penal.ms_motor_reglas.model.CondicionRegla convertirCondicion(CondicionReglaDTORequest dto) {
        return pe.gob.pj.eje.penal.ms_motor_reglas.model.CondicionRegla.builder()
                .atributo(dto.getAtributo())
                .operador(dto.getOperador())
                .valor(dto.getValor())
                .build();
    }
    @Override
    public BuilderReglaDTOResponse convertirABuilder(Long idRegla) {
        // Obtener la regla
        ReglaDrools regla = reglaDroolsRepository.findById(idRegla)
                .orElseThrow(() -> new IllegalArgumentException("Regla no encontrada"));
        // Parsear DRL para encontrar condiciones adicionales
        List<CondicionReglaDTOResponse> condicionesAdicionales = extraerCondicionesDesdeRegla(regla);
        // Construir el BuilderReglaDTO
        return BuilderReglaDTOResponse.builder()
                .id(regla.getId())
                .nombre(regla.getNombre())
                .descripcion(regla.getDescripcion())
                .documento(regla.getDocumento())
                .prioridad(regla.getPrioridad())
                .bandejaDestino(extraerBandejaDestinoDesdeRegla(regla))
                .condicionesAdicionales(condicionesAdicionales)
                .usarDrlPersonalizado(true)
                .drlPersonalizado(regla.getReglaDrl().toString())
                .build();
    }
    /**
     * Extrae las condiciones adicionales desde una regla DRL
     */
    private List<CondicionReglaDTOResponse> extraerCondicionesDesdeRegla(ReglaDrools regla) {
        List<CondicionReglaDTOResponse> condiciones = new ArrayList<>();
        // Esto es un análisis simplificado; en una implementación real se necesitaría
        // un parser más robusto para DRL
        String drl = regla.getReglaDrl().toString();
        // Encontrar la sección when
        Pattern whenPattern = Pattern.compile("when\\s*\\{([^}]*)\\}");
        Matcher whenMatcher = whenPattern.matcher(drl);
        if (whenMatcher.find()) {
            String whenBlock = whenMatcher.group(1);
            // Buscar condiciones
            Pattern conditionPattern = Pattern.compile("(\\w+)\\s*([=!<>]+|matches|in)\\s*\"?([^,\"]+)\"?");
            Matcher conditionMatcher = conditionPattern.matcher(whenBlock);
            while (conditionMatcher.find()) {
                String atributo = conditionMatcher.group(1);
                String operador = conditionMatcher.group(2);
                String valor = conditionMatcher.group(3);
                // Ignorar atributos principales que ya están en el builder
                if (!esAtributoPrincipal(atributo)) {
                    condiciones.add(CondicionReglaDTOResponse.builder()
                            .atributo(atributo)
                            .operador(operador)
                            .valor(valor)
                            .negacion(whenBlock.contains("!" + atributo))
                            .build());
                }
            }
        }
        return condiciones;
    }
    /**
     * Extrae la bandeja destino desde una regla DRL
     */
    private String extraerBandejaDestinoDesdeRegla(ReglaDrools regla) {
        String drl = regla.getReglaDrl().toString();
        // Buscar la asignación de bandeja destino
        Pattern pattern = Pattern.compile("setBandejaDestino\\(\\s*\"([^\"]+)\"\\s*\\)");
        Matcher matcher = pattern.matcher(drl);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
    /**
     * Verifica si un atributo es parte de los atributos principales
     */
    private boolean esAtributoPrincipal(String atributo) {
        return Arrays.asList("tipoProceso", "subProceso", "documento", "tipoPedido",
                "tipoOOJJ", "bandejaActual", "actoProcesalId").contains(atributo);
    }
    @Override
    @Transactional
    public ReglaDroolsDTOResponse actualizarReglaDesdeBuilder(BuilderReglaDTORequest builderRegla, String usuario) {
        // Verificar si la regla existe
        ReglaDrools reglaExistente = reglaDroolsRepository.findById(builderRegla.getId())
                .orElseThrow(() -> new IllegalArgumentException("Regla no encontrada"));
        // Generar DRL
        String drl;
        if (Boolean.TRUE.equals(builderRegla.getUsarDrlPersonalizado()) &&
                builderRegla.getDrlPersonalizado() != null &&
                !builderRegla.getDrlPersonalizado().isEmpty()) {
            // Usar DRL personalizado
            drl = builderRegla.getDrlPersonalizado();
            // Validar que el DRL tenga el formato correcto
            validarDrlPersonalizado(drl);
        } else {
            // Generar DRL a partir del builder
            drl = generarDRLDesdeBuilder(builderRegla);
        }
        // Actualizar la entidad
        reglaExistente.setNombre(builderRegla.getNombre());
        reglaExistente.setDescripcion(builderRegla.getDescripcion());
        reglaExistente.setDocumento(builderRegla.getDocumento());
        reglaExistente.setPrioridad(builderRegla.getPrioridad() != null ? builderRegla.getPrioridad() : 100);
        reglaExistente.setReglaDrl(drl);
        reglaExistente.setFechaModificacion(LocalDateTime.now());
        reglaExistente.setUsuarioModificacion(usuario);
        reglaExistente.setVersion(reglaExistente.getVersion() + 1);
        // Guardar en base de datos
        ReglaDrools reglaSaved = reglaDroolsRepository.save(reglaExistente);
        // Recargar reglas
        return ReglaDroolsDTOResponse.builder()
                .id(reglaSaved.getId())
                .nombre(reglaSaved.getNombre())
                .descripcion(reglaSaved.getDescripcion())
                .documento(reglaSaved.getDocumento())
                .prioridad(reglaSaved.getPrioridad())
                .reglaDrl(reglaSaved.getReglaDrl())
                .fechaModificacion(reglaSaved.getFechaModificacion())
                .usuarioModificacion(reglaSaved.getUsuarioModificacion())
                .activo(reglaSaved.getActivo())
                .version(reglaSaved.getVersion())
                .build();
    }
    @Override
    @Transactional
    public void eliminarRegla(Long id) {
        log.info("Eliminando regla con ID: {}", id);
        // Verificar si la regla existe
        reglaDroolsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Regla no encontrada"));
        // Eliminar regla
        reglaDroolsRepository.deleteById(id);
    }
    @Override
    @Transactional
    public ReglaDroolsDTOResponse cambiarEstadoRegla(Long id, Boolean activo, String usuario) {
        log.info("Cambiando estado de regla {} a {}", id, activo);
        // Verificar si la regla existe
        ReglaDrools regla = reglaDroolsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Regla no encontrada"));
        // Actualizar estado
        regla.setActivo(activo);
        regla.setFechaModificacion(LocalDateTime.now());
        regla.setUsuarioModificacion(usuario);
        regla.setVersion(regla.getVersion() + 1);
        // Guardar en base de datos
        ReglaDrools reglaSaved = reglaDroolsRepository.save(regla);
        return ReglaDroolsDTOResponse.builder()
                .id(reglaSaved.getId())
                .nombre(reglaSaved.getNombre())
                .descripcion(reglaSaved.getDescripcion())
                .documento(reglaSaved.getDocumento())
                .prioridad(reglaSaved.getPrioridad())
                .reglaDrl(reglaSaved.getReglaDrl())
                .fechaModificacion(reglaSaved.getFechaModificacion())
                .usuarioModificacion(reglaSaved.getUsuarioModificacion())
                .activo(reglaSaved.getActivo())
                .version(reglaSaved.getVersion())
                .build();
    }
    @Override
    public ResultadoEvaluacionDTOResponse evaluarExpediente(ExpedienteDTORequest expedienteDTO) {
        log.info("Evaluando expediente: {}", expedienteDTO.getNumeroExpediente());
        // Crear una nueva sesión de Drools con reglas desde BD
        KieSession kieSession = droolsEngineService.createKieSessionDinamico();
        try {
            log.info("DTO QUE LLEGA A MAPEARSE: {}", expedienteDTO);
            Expediente expediente = Expediente.builder()
                    .bandejaOrigen(expedienteDTO.getBandejaOrigen())
//                    .estado("FIRMADO")
                    .tipoResolucion(expedienteDTO.getTipoResolucion())
                    .actoProcesal(expedienteDTO.getActoProcesal())
                    .bandejaDestino(null)
                    .build();
            log.info("EXPEDIENTE INSERTADO: {}", expediente);
            kieSession.insert(expediente);
            int reglasFired = kieSession.fireAllRules();
            log.info("Reglas ejecutadas: {}", reglasFired);
            if (expediente.getBandejaDestino() != null) {
                return ResultadoEvaluacionDTOResponse.builder()
                        .exito(true)
                        .bandejaDestino(expediente.getBandejaDestino())
                        .reglaId(expediente.getReglaId())
                        .nombreRegla(expediente.getNombreRegla())
                        .mensaje("Evaluación exitosa")
                        .build();
            } else {
                return ResultadoEvaluacionDTOResponse.builder()
                        .exito(false)
                        .mensaje("No se encontró regla aplicable")
                        .build();
            }
        } finally {
            kieSession.dispose(); // liberar recursos
        }
    }



    @Override
    public ResultadoSimulacionDTO simularRegla(SimulacionReglaDTO simulacion) {
        try {
            // Generar DRL para la regla
            String drl = generarDRLDesdeBuilder(simulacion.getBuilder());
            log.info("DRL GENERADO : {} ",drl);
            // Crear una sesión Drools temporal para simulación
            KieSession kieSession = crearSesionTemporalParaSimulacion(drl);
            try {
                // Convertir expediente DTO a modelo
                Expediente expediente = Expediente.builder()
                        .id(String.valueOf(simulacion.getExpedienteTest().getId()))
                        .numeroExpediente(simulacion.getExpedienteTest().getNumeroExpediente())
                        .tipoResolucion(simulacion.getExpedienteTest().getTipoResolucion())
                        .actoProcesal(simulacion.getExpedienteTest().getActoProcesal())
                        .sumilla(simulacion.getExpedienteTest().getSumilla())
                        .bandejaOrigen(simulacion.getExpedienteTest().getBandejaOrigen())
                        .build();
                expediente.setNombreRegla(simulacion.getBuilder().getNombre());
//                expediente.setBandejaDestino(simulacion.getBuilder().getBandejaDestino());
                log.info("EXPEDIENTE:{}",expediente);
                // Insertar expediente en la sesión
                kieSession.insert(expediente);
                // Ejecutar las reglas
                int reglasActivadas = kieSession.fireAllRules();
                log.info("REGLAS ACTIVADAS:{}",reglasActivadas);
                if (reglasActivadas > 0 && expediente.getBandejaDestino() != null) {
                    return ResultadoSimulacionDTO.builder()
                            .reglaAplicada(true)
                            .bandejaResultante(expediente.getBandejaDestino())
                            .drlGenerado(drl)
                            .mensaje("La regla se aplicó correctamente. " +
                                    "El expediente se movería a la bandeja: " + expediente.getBandejaDestino())
                            .tieneErrores(false)
                            .build();
                } else {
                    return ResultadoSimulacionDTO.builder()
                            .reglaAplicada(false)
                            .drlGenerado(drl)
                            .mensaje("La regla está correcta pero no se aplicó al expediente proporcionado por que no cumple con las condiciones de la regla.")
                            .tieneErrores(false)
                            .build();
                }
            } finally {
                // Liberar recursos
                kieSession.dispose();
            }
        } catch (Exception e) {
            log.error("Error al simular regla", e);
            return ResultadoSimulacionDTO.builder()
                    .reglaAplicada(false)
                    .tieneErrores(true)
                    .errores("Error al simular regla: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Crea una sesión Drools temporal para simulación
     */
    private KieSession crearSesionTemporalParaSimulacion(String drl) {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        String drlPath = "src/main/resources/rules/temp_" + UUID.randomUUID().toString() + ".drl";
        kieFileSystem.write(drlPath, ResourceFactory.newByteArrayResource(drl.getBytes()));

        // Generar un ReleaseId personalizado
        ReleaseId releaseId = kieServices.newReleaseId("pe.gob.pj", "motor-reglas-simulacion", UUID.randomUUID().toString());
        kieFileSystem.generateAndWritePomXML(releaseId);

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();
        if (kieBuilder.getResults().hasMessages()) {
            throw new IllegalArgumentException("Error al compilar regla: " + kieBuilder.getResults().getMessages());
        }

        // Instalar en el repositorio
        kieServices.getRepository().addKieModule(kieBuilder.getKieModule());

        // Crear contenedor y sesión
        KieContainer kieContainer = kieServices.newKieContainer(releaseId);
        return kieContainer.newKieSession();
    }


    /*private KieSession crearSesionTemporalParaSimulacion(String drl) {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        System.out.println("📄 DRL generado dinámicamente:\n" + drl);
        // Agregar la regla al sistema de archivos
        String drlPath = "src/main/resources/rules/temp_" + UUID.randomUUID().toString() + ".drl";
        kieFileSystem.write(drlPath, drl);
        // Construir las reglas
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        // Verificar errores
        if (kieBuilder.getResults().hasMessages()) {
            throw new IllegalArgumentException("Error al compilar regla: " +
                    kieBuilder.getResults().getMessages());
        }
        // Crear contenedor y sesión
        KieContainer kieContainer = kieServices.newKieContainer(
                kieServices.getRepository().getDefaultReleaseId());
        return kieContainer.newKieSession();
    }*/

}




/*
@Override
@Transactional
public List<ReglaDroolsDTO> importarReglas(byte[] excelBytes, String usuario) throws IOException {
    log.info("Importando reglas desde Excel");

    // Usar el importador de Excel
    List<PlantillaRegla> plantillas = excelRuleImporter.importarReglasDroolsDesdeExcel(excelBytes);

    List<ReglaDroolsDTO> reglasCreadas = new ArrayList<>();

    // Crear reglas a partir de las plantillas
    for (PlantillaRegla plantilla : plantillas) {
        ReglaDroolsDTO reglaDTO = crearReglaDesdePlantilla(plantilla, usuario);
        reglasCreadas.add(reglaDTO);
    }

    // Recargar reglas en el motor
//        recargarReglas();

    return reglasCreadas;
}*/