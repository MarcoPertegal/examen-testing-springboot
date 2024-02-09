package com.salesianostriana.dam.testing.examen.dto;

import java.util.List;
import java.util.Map;

public record GetMediaFechaPoblacionDto(
        String ciudad,
        List<GetItemMediaFechaPoblacionDto> items
) {

    public static GetMediaFechaPoblacionDto of (String ciudad, Map<String, Double> datos) {
        return new GetMediaFechaPoblacionDto(ciudad,
                datos.keySet()
                        .stream()
                        .map(k -> new GetItemMediaFechaPoblacionDto(k, datos.get(k)))
                        .toList()
                );
    }


}
