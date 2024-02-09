package com.salesianostriana.dam.testing.examen.init;


import com.salesianostriana.dam.testing.examen.security.user.CargadorUsuarios;
import com.salesianostriana.dam.testing.examen.service.CargadorDatosMeteorologicos;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log
public class InitData {

    private final CargadorDatosMeteorologicos cargadorDatosMeteorologicos;
    private final CargadorUsuarios cargadorUsuarios;
    @PostConstruct
    public void init() {
        log.info("Cargando datos meteorológicos");
        cargadorDatosMeteorologicos.cargarDatos();
        log.info("Cargando usuarios");
        cargadorUsuarios.cargarUsuarios();
    }

}
