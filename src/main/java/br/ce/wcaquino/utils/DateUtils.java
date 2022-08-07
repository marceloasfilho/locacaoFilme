package br.ce.wcaquino.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateUtils {
    public static boolean verificarDiaSemana(DayOfWeek dia) {
        return !(dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY);
    }

    public static LocalDate obterDataComDiferencaDeDias(LocalDate data, int dias) {
        return dias < 0 ? data.minusDays(dias) : data.plusDays(dias);
    }
}
