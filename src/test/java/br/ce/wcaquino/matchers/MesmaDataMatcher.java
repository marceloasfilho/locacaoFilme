package br.ce.wcaquino.matchers;

import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Date;

public class MesmaDataMatcher extends TypeSafeMatcher<Date> {

    private final Date dataSomada;

    public MesmaDataMatcher(Date dataSomada) {
        this.dataSomada = dataSomada;
    }

    @Override
    protected boolean matchesSafely(Date data) {
        return DataUtils.isMesmaData(data, this.dataSomada);
    }

    @Override
    public void describeTo(Description description) {

    }
}
