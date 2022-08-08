package br.ce.wcaquino.servicos;

import br.ce.wcaquino.dao.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {
    private LocacaoService locacaoService;
    private SPCService spcService;

    @Parameterized.Parameter
    public List<Filme> listaFilmes;
    @Parameterized.Parameter(value = 1)
    public Double valorLocacao;

    @Parameterized.Parameter(value = 2)
    public String cenario;

    @Before
    public void setup() {
        // Inicialização
        locacaoService = new LocacaoService();

        // Criação de Mocks
        LocacaoDAO locacaoDAO = Mockito.mock(LocacaoDAO.class);
        this.spcService = Mockito.mock(SPCService.class);

        // Injeção de Dependência
        locacaoService.setLocacaoDAO(locacaoDAO);
        locacaoService.setSpcService(this.spcService);
    }

    public static final Filme filme1 = new Filme("filme1", 2, 4.0);
    public static final Filme filme2 = new Filme("filme2", 2, 4.0);
    public static final Filme filme3 = new Filme("filme3", 2, 4.0);
    public static final Filme filme4 = new Filme("filme4", 2, 4.0);
    public static final Filme filme5 = new Filme("filme5", 2, 4.0);
    public static final Filme filme6 = new Filme("filme6", 2, 4.0);

    @Parameterized.Parameters(name = "{2}")
    public static Collection<Object[]> getParametros() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList(filme1, filme2, filme3), 11.0, "3 Filmes: 25%"},
                {Arrays.asList(filme1, filme2, filme3, filme4), 13.0, "4 Filmes: 50%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0, "5 Filmes: 75%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "6 Filmes: 100%"}
        });
    }

    @Test
    public void deveCalcularValorLocacaoConsiderandoDescontos() throws Exception {
        // Cenário
        Usuario usuario = new Usuario("Usuário qualquer");
        // Ação
        Locacao locacao = locacaoService.alugarFilme(usuario, listaFilmes);
        // Verificação
        Assert.assertEquals(locacao.getValor(), valorLocacao, 0.0);
    }
}