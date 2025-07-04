package pe.gob.pj.eje.penal.ms_motor_reglas.application.query.handler;
import pe.gob.pj.core.cqrs.query.QueryHandler;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response.ReglaDroolsDTOResponse;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.query.ListarReglasActivasQuery;
import pe.gob.pj.eje.penal.ms_motor_reglas.domain.service.IConstructorReglaService;
import java.util.List;
public class ListarReglasActivasQueryHandler implements QueryHandler<ListarReglasActivasQuery, List<ReglaDroolsDTOResponse>> {
    private final IConstructorReglaService constructorReglaService;
    public ListarReglasActivasQueryHandler(IConstructorReglaService constructorReglaService) {
        this.constructorReglaService = constructorReglaService;
    }
    @Override
    public List<ReglaDroolsDTOResponse> handle(ListarReglasActivasQuery query) {
        return constructorReglaService.listarReglasActivas();
    }
    @Override
    public Class<ListarReglasActivasQuery> getQueryClass() {
        return ListarReglasActivasQuery.class;
    }
}