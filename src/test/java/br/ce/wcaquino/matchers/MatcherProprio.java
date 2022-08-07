package br.ce.wcaquino.matchers;

import br.ce.wcaquino.utils.DateUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class MatcherProprio {
    public static DiaSemanaMatcher caiNumaSegunda() {
        return new DiaSemanaMatcher(DayOfWeek.MONDAY);
    }


    public static MesmaDataMatcher ehHojeComDiferencaDeDias(Integer dias) {
        return new MesmaDataMatcher(DateUtils.obterDataComDiferencaDeDias(LocalDate.now(), dias));
    }

    public static MesmaDataMatcher ehHoje() {
        return new MesmaDataMatcher(DateUtils.obterDataComDiferencaDeDias(LocalDate.now(), 0));
    }
}
