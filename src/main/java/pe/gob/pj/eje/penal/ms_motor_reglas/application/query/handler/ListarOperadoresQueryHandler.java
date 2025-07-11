package pe.gob.pj.eje.penal.ms_motor_reglas.application.query.handler;
import org.springframework.stereotype.Component;
import pe.gob.pj.core.cqrs.annotations.QueryHandlerComponent;
import pe.gob.pj.core.cqrs.query.QueryHandler;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response.OperadorDTOResponse;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.query.ListarOperadoresQuery;
import pe.gob.pj.eje.penal.ms_motor_reglas.domain.service.IConstructorReglaService;
import java.util.List;
@QueryHandlerComponent
@Component
public class ListarOperadoresQueryHandler implements QueryHandler<ListarOperadoresQuery, List<OperadorDTOResponse>> {
    private final IConstructorReglaService constructorReglaService;
    public ListarOperadoresQueryHandler(IConstructorReglaService constructorReglaService) {
        this.constructorReglaService = constructorReglaService;
    }
    @Override
    public List<OperadorDTOResponse> handle(ListarOperadoresQuery query) {
        return constructorReglaService.obtenerOperadores();
    }
    @Override
    public Class<ListarOperadoresQuery> getQueryClass() {
        return ListarOperadoresQuery.class;
    }
}