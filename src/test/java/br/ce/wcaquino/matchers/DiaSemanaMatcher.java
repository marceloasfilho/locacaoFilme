package br.ce.wcaquino.matchers;

import br.ce.wcaquino.utils.DateUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

public class DiaSemanaMatcher extends TypeSafeMatcher<DayOfWeek> {

    private DayOfWeek dia;

    public DiaSemanaMatcher(DayOfWeek dia) {
        this.dia = dia;
    }

    @Override
    protected boolean matchesSafely(DayOfWeek dia) {
        return DateUtils.verificarDiaSemana(dia);
    }

    @Override
    public void describeTo(Description description) {
        String dataExtenso = dia.getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
        description.appendText(dataExtenso);
    }
}