package pe.gob.pj.eje.penal.ms_motor_reglas.application.query.handler;
import pe.gob.pj.core.cqrs.query.QueryHandler;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response.BuilderReglaDTOResponse;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.query.ListarReglaPorIdQuery;
import pe.gob.pj.eje.penal.ms_motor_reglas.domain.service.IConstructorReglaService;
public class ListarReglaPorIdQueryHandler implements QueryHandler<ListarReglaPorIdQuery,BuilderReglaDTOResponse> {
    private final IConstructorReglaService constructorReglaService;

    public ListarReglaPorIdQueryHandler(IConstructorReglaService constructorReglaService) {
        this.constructorReglaService = constructorReglaService;
    }

    @Override
    public BuilderReglaDTOResponse handle(ListarReglaPorIdQuery query) {
        return constructorReglaService.convertirABuilder(query.getId());
    }

    @Override
    public Class<ListarReglaPorIdQuery> getQueryClass() {
        return ListarReglaPorIdQuery.class;
    }
}