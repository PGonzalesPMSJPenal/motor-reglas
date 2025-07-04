package pe.gob.pj.eje.penal.ms_motor_reglas.domain.service.impl;/*
package pe.gob.pj.juspro.ms_motor_reglas.core;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import pe.gob.pj.juspro.ms_motor_reglas.model.CondicionRegla;
import pe.gob.pj.juspro.ms_motor_reglas.model.PlantillaRegla;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
*/
/**
 * Importador de reglas desde Excel para Drools
 *//*

@Component
@Slf4j
public class ExcelRuleImporter {

    // Nombres de columnas esperados en el Excel
    private static final String[] COLUMNAS_ESPERADAS = {
            "TIPO DE PROCESO", "SUB PROCESO", "DOCUMENTO", "TIPO DE PEDIDO",
            "TIPO DE OOJJ", "BANDEJA ACTUAL", "ACTO PROCESAL", "¿MOVIMIENTO BANDEJA?", "BANDEJA DESTINO"
    };


*/
/**
     * Importa reglas desde un archivo Excel
     *//*


    public List<PlantillaRegla> importarReglasDroolsDesdeExcel(byte[] excelData) throws IOException {
        List<PlantillaRegla> plantillas = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(excelData))) {
            // Encontrar hoja principal con la matriz de configuración
            Sheet sheet = encontrarHojaConfiguracion(workbook);

            if (sheet == null) {
                throw new IllegalArgumentException("No se encontró la hoja con la matriz de configuración");
            }

            // Validar y mapear columnas
            Map<String, Integer> mapeoColumnas = validarYMapearColumnas(sheet);
            if (mapeoColumnas.size() != COLUMNAS_ESPERADAS.length) {
                throw new IllegalArgumentException("El formato del Excel no coincide con el esperado");
            }

            // Procesar filas y crear plantillas
            procesarFilas(sheet, mapeoColumnas, plantillas);

            log.info("Se importaron {} plantillas de reglas desde el Excel", plantillas.size());
        }

        return plantillas;
    }


*/
/**
     * Encuentra la hoja que contiene la matriz de configuración
     *//*


    private Sheet encontrarHojaConfiguracion(Workbook workbook) {
        // Buscar la hoja llamada "Formato" o similar
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            String nombre = sheet.getSheetName().toLowerCase();

            if (nombre.contains("formato") || nombre.contains("config") || nombre.contains("matriz")) {
                return sheet;
            }
        }

        // Si no encontramos una hoja específica, usamos la primera
        return workbook.getSheetAt(0);
    }


*/
/**
     * Valida y mapea las columnas del Excel
     *//*


    private Map<String, Integer> validarYMapearColumnas(Sheet sheet) {
        Map<String, Integer> mapeoColumnas = new HashMap<>();
        Row headerRow = sheet.getRow(0);

        if (headerRow == null) {
            throw new IllegalArgumentException("El Excel no tiene una fila de encabezado");
        }

        // Mapear índices de columnas
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell != null) {
                String columnName = cell.getStringCellValue().trim();
                for (String expectedColumn : COLUMNAS_ESPERADAS) {
                    if (expectedColumn.equalsIgnoreCase(columnName)) {
                        mapeoColumnas.put(expectedColumn, i);
                        break;
                    }
                }
            }
        }

        return mapeoColumnas;
    }


*/
/**
     * Procesa las filas del Excel y crea las plantillas
     *//*


    private void procesarFilas(Sheet sheet, Map<String, Integer> mapeoColumnas, List<PlantillaRegla> plantillas) {
        int ultimaFila = sheet.getLastRowNum();

        for (int i = 1; i <= ultimaFila; i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            try {
                PlantillaRegla plantilla = crearPlantillaDesdeFilaExcel(row, mapeoColumnas);
                if (plantilla != null) {
                    plantillas.add(plantilla);
                }
            } catch (Exception e) {
                log.error("Error procesando fila {}: {}", i, e.getMessage());
                // Continuamos con la siguiente fila
            }
        }
    }


*/
/**
     * Crea una plantilla de regla a partir de una fila del Excel
     *//*


    private PlantillaRegla crearPlantillaDesdeFilaExcel(Row row, Map<String, Integer> mapeoColumnas) {
        // Verificar si hay movimiento de bandeja (obligatorio)
        Cell cellMovimiento = row.getCell(mapeoColumnas.get("¿MOVIMIENTO BANDEJA?"));
        if (cellMovimiento == null || !obtenerValorBooleano(cellMovimiento)) {
            return null; // No hay movimiento, no se crea regla
        }

        // Verificar bandeja destino (obligatorio)
        Cell cellBandejaDestino = row.getCell(mapeoColumnas.get("BANDEJA DESTINO"));
        if (cellBandejaDestino == null || cellBandejaDestino.getStringCellValue().trim().isEmpty()) {
            return null; // No hay bandeja destino, no se crea regla
        }

        // Obtener valores de las celdas
        String tipoProceso = obtenerValorCelda(row, mapeoColumnas, "TIPO DE PROCESO");
        String subProceso = obtenerValorCelda(row, mapeoColumnas, "SUB PROCESO");
        String documento = obtenerValorCelda(row, mapeoColumnas, "DOCUMENTO");
        String tipoPedido = obtenerValorCelda(row, mapeoColumnas, "TIPO DE PEDIDO");
        String tipoOOJJ = obtenerValorCelda(row, mapeoColumnas, "TIPO DE OOJJ");
        String bandejaActual = obtenerValorCelda(row, mapeoColumnas, "BANDEJA ACTUAL");
        String actoProcesalId = obtenerValorCelda(row, mapeoColumnas, "ACTO PROCESAL");
        String bandejaDestino = obtenerValorCelda(row, mapeoColumnas, "BANDEJA DESTINO");

        // Crear plantilla
        PlantillaRegla plantilla = PlantillaRegla.builder()
                .nombre(generarNombreRegla(tipoProceso, actoProcesalId, bandejaActual, bandejaDestino))
                .identificador("rule_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16))
                .prioridad(100) // Prioridad alta por defecto
                .tipoResolucion(tipoProceso)
                .subProceso(subProceso)
                .documento(documento)
                .tipoPedido(tipoPedido)
                .tipoOOJJ(tipoOOJJ)
                .bandejaActual(bandejaActual)
                .actoProcesalId(actoProcesalId)
                .estado("FIRMADO") // Asumimos que siempre es "FIRMADO" para movimiento de bandeja
                .bandejaDestino(bandejaDestino)
                .build();

        // Agregar condiciones adicionales específicas
        List<CondicionRegla> condicionesAdicionales = new ArrayList<>();

        // Si es tipo proceso "Especial", necesitamos verificar subProceso
        if ("Especial".equals(tipoProceso) && subProceso != null && !subProceso.isEmpty()) {
            condicionesAdicionales.add(CondicionRegla.builder()
                    .atributo("subProceso")
                    .operador("==")
                    .valor("\"" + subProceso + "\"")
                    .build());
        }

        plantilla.setCondicionesAdicionales(condicionesAdicionales);

        return plantilla;
    }


*/
/**
     * Genera un nombre para la regla basado en sus atributos
     *//*


    private String generarNombreRegla(String tipoProceso, String actoProcesalId, String bandejaOrigen, String bandejaDestino) {
        StringBuilder sb = new StringBuilder();

        if (tipoProceso != null && !tipoProceso.isEmpty()) {
            sb.append(tipoProceso).append(" - ");
        }

        if (actoProcesalId != null && !actoProcesalId.isEmpty()) {
            sb.append(actoProcesalId).append(" - ");
        }

        if (bandejaOrigen != null && !bandejaOrigen.isEmpty()) {
            sb.append(bandejaOrigen).append(" -> ");
        }

        sb.append(bandejaDestino);

        return sb.toString();
    }


*/
/**
     * Obtiene el valor de una celda como string
     *//*


    private String obtenerValorCelda(Row row, Map<String, Integer> mapeoColumnas, String nombreColumna) {
        Integer index = mapeoColumnas.get(nombreColumna);
        if (index == null) {
            return null;
        }

        Cell cell = row.getCell(index);
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return null;
        }
    }


*/
/**
     * Obtiene el valor booleano de una celda
     *//*


    private boolean obtenerValorBooleano(Cell cell) {
        switch (cell.getCellType()) {
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case STRING:
                String valor = cell.getStringCellValue().trim().toLowerCase();
                return valor.equals("si") || valor.equals("sí") || valor.equals("s") ||
                        valor.equals("true") || valor.equals("verdadero") || valor.equals("1");
            case NUMERIC:
                return cell.getNumericCellValue() > 0;
            default:
                return false;
        }
    }
}*/
