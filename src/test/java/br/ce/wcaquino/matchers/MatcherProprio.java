package br.ce.wcaquino.matchers;

import br.ce.wcaquino.utils.DataUtils;

import java.util.Calendar;

public class MatcherProprio {
    public static DiaSemanaMatcher caiNumaSegunda() {
        return new DiaSemanaMatcher(Calendar.MONDAY);
    }


    public static MesmaDataMatcher ehHojeComDiferencaDeDias(Integer dias) {
        return new MesmaDataMatcher(DataUtils.obterDataComDiferencaDias(dias));
    }

    public static MesmaDataMatcher ehHoje(){
        return new MesmaDataMatcher(DataUtils.obterDataComDiferencaDias(0));
    }
}
