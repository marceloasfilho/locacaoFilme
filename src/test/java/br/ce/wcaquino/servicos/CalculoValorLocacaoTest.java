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
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {
    @InjectMocks
    private LocacaoService locacaoService;

    @Mock
    private SPCService spcService;
    @Mock
    private LocacaoDAO locacaoDAO;
    @Parameterized.Parameter
    public List<Filme> listaFilmes;
    @Parameterized.Parameter(value = 1)
    public Double valorLocacao;

    @Parameterized.Parameter(value = 2)
    public String cenario;

    @Before
    public void setup() {
        openMocks(this);
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
        // Cen??rio
        Usuario usuario = new Usuario("Usu??rio qualquer");
        // A????o
        Locacao locacao = locacaoService.alugarFilme(usuario, listaFilmes);
        // Verifica????o
        Assert.assertEquals(locacao.getValor(), valorLocacao, 0.0);
    }
}