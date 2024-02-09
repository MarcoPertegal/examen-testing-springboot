package com.salesianostriana.dam.testing.examen.service;

import com.salesianostriana.dam.testing.examen.exception.ResourceNotFoundException;
import com.salesianostriana.dam.testing.examen.model.DatoMeteorologico;
import com.salesianostriana.dam.testing.examen.model.DatoMeterologicoPK;
import com.salesianostriana.dam.testing.examen.repo.DatoMeteorologicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ServicioMeteorologicoTest {
    @InjectMocks

    ServicioMeteorologico servicioMeteorologico;
    @Mock
    DatoMeteorologicoRepository datoMeteorologicoRepository;
    String poblacion;
    List<DatoMeteorologico> filtradosPorPoblacion;
    DatoMeteorologico datoMeteorologico;
    DatoMeteorologico datoMeteorologico1;
    DatoMeterologicoPK datoMeterologicoPK;
    DatoMeterologicoPK datoMeterologicoPK1;

    Map<String, Double> mediaMensual;
    @BeforeEach
    void setUp(){
        poblacion= "Sanlúcar";
        datoMeterologicoPK = new DatoMeterologicoPK("Sanlúcar", LocalDate.of(2017,12,03));
        datoMeterologicoPK1 = new DatoMeterologicoPK("Ubeda", LocalDate.of(2018,10,26));
        datoMeteorologico = new DatoMeteorologico(datoMeterologicoPK, 20);
        datoMeteorologico1 = new DatoMeteorologico(datoMeterologicoPK1, 30);
        filtradosPorPoblacion = List.of(datoMeteorologico1, datoMeteorologico);

    }

    /*
    @Test
    void whenPostDtoIsPresentThenReturnCreatedPost(){
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.ofNullable(category));
        Mockito.when(postService.mapToEntity(postDto)).thenReturn(post);
        Mockito.when(postRepository.save(post)).thenReturn(post);
        Mockito.when(postService.mapToDTO(post)).thenReturn(postDto);

        PostDto result = postService.createPost(postDto);
        assertNotNull(result);
        assertEquals("a",result.getTitle());
    }*/

    @Test
    void whenPoblacionFoundAndNotEmptyThenReturnMonthAndValue(){
        Mockito.when(datoMeteorologicoRepository.buscarPorPoblacion(poblacion)).thenReturn(filtradosPorPoblacion);
        Map<String, Double> result = servicioMeteorologico.mediaMensual(poblacion);
        assertEquals("{DICIEMBRE=20.0, OCTUBRE=30.0}",result.toString());
    }
    @Test
    void whenPoblacionNotFoundAndEmptyThenReturnResourceNotFoundException(){
        String poblacionEmpty = "";
        Mockito.when(datoMeteorologicoRepository.buscarPorPoblacion(poblacionEmpty)).thenReturn(filtradosPorPoblacion);
        Map<String, Double> result = servicioMeteorologico.mediaMensual(poblacion);
        assertThrows(result, ResourceNotFoundException.class);
    }

}
