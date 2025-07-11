package pe.gob.pj.eje.penal.ms_motor_reglas.application.query.handler;
import org.springframework.stereotype.Component;
import pe.gob.pj.core.cqrs.annotations.CommandHandlerComponent;
import pe.gob.pj.core.cqrs.annotations.QueryHandlerComponent;
import pe.gob.pj.core.cqrs.query.QueryHandler;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.query.ListarCatalogoQuery;
import pe.gob.pj.eje.penal.ms_motor_reglas.domain.service.IConstructorReglaService;
import java.util.List;
import java.util.Map;
@QueryHandlerComponent
@Component
public class ListarCatalogoQueryHandler implements QueryHandler<ListarCatalogoQuery, Map<String, List<String>>> {
    private final IConstructorReglaService constructorReglaService;
    public ListarCatalogoQueryHandler(IConstructorReglaService constructorReglaService) {
        this.constructorReglaService = constructorReglaService;
    }
    @Override
    public Map<String, List<String>> handle(ListarCatalogoQuery query) {
        return constructorReglaService.obtenerCatalogos();
    }
    @Override
    public Class<ListarCatalogoQuery> getQueryClass() {
        return ListarCatalogoQuery.class;
    }
}