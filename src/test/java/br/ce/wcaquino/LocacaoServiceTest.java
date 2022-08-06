package br.ce.wcaquino;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.servicos.LocacaoService;
import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.Date;

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
    public void testeLocacao() throws Exception {
        // Cenário
        locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Usuário 1");
        Filme filme = new Filme("Filme 1", 2, 5.0);

        // Ação
        Locacao locacao = locacaoService.alugarFilme(usuario, filme);

        // Verificação
        Assert.assertEquals(5.0, locacao.getValor(), 0.01);
        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));

        errorCollector.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
        errorCollector.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void testeLocacaoFilmeSemEstoqueElegante() throws Exception {
        Usuario usuario = new Usuario("Usuário 1");
        Filme filme = new Filme("Filme 1", 0, 5.0);

        // Ação
        locacaoService.alugarFilme(usuario, filme);
    }

    @Test
    public void testeLocacaoFilmeSemEstoqueRobusta() {
        Usuario usuario = new Usuario("Usuário 1");
        Filme filme = new Filme("Filme 1", 0, 5.0);

        // Ação
        try {
            locacaoService.alugarFilme(usuario, filme);
            Assert.fail("Deveria ter lançado uma exceção!");
        } catch (Exception e) {
            Assert.assertEquals("Filme sem estoque!", e.getMessage());
        }
    }

    @Test
    public void testeLocacaoFilmeSemEstoqueNova() {
        Usuario usuario = new Usuario("Usuário 1");
        Filme filme = new Filme("Filme 1", 0, 5.0);

        Assert.assertThrows(FilmeSemEstoqueException.class, () -> locacaoService.alugarFilme(usuario, filme));
    }

    @Test
    public void testeLocacaoUsuarioVazio() {
        Filme filme = new Filme("Filme 1", 2, 5.0);

        // Ação
        Assert.assertThrows(LocadoraException.class, () -> locacaoService.alugarFilme(null, filme));
    }

    @Test
    public void testeLocacaoFilmeVazio() {
        Usuario usuario = new Usuario("Marcelo");

        // Ação
        Assert.assertThrows(LocadoraException.class, () -> locacaoService.alugarFilme(usuario, null));
    }
}