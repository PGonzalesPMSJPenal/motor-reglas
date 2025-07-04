package pe.gob.pj.eje.penal.ms_motor_reglas;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import pe.gob.pj.core.cqrs.annotations.EnableCqrs;
@SpringBootApplication(scanBasePackages = "pe.gob.pj")
@EntityScan(basePackages = "pe.gob.pj.eje.penal.ms_motor_reglas.infraestructure.model")
@EnableCqrs("pe.gob.pj.core.eje.penal.ms-motor-reglas")
@ComponentScan(
		basePackages = {"pe.gob.pj", "pe.gob.pj.core"}
)
public class MsMotorReglasApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsMotorReglasApplication.class, args);
	}
}