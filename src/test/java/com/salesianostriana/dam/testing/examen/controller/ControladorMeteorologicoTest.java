package com.salesianostriana.dam.testing.examen.controller;

import com.salesianostriana.dam.testing.examen.dto.GetItemMediaFechaPoblacionDto;
import com.salesianostriana.dam.testing.examen.dto.GetMediaFechaPoblacionDto;
import com.salesianostriana.dam.testing.examen.service.ServicioMeteorologico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@WebMvcTest
public class ControladorMeteorologicoTest {

    @MockBean
    ServicioMeteorologico servicioMeteorologico;

    String ciudad;
    GetMediaFechaPoblacionDto getMediaFechaPoblacionDto;

    List<GetItemMediaFechaPoblacionDto> items;
    GetItemMediaFechaPoblacionDto getItemMediaFechaPoblacionDto;
    GetItemMediaFechaPoblacionDto getItemMediaFechaPoblacionDto1;
    @BeforeEach
    void setUp(){
        ciudad = "Sanlúcar";
        getItemMediaFechaPoblacionDto = new GetItemMediaFechaPoblacionDto("2021-04-23)", 32.5);
        getItemMediaFechaPoblacionDto1 = new GetItemMediaFechaPoblacionDto("2020-06-13)", 52.9);
        items = List.of(getItemMediaFechaPoblacionDto,getItemMediaFechaPoblacionDto1);
        getMediaFechaPoblacionDto = new GetMediaFechaPoblacionDto(ciudad, items);
    }

    @Test
    void whenCiudadThenReturnGetMediaFechaPoblacionDtoHttp200() throws Exception{
                mvc.perform(MockMvcRequestBuilders.get("/meteo/media/mes/"+ciudad)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    @Test whenCiudadNotFoundTherReturnHttp200() throws Exception{
        String ciudadEmpty = "";
        mvc.perform(MockMvcRequestBuilders.get("/meteo/media/mes/"+ciudadEmpty)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().NOT_FOUND);
    }

}
