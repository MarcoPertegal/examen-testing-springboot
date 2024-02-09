package com.salesianostriana.dam.testing.examen.controller;

import com.salesianostriana.dam.testing.examen.dto.EditDatoMeteorologicoDto;
import com.salesianostriana.dam.testing.examen.dto.GetDatoMeteoDto;
import com.salesianostriana.dam.testing.examen.dto.GetMediaFechaPoblacionDto;
import com.salesianostriana.dam.testing.examen.service.ServicioMeteorologico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meteo")
@RequiredArgsConstructor
public class ControladorMeteorologico {

    private final ServicioMeteorologico servicio;


    @GetMapping("/")
    public List<GetDatoMeteoDto> obtenerTodos() {
        return
                servicio.obtenerTodos()
                        .stream().map(GetDatoMeteoDto::of).toList();
    }


    @GetMapping("/media/mes/{ciudad}")
    public GetMediaFechaPoblacionDto mediaMensualPorPoblacion(@PathVariable String ciudad) {
        return GetMediaFechaPoblacionDto.of(ciudad, servicio.mediaMensual(ciudad));
    }


    @GetMapping("/media/semana/{ciudad}")
    public GetMediaFechaPoblacionDto mediaSemanalPorPoblacion(@PathVariable String ciudad) {
        return GetMediaFechaPoblacionDto.of(ciudad, servicio.mediaDiaSemana(ciudad));
    }

    @PostMapping("/add")
    public GetDatoMeteoDto add(@Valid @RequestBody EditDatoMeteorologicoDto nuevo) {
        return GetDatoMeteoDto.of(servicio.insertar(nuevo));
    }


    @PutMapping("/edit")
    public GetDatoMeteoDto edit(@Valid @RequestBody EditDatoMeteorologicoDto editado) {
        return GetDatoMeteoDto.of(servicio.editar(editado));
    }



}
