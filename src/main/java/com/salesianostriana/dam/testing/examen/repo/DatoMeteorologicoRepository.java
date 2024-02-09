package com.salesianostriana.dam.testing.examen.repo;

import com.salesianostriana.dam.testing.examen.model.DatoMeteorologico;
import com.salesianostriana.dam.testing.examen.model.DatoMeterologicoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DatoMeteorologicoRepository extends JpaRepository<DatoMeteorologico, DatoMeterologicoPK> {

    @Query("""
            select case when count(d)> 0 then true else false end 
            from DatoMeteorologico d
            where d.id.fecha = :fecha and d.id.ciudad = :ciudad
            """)
    boolean existePorFechaPoblacion(LocalDate fecha, String ciudad);


    @Query("""
        select d from DatoMeteorologico d where lower(d.id.ciudad) = lower(?1)
    """)
    List<DatoMeteorologico> buscarPorPoblacion(String poblacion);

}
