package com.salesianostriana.dam.testing.examen.service;


import com.salesianostriana.dam.testing.examen.dto.EditDatoMeteorologicoDto;
import com.salesianostriana.dam.testing.examen.exception.RepeatedValueException;
import com.salesianostriana.dam.testing.examen.exception.ResourceNotFoundException;
import com.salesianostriana.dam.testing.examen.model.DatoMeteorologico;
import com.salesianostriana.dam.testing.examen.model.DatoMeterologicoPK;
import com.salesianostriana.dam.testing.examen.repo.DatoMeteorologicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.salesianostriana.dam.testing.examen.Utils.fechaADiaSemana;
import static com.salesianostriana.dam.testing.examen.Utils.fechaAMes;

@Service
@RequiredArgsConstructor
public class ServicioMeteorologico {

    private final DatoMeteorologicoRepository repository;


    public List<DatoMeteorologico> obtenerTodos() {
        List<DatoMeteorologico> result = repository.findAll();

        if (result.isEmpty())
            throw new ResourceNotFoundException();

        return result;

    }

    /**
     * Método que calcula la media de precipitación de una población
     * para todos los meses de los que se tienen datos
     * @param poblacion
     * @return Un map donde cada entrada es un mes representado como texto y
     * como valor la media de precipitación
     */
    public Map<String, Double> mediaMensual(String poblacion) {

        List<DatoMeteorologico> filtradosPorPoblacion =
                repository.buscarPorPoblacion(poblacion);

        if (filtradosPorPoblacion.isEmpty())
            throw new ResourceNotFoundException();

        Map<String, List<Double>> aux = new HashMap<>();
        Map<String, Double> result = new HashMap<>();

        for(DatoMeteorologico dato : filtradosPorPoblacion) {
            if (!aux.containsKey(fechaAMes(dato.getId().getFecha()))) {
                aux.put(fechaAMes(dato.getId().getFecha()), new ArrayList<>());
                aux.get(fechaAMes(dato.getId().getFecha())).add(dato.getPrecipitacion());
            } else {
                aux.get(fechaAMes(dato.getId().getFecha())).add(dato.getPrecipitacion());
            }
        }

        for (String mes : aux.keySet()) {
            double acumulador = 0;
            for (double precipitacion : aux.get(mes)) {
                acumulador += precipitacion;
            }
            double media = acumulador / aux.get(mes).size();
            result.put(mes, media);
        }

        return result;
    }

    /**
     * Método que inserta un nuevo dato meteorológico
     * @param nuevo Comprueba si ya está insertado
     * @return DatoMeteorologico insertado
     * @throws RepeatedValueException si el dato ya está insertado
     */

    public DatoMeteorologico insertar(EditDatoMeteorologicoDto nuevo) {

        String ciudad = nuevo.ciudad();
        LocalDate fecha = LocalDate.parse(nuevo.fecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        if (repository.existePorFechaPoblacion(fecha, ciudad))
            throw new RepeatedValueException();

        return repository.save(new DatoMeteorologico(new DatoMeterologicoPK(ciudad, fecha), nuevo.precipitacion()));

    }

    /**
     * Método que edita un  dato meteorológico existente
     * @param editado
     * @return DatoMeteorologico Modificado
     * @throws ResourceNotFoundException si no encuentra el dato a editar
     */

    public DatoMeteorologico editar(EditDatoMeteorologicoDto editado) {

        String ciudad = editado.ciudad();
        LocalDate fecha = LocalDate.parse(editado.fecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        if (!repository.existePorFechaPoblacion(fecha, ciudad))
            throw new ResourceNotFoundException();

        return repository.save(new DatoMeteorologico(new DatoMeterologicoPK(ciudad, fecha), editado.precipitacion()));

    }

    /**
     * Método que devuelve la media de precipitaciones para cada día de la semana para una población
     * @param poblacion
     * @return Mapa de tipo clave = Mes, valor = Media de precipitaciones de ese mes
     */
    public Map<String, Double> mediaDiaSemana(String poblacion) {
        List<DatoMeteorologico> filtrados =
                repository.buscarPorPoblacion(poblacion);

        Map<String, List<Double>> aux = new HashMap<>();
        Map<String, Double> result = new HashMap<>();

        for(DatoMeteorologico dato : filtrados) {
            if (!aux.containsKey(fechaADiaSemana(dato.getId().getFecha()))) {
                aux.put(fechaADiaSemana(dato.getId().getFecha()), new ArrayList<>());
                aux.get(fechaADiaSemana(dato.getId().getFecha())).add(dato.getPrecipitacion());
            } else {
                aux.get(fechaADiaSemana(dato.getId().getFecha())).add(dato.getPrecipitacion());
            }
        }

        for (String diaSemana : aux.keySet()) {
            double acumulador = 0;
            for (double precipitacion : aux.get(diaSemana)) {
                acumulador += precipitacion;
            }
            double media = acumulador / aux.get(diaSemana).size();
            result.put(diaSemana, media);
        }


        return result;
    }
}
