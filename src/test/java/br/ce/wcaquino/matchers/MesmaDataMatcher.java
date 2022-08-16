package br.ce.wcaquino.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MesmaDataMatcher extends TypeSafeMatcher<LocalDate> {

    private final LocalDate dataSomada;

    public MesmaDataMatcher(LocalDate dataSomada) {
        this.dataSomada = dataSomada;
    }

    @Override
    protected boolean matchesSafely(LocalDate data) {
        return data.isEqual(this.dataSomada);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(this.dataSomada));
    }
}
