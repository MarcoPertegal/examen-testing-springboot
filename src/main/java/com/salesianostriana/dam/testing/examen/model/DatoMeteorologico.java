package com.salesianostriana.dam.testing.examen.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class DatoMeteorologico {

    @EmbeddedId
    private DatoMeterologicoPK id;

    private double precipitacion;

}
