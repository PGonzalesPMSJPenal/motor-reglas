package pe.gob.pj.eje.penal.ms_motor_reglas.dto.response;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import java.util.List;
@Data
public class ApiResponse {
   private String timestamp;
   private boolean success;
   private String httpStatus;
   private List<String> messages;
   private JsonNode data;
}