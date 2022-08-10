package br.ce.wcaquino.servicos;

import br.ce.wcaquino.builders.LocacaoBuilder;
import br.ce.wcaquino.dao.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DateUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static br.ce.wcaquino.builders.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builders.LocacaoBuilder.*;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static br.ce.wcaquino.matchers.MatcherProprio.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LocacaoServiceTest {
    private LocacaoService locacaoService;
    private SPCService spcService;
    private LocacaoDAO locacaoDAO;
    private EmailService emailService;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setup() {
        // Inicialização
        this.locacaoService = new LocacaoService();

        // Criação dos Mocks
        this.spcService = mock(SPCService.class);
        this.locacaoDAO = mock(LocacaoDAO.class);
        this.emailService = mock(EmailService.class);

        // Injeção de Dependência
        this.locacaoService.setLocacaoDAO(this.locacaoDAO);
        this.locacaoService.setSpcService(this.spcService);
        this.locacaoService.setEmailService(this.emailService);
    }

    @Test
    public void deveAlugarFilmeComSucesso() throws Exception {

        //Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        // Cenário
        Usuario usuario = umUsuario().agora();
        List<Filme> filme = List.of(umFilme().comValor(5.0).agora());

        // Ação
        Locacao locacao = locacaoService.alugarFilme(usuario, filme);

        // Verificação
        Assert.assertEquals(5.0, locacao.getValor(), 0.01);

        Assert.assertTrue(locacao.getDataLocacao().isEqual(LocalDate.now()));
        errorCollector.checkThat(locacao.getDataLocacao(), ehHoje());

        Assert.assertTrue(locacao.getDataRetorno().isEqual(DateUtils.obterDataComDiferencaDeDias(LocalDate.now(), 1)));
        errorCollector.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDeDias(1));

        errorCollector.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
        errorCollector.checkThat(locacao.getDataRetorno().isEqual(LocalDate.now()), CoreMatchers.is(false));
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void deveLancarExcecaoAlugarFilmeSemEstoqueFormaElegante() throws Exception {
        Usuario usuario = umUsuario().agora();
        List<Filme> filme = List.of(umFilme().semEstoque().agora());

        // Ação
        locacaoService.alugarFilme(usuario, filme);
    }

    @Test
    public void deveLancarExcecaoAlugarFilmeSemEstoqueFormaRobusta() {
        Usuario usuario = umUsuario().agora();
        List<Filme> filme = List.of(umFilme().semEstoque().comValor(5.0).agora());

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
        Usuario usuario = umUsuario().agora();
        List<Filme> filme = List.of(umFilme().semEstoque().agora());

        Assert.assertThrows(FilmeSemEstoqueException.class, () -> locacaoService.alugarFilme(usuario, filme));
    }

    @Test
    public void naoDeveAlugarFilmeSemUsuario() {
        List<Filme> filme = List.of(umFilme().agora());

        // Ação
        Assert.assertThrows(LocadoraException.class, () -> locacaoService.alugarFilme(null, filme));
    }

    @Test
    public void naoDeveAlugarFilmeSemFilme() {
        Usuario usuario = umUsuario().agora();

        // Ação
        Assert.assertThrows(LocadoraException.class, () -> locacaoService.alugarFilme(usuario, null));
    }

    @Test
    public void deveDarDesconto25ppNoFilme3() throws Exception {
        // Cenário
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = List.of(umFilme().comNome("Filme1").agora(), umFilme().comNome("Filme2").agora(), umFilme().comNome("Filme3").agora());
        // Ação
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

        // Verificação
        Assert.assertEquals(11.00, locacao.getValor(), 0.0);
    }

    @Test
    public void deveDarDesconto50ppNoFilme4() throws Exception {
        // Cenário
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = List.of(umFilme().comNome("Filme1").agora(), umFilme().comNome("Filme2").agora(), umFilme().comNome("Filme3").agora(), umFilme().comNome("Filme4").agora());
        // Ação
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

        // Verificação
        Assert.assertEquals(13.00, locacao.getValor(), 0.0);
    }

    @Test
    public void deveDarDesconto75ppNoFilme5() throws Exception {
        // Cenário
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = List.of(umFilme().comNome("Filme1").agora(), umFilme().comNome("Filme2").agora(), umFilme().comNome("Filme3").agora(), umFilme().comNome("Filme4").agora(), umFilme().comNome("Filme5").agora());
        // Ação
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

        // Verificação
        Assert.assertEquals(14.00, locacao.getValor(), 0.0);
    }

    @Test
    public void deveDarDesconto100ppNoFilme6() throws Exception {
        // Cenário
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = List.of(umFilme().comNome("Filme1").agora(), umFilme().comNome("Filme2").agora(), umFilme().comNome("Filme3").agora(), umFilme().comNome("Filme4").agora(), umFilme().comNome("Filme5").agora(), umFilme().comNome("Filme6").agora());
        // Ação
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

        // Verificação
        Assert.assertEquals(14.00, locacao.getValor(), 0.0);
    }

    @Test
    public void naoDeveDevolverFilmeNoDomingo() throws Exception {
        // Cenário
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = List.of(umFilme().agora());
        // Ação
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
        // Verificação
        boolean ehSegundaFeira = DateUtils.verificarDiaSemana(locacao.getDataRetorno().getDayOfWeek());
        errorCollector.checkThat(locacao.getDataRetorno().getDayOfWeek(), caiNumaSegunda());
        Assert.assertTrue(ehSegundaFeira);
    }

    @Test
    public void naoDeveAlugarFilmeParaNegativadoSPC() {
        // Cenário
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = List.of(umFilme().agora());
        when(this.spcService.possuiNegativacao(usuario)).thenReturn(true);

        // Ação
        Assert.assertThrows(LocadoraException.class, () -> this.locacaoService.alugarFilme(usuario, filmes));

        // Verificação
        verify(this.spcService).possuiNegativacao(usuario);
    }

    @Test
    public void deveEnviarEmailParaLocacoesAtrasadas(){
        // Cenário
        Usuario usuario = umUsuario().agora();
        List<Locacao> locacoes = List.of(umLocacao().comUsuario(usuario).comDataLocacao(LocalDate.now().minusDays(2)).agora());
        when(this.locacaoDAO.obterLocacoesPendentes()).thenReturn(locacoes);
        // Ação
        this.locacaoService.notificarAtrasos();
        // Verificação
        verify(this.emailService).notificarAtraso(usuario);
    }
}