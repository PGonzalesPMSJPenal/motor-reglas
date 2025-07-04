package pe.gob.pj.eje.penal.ms_motor_reglas.domain.service.impl;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import pe.gob.pj.eje.penal.ms_motor_reglas.infraestructure.model.ReglaDrools;
import pe.gob.pj.eje.penal.ms_motor_reglas.domain.repository.ReglaDroolsRepository;
import java.util.List;
@Service
@Slf4j
public class DroolsEngineServiceImpl {
    private final ReglaDroolsRepository reglaDroolsRepository;
    private final KieServices kieServices = KieServices.Factory.get();
    public DroolsEngineServiceImpl(ReglaDroolsRepository reglaDroolsRepository) {
        this.reglaDroolsRepository = reglaDroolsRepository;
    }
    public KieSession createKieSessionDinamico() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        List<ReglaDrools> reglas = reglaDroolsRepository.findByActivoTrue();
        log.info("REGLAS DESDE BD: {}", reglas);
        for (ReglaDrools regla : reglas) {
            String drl = regla.getReglaDrl();
            if (drl != null && !drl.isEmpty()) {
                kieFileSystem.write("src/main/resources/rules/regla_" + regla.getId() + ".drl", drl);
            }
        }
        // No hace falta setear ReleaseId personalizado si no vas a instalarlo en un repositorio
        // kieFileSystem.generateAndWritePomXML(...);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();
        if (kieBuilder.getResults().hasMessages()) {
            log.error("Errores al compilar reglas: {}", kieBuilder.getResults().getMessages());
            throw new RuntimeException("Errores en reglas Drools");
        }
        // Usar el módulo compilado en memoria
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KieBaseConfiguration baseConfig = kieServices.newKieBaseConfiguration();
        baseConfig.setOption(EventProcessingOption.STREAM);
        KieBase kieBase = kieContainer.newKieBase(baseConfig);
        return kieBase.newKieSession();
    }
}