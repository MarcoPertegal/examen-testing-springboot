package com.salesianostriana.dam.testing.examen.security.jwt.errorhandling;

public class JwtTokenException extends RuntimeException{

    public JwtTokenException(String msg) {
        super(msg);
    }


}
