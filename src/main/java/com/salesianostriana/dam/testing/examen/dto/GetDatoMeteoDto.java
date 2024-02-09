package com.salesianostriana.dam.testing.examen.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesianostriana.dam.testing.examen.model.DatoMeteorologico;

import java.time.LocalDate;

public record GetDatoMeteoDto(
        String ciudad,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate fecha,
        double precipitacion) {



    public static GetDatoMeteoDto of (DatoMeteorologico dm) {
        return new GetDatoMeteoDto(dm.getId().getCiudad(), dm.getId().getFecha(), dm.getPrecipitacion());
    }

}
