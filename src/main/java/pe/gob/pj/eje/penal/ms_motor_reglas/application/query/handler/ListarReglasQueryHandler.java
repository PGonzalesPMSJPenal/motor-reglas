package pe.gob.pj.eje.penal.ms_motor_reglas.application.query.handler;
import org.springframework.stereotype.Component;
import pe.gob.pj.core.cqrs.annotations.QueryHandlerComponent;
import pe.gob.pj.core.cqrs.query.QueryHandler;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response.ReglaDroolsDTOResponse;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.query.ListarReglasQuery;
import pe.gob.pj.eje.penal.ms_motor_reglas.domain.service.IConstructorReglaService;
import java.util.List;
@QueryHandlerComponent
@Component
public class ListarReglasQueryHandler implements QueryHandler<ListarReglasQuery, List<ReglaDroolsDTOResponse>> {
    private final IConstructorReglaService constructorReglaService;
    public ListarReglasQueryHandler(IConstructorReglaService constructorReglaService) {
        this.constructorReglaService = constructorReglaService;
    }
    @Override
    public List<ReglaDroolsDTOResponse> handle(ListarReglasQuery query) {
        return constructorReglaService.buscarReglas(query.getNombre());
    }
    @Override
    public Class<ListarReglasQuery> getQueryClass() {
        return ListarReglasQuery.class;
    }
}