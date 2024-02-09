package com.salesianostriana.dam.testing.examen.service;

import com.salesianostriana.dam.testing.examen.model.DatoMeteorologico;
import com.salesianostriana.dam.testing.examen.model.DatoMeterologicoPK;
import com.salesianostriana.dam.testing.examen.repo.DatoMeteorologicoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log
@Service
@RequiredArgsConstructor
public class CargadorDatosMeteorologicos {

    @Value("${data.dir}")
    private String pathDataDirectory;

    public static final String DELIMITADOR = ";";

    private final DatoMeteorologicoRepository repository;

    private List<Path> ficherosCsvEnDirectorio() {
        try (Stream<Path> stream = Files.list(Paths.get(pathDataDirectory))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .filter(file -> file.toString().substring(file.toString().lastIndexOf(".")+1).equalsIgnoreCase("csv"))
                    .toList();
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public long cargarDatos() {
        log.info("Iniciando proceso de carga");
        List<Path> ficheros = ficherosCsvEnDirectorio();
        AtomicLong contador = new AtomicLong();

        if (!ficheros.isEmpty()) {
            ficheros.forEach(fichero -> {

                List<DatoMeteorologico> data = null;
                try {
                    data = Files.lines(fichero)
                            .map(linea -> {
                                String[] partes = linea.split(DELIMITADOR);
                                return DatoMeteorologico.builder()
                                        .id(new DatoMeterologicoPK(partes[2], LocalDate.parse(partes[0], DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
                                        .precipitacion(Double.parseDouble(partes[1]))
                                        .build();
                            })
                            .filter(dm -> !repository.existePorFechaPoblacion(dm.getId().getFecha(), dm.getId().getCiudad()))
                            .peek(dm -> log.info("Dato meteorológico a cargar -> %s %s %.2f".formatted(dm.getId().getCiudad(), dm.getId().getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),dm.getPrecipitacion())))
                            .collect(Collectors.toList());

                    repository.saveAll(data);
                    contador.addAndGet(data.size());

                } catch (IOException e) {
                    log.info("Error al procesar el fichero csv %s".formatted(fichero.getFileName().toString()));
                }



            });
        } else {
            log.info("No hay ficheros que procesar");
        }

        return contador.get();


    }

}
