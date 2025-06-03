package pe.gob.pj.eje.penal.ms_motor_reglas.core;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.gob.pj.eje.penal.ms_motor_reglas.model.PlantillaRegla;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Generador de reglas DRL para Drools
 */
@Component
@Slf4j
public class DroolsRuleGenerator {
    private static final String PACKAGE_NAME = "rules";
    private static final String IMPORT_EXPEDIENTE = "import pe.gob.pj.eje.penal.ms_motor_reglas.model.Expediente;";
    /**
     * Genera una regla DRL a partir de una plantilla
     */
    public String generarDRLDesdePlantilla(PlantillaRegla plantilla) {
        log.debug("Generando DRL para plantilla: {}", plantilla.getNombre());
        StringBuilder drl = new StringBuilder();
        // Encabezado del DRL
        drl.append("package ").append(PACKAGE_NAME).append(";\n\n");
        drl.append(IMPORT_EXPEDIENTE).append("\n\n");
        // Comentario de la regla
        drl.append("// Regla generada automáticamente para ").append(plantilla.getNombre()).append("\n");
        // Definición de la regla
        drl.append("rule \"").append(plantilla.getIdentificador()).append("\"\n");
        // Prioridad (salience)
        drl.append("    salience ").append(plantilla.getPrioridad() != null ? plantilla.getPrioridad() : 0).append("\n");
        // Evitar ciclos infinitos
        drl.append("    no-loop true\n");
        // Cuerpo de la regla
        drl.append("    when\n");
        // Condiciones del Expediente
        drl.append("        $expediente: Expediente(\n");
        // Lista para almacenar las condiciones
        List<String> condiciones = buildCondicionesPrincipales(plantilla);
        // Agregar condiciones adicionales
        if (plantilla.getCondicionesAdicionales() != null && !plantilla.getCondicionesAdicionales().isEmpty()) {
            List<String> condicionesAdicionales = plantilla.getCondicionesAdicionales().stream()
                    .map(c -> buildCondicion(c.getAtributo(), c.getOperador(), c.getValor()))
                    .collect(Collectors.toList());
            condiciones.addAll(condicionesAdicionales);
        }
        // Agregar condición para verificar que bandejaDestino no esté ya asignada
        condiciones.add("bandejaDestino == null");
        // Unir todas las condiciones
        drl.append("            ").append(String.join(",\n            ", condiciones)).append("\n");
        drl.append("        )\n");
        // Bloque then
        drl.append("    then\n");
        // Acción principal: asignar bandeja destino
        drl.append("        $expediente.setBandejaDestino(\"").append(plantilla.getBandejaDestino()).append("\");\n");
        drl.append("        $expediente.setReglaId(null); // ID se asignará al guardar la regla\n");
        drl.append("        $expediente.setNombreRegla(\"").append(plantilla.getNombre()).append("\");\n");
        drl.append("\n");
        // Mensajes de log
        drl.append("        System.out.println(\"Regla aplicada: ").append(plantilla.getNombre()).append("\");\n");
        drl.append("        System.out.println(\"Expediente: \" + $expediente.getNumeroExpediente());\n");
        drl.append("        System.out.println(\"Bandeja destino: \" + $expediente.getBandejaDestino());\n");
        drl.append("\n");
        // Actualizar el objeto en la sesión
        drl.append("        update($expediente);\n");
        // Fin de la regla
        drl.append("end\n");
        log.debug("DRL generado: {}", drl.toString());
        return drl.toString();
    }
    /**
     * Construye las condiciones principales a partir de la plantilla
     */
    private List<String> buildCondicionesPrincipales(PlantillaRegla plantilla) {
        List<String> condiciones = new java.util.ArrayList<>();
        if (plantilla.getEstado() != null && !plantilla.getEstado().isEmpty()) {
/*
            condiciones.add(buildCondicion("estado", "==", plantilla.getEstado()));
*/
        }
        return condiciones;
    }
    private String buildCondicion(String atributo, String operador, String valor) {
        // Detectar si el operador es "in"
        if ("in".equalsIgnoreCase(operador.trim())) {
            // Soporta múltiples valores separados por coma
            String[] valores = valor.split(",");
            String valoresFormateados = Arrays.stream(valores)
                    .map(String::trim)
                    .map(this::formatearValorLiteral)
                    .collect(Collectors.joining(", "));
            return atributo + " in (" + valoresFormateados + ")";
        }
        return atributo + " " + operador + " " + formatearValorLiteral(valor);
    }
    private String formatearValorLiteral(String valor) {
        // Si ya tiene comillas o es boolean/número, no lo modifiques
        if (valor == null) return "null";
        // Detección básica para evitar duplicar comillas
        String valorLower = valor.toLowerCase();
        if (valor.startsWith("\"") && valor.endsWith("\"")) return valor;
        if ("true".equals(valorLower) || "false".equals(valorLower)) return valor;
        if (valor.matches("-?\\d+(\\.\\d+)?")) return valor; // número
        // Si no parece enum calificado ni está importado, trátalo como string
        return "\"" + valor + "\"";
    }
}