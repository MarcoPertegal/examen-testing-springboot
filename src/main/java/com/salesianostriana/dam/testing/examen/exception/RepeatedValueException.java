package com.salesianostriana.dam.testing.examen.exception;

public class RepeatedValueException extends RuntimeException {

    public RepeatedValueException() {
        super("Ya existe un registro para la ciudad y fecha indicados");
    }

}
