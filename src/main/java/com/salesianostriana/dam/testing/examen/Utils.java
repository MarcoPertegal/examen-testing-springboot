package com.salesianostriana.dam.testing.examen;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class Utils {

    public static String fechaAMes(LocalDate date) {
        return date.getMonth().getDisplayName(TextStyle.FULL, new Locale("es")).toUpperCase();
    }

    public static String fechaADiaSemana(LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es")).toUpperCase();
    }

}
