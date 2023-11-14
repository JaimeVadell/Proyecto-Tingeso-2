package com.microservice.gestorAcademico.Services;

import com.microservice.gestorAcademico.Entities.Prueba;
import com.microservice.gestorAcademico.Model.Estudiante;
import com.microservice.gestorAcademico.Repositories.PruebaRepository;
import com.microservice.gestorAcademico.Utils.BuscadorEstudiante;
import com.microservice.gestorAcademico.Utils.VerificadorRut;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PruebaService {
    @Autowired
    PruebaRepository pruebaRepository;

    @Autowired
    BuscadorEstudiante buscadorEstudiante;

    @Data
    @Builder
    public static class PuntajeFecha {
        private int puntaje;
        private LocalDate fecha;
    }



    @Transactional(rollbackOn = Exception.class)
    public void RevisarDocumentoExamen(MultipartFile archivo) {
        try {
            InputStream fileInputStream = archivo.getInputStream();
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0); // Suponiendo que tu hoja de datos está en la primera pestaña

            Map<String, PuntajeFecha> estudianteMap = new HashMap<>();
            LocalDate fechaBase = null;
            String rutEstudiante;
            int lastRowNum = sheet.getLastRowNum();

            // Iniciar un índice para recorrer las filas
            int rowIndex = 0;
            for (Row row : sheet) {
                Cell c = row.getCell(0);
                if (row == null || c == null || c.getCellType() == CellType.BLANK) {
                    break; // Salir del bucle cuando se alcance la última fila
                }

                if (row.getPhysicalNumberOfCells() != 3) {
                    throw new IllegalArgumentException("Error: Hay una fila que tiene mas de 3 columnas.");

                }

                String rut = row.getCell(0).getStringCellValue();
                rutEstudiante = VerificadorRut.devolverRutParseado(rut);
                if (rutEstudiante.equals("")) {
                    throw  new IllegalArgumentException("Error: Rut inválido - " + rut);

                }

                if (buscadorEstudiante.buscarEstudiantePorRut(rutEstudiante) == null) {
                    throw new IllegalArgumentException("Error: Estudiante no existe - " + rut);
                }

                Date fechaFormatoDate = row.getCell(1).getDateCellValue();
                LocalDate fecha = fechaFormatoDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                System.out.println(fecha);

                int puntaje = (int) row.getCell(2).getNumericCellValue();

                // Verificar que el rut no se repita
                if (estudianteMap.containsKey(rut)) {
                    throw  new IllegalArgumentException("Error: Estudiante duplicado - Rut: " + rut);

                }

                // Verificar que el puntaje esté en el rango correcto
                if (puntaje < 0 || puntaje > 1000) {
                    throw new IllegalArgumentException("Error: Puntaje fuera de rango - Rut: " + rut + ", Puntaje: " + puntaje);
                }

                // Verificar que las fechas sean iguales
                if (fechaBase == null) {
                    fechaBase = fecha;
                    if(fechaBase.isAfter(LocalDate.now()) || fechaBase.isBefore(LocalDate.now().minusMonths(1))){
                        throw new IllegalArgumentException("Error: Fecha fuera de Rango:" + fechaBase);
                    }
                } else if (!fecha.equals(fechaBase)) {
                    throw  new IllegalArgumentException("Error: Fechas diferentes - Rut: " + rut);
                }
                PuntajeFecha puntajeFechaEstudiante = PuntajeFecha.builder()
                        .fecha(fecha)
                        .puntaje(puntaje)
                        .build();
                estudianteMap.put(rut, puntajeFechaEstudiante);
            }

            fileInputStream.close();
            workbook.close();
            guardarPruebasEstudiante(estudianteMap);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void guardarPruebasEstudiante(Map<String, PuntajeFecha> estudianteMap)  {
        for (Map.Entry<String, PuntajeFecha> entry : estudianteMap.entrySet()) {
            String rutEstudiante = entry.getKey();
            PuntajeFecha puntajeFecha = entry.getValue();
            int puntaje = puntajeFecha.getPuntaje();
            LocalDate fecha = puntajeFecha.getFecha();
            Estudiante estudiante = buscadorEstudiante.buscarEstudiantePorRut(rutEstudiante);
            if(estudiante == null) {
                throw new IllegalArgumentException("Error: Estudiante no existe - Rut: " + rutEstudiante);

            }
            rutEstudiante = estudiante.getRut();
            if(!verificarPruebaMesEstudiante(rutEstudiante,fecha)){
                throw new IllegalArgumentException("Error: Estudiante ya tiene una prueba este mes - Rut: " + rutEstudiante);
            }
            Prueba prueba = Prueba.builder()
                    .puntaje(puntaje)
                    .diaPrueba(fecha)
                    .rutEstudiante(rutEstudiante)
                    .build();
            pruebaRepository.save(prueba);
        }
        //Pruebas guardas exitosamente
    }

    // Falta hacer el reembolso pertinente en el caso de que el estudiante ya haya pagado
    private boolean verificarPruebaMesEstudiante(String rutEstudiante, LocalDate fechaPrueba){

        List<Prueba> pruebasEstudiante = pruebaRepository.findAllByRutEstudianteOrderByDiaPruebaAsc(rutEstudiante);
        // Una prueba por mes de estudiante
        for(Prueba pruebaEstudiante: pruebasEstudiante){
            LocalDate fechaPruebaAntigua = pruebaEstudiante.getDiaPrueba();
            int anioPruebaAntigua = fechaPruebaAntigua.getYear();
            int mesPruebaAntigua = fechaPruebaAntigua.getMonthValue();
            if(fechaPrueba.getYear() == anioPruebaAntigua && fechaPrueba.getMonthValue() == mesPruebaAntigua){
                return false;
            }
        }
        return true;
    }


    public List<Prueba> obtenerPruebasEstudiante(String rutEstudiante){
        Estudiante estudiante = buscadorEstudiante.buscarEstudiantePorRut(rutEstudiante);
        if(estudiante == null){
            throw new IllegalArgumentException("Error: Estudiante no existe - Rut: " + rutEstudiante);
        }
        rutEstudiante = estudiante.getRut();
        return pruebaRepository.findAllByRutEstudianteOrderByDiaPruebaAsc(rutEstudiante);
    }


}
