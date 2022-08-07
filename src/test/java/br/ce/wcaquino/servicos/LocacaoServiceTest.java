package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static br.ce.wcaquino.matchers.MatcherProprio.*;

public class LocacaoServiceTest {
    private LocacaoService locacaoService;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setup() {
        // Cenário
        locacaoService = new LocacaoService();
    }

    @Test
    public void deveAlugarFilmeComSucesso() throws Exception {

        //Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        // Cenário
        locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Usuário 1");
        List<Filme> filme = List.of(new Filme("Filme 1", 2, 5.0));

        // Ação
        Locacao locacao = locacaoService.alugarFilme(usuario, filme);

        // Verificação
        Assert.assertEquals(5.0, locacao.getValor(), 0.01);

        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
        errorCollector.checkThat(locacao.getDataLocacao(), ehHoje());

        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
        errorCollector.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDeDias(1));

        errorCollector.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
        errorCollector.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void deveLancarExcecaoAlugarFilmeSemEstoqueFormaElegante() throws Exception {
        Usuario usuario = new Usuario("Usuário 1");
        List<Filme> filme = List.of(new Filme("Filme 1", 0, 5.0));

        // Ação
        locacaoService.alugarFilme(usuario, filme);
    }

    @Test
    public void deveLancarExcecaoAlugarFilmeSemEstoqueFormaRobusta() {
        Usuario usuario = new Usuario("Usuário 1");
        List<Filme> filme = List.of(new Filme("Filme 1", 0, 5.0));

        // Ação
        try {
            locacaoService.alugarFilme(usuario, filme);
            Assert.fail("Deveria ter lançado uma exceção!");
        } catch (Exception e) {
            Assert.assertEquals("Filme sem estoque!", e.getMessage());
        }
    }

    @Test
    public void deveLancarExcecaoAlugarFilmeSemEstoqueFormaFinal() {
        Usuario usuario = new Usuario("Usuário 1");
        List<Filme> filme = List.of(new Filme("Filme 1", 0, 5.0));

        Assert.assertThrows(FilmeSemEstoqueException.class, () -> locacaoService.alugarFilme(usuario, filme));
    }

    @Test
    public void naoDeveAlugarFilmeSemUsuario() {
        List<Filme> filme = List.of(new Filme("Filme 1", 2, 5.0));

        // Ação
        Assert.assertThrows(LocadoraException.class, () -> locacaoService.alugarFilme(null, filme));
    }

    @Test
    public void naoDeveAlugarFilmeSemFilme() {
        Usuario usuario = new Usuario("Marcelo");

        // Ação
        Assert.assertThrows(LocadoraException.class, () -> locacaoService.alugarFilme(usuario, null));
    }

    @Test
    public void deveDarDesconto25ppNoFilme3() throws Exception {
        // Cenário
        Usuario usuario = new Usuario("Marcelo");
        List<Filme> filmes = List.of(new Filme("Filme 1", 2, 4.0),
                new Filme("Filme 2", 5, 4.0),
                new Filme("Filme 3", 8, 4.0));
        // Ação
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

        // Verificação
        Assert.assertEquals(11.00, locacao.getValor(), 0.0);
    }

    @Test
    public void deveDarDesconto50ppNoFilme4() throws Exception {
        // Cenário
        Usuario usuario = new Usuario("Marcelo");
        List<Filme> filmes = List.of(new Filme("Filme 1", 2, 4.0),
                new Filme("Filme 2", 5, 4.0),
                new Filme("Filme 3", 8, 4.0),
                new Filme("Filme 4", 1, 4.0));
        // Ação
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

        // Verificação
        Assert.assertEquals(13.00, locacao.getValor(), 0.0);
    }

    @Test
    public void deveDarDesconto75ppNoFilme5() throws Exception {
        // Cenário
        Usuario usuario = new Usuario("Marcelo");
        List<Filme> filmes = List.of(new Filme("Filme 1", 2, 4.0),
                new Filme("Filme 2", 5, 4.0),
                new Filme("Filme 3", 8, 4.0),
                new Filme("Filme 4", 1, 4.0),
                new Filme("Filme 5", 2, 4.0));
        // Ação
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

        // Verificação
        Assert.assertEquals(14.00, locacao.getValor(), 0.0);
    }

    @Test
    public void deveDarDesconto100ppNoFilme6() throws Exception {
        // Cenário
        Usuario usuario = new Usuario("Marcelo");
        List<Filme> filmes = List.of(new Filme("Filme 1", 2, 4.0),
                new Filme("Filme 2", 5, 4.0),
                new Filme("Filme 3", 8, 4.0),
                new Filme("Filme 4", 1, 4.0),
                new Filme("Filme 5", 2, 4.0),
                new Filme("Filme 6", 2, 4.0));
        // Ação
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

        // Verificação
        Assert.assertEquals(14.00, locacao.getValor(), 0.0);
    }

    @Test
    public void naoDeveDevolverFilmeNoDomingo() throws Exception {
        // Cenário
        Usuario usuario = new Usuario("Marcelo");
        List<Filme> filmes = List.of(new Filme("Filme 1", 2, 4.0));
        // Ação
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
        // Verificação
        boolean ehSegundaFeira = DataUtils.verificarDiaSemana(locacao.getDataRetorno(), Calendar.MONDAY);
        errorCollector.checkThat(locacao.getDataRetorno(), caiNumaSegunda());
        Assert.assertTrue(ehSegundaFeira);
    }
}