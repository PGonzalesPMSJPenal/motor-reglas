package pe.gob.pj.eje.penal.ms_motor_reglas.application.query.handler;
import pe.gob.pj.core.cqrs.query.QueryHandler;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.dto.response.ComboDTOResponseExpediente;
import pe.gob.pj.eje.penal.ms_motor_reglas.application.query.ListarExpedienteQuery;
import pe.gob.pj.eje.penal.ms_motor_reglas.domain.service.IConstructorReglaService;
import java.io.IOException;
import java.util.List;
public class ListarExpedienteQueryHandler implements QueryHandler<ListarExpedienteQuery, List<ComboDTOResponseExpediente>> {
    private final IConstructorReglaService constructorReglaService;
    public ListarExpedienteQueryHandler(IConstructorReglaService constructorReglaService) {
        this.constructorReglaService = constructorReglaService;
    }
    @Override
    public List<ComboDTOResponseExpediente> handle(ListarExpedienteQuery query) {
        try {
            return constructorReglaService.obtenerExpedientes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Class<ListarExpedienteQuery> getQueryClass() {
        return ListarExpedienteQuery.class;
    }
}