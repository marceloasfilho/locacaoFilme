package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

public class LocacaoService {

    public static void main(String[] args) {
        List<Filme> lista = List.of(new Filme("Filme 1", 2, 4.00), new Filme("Filme 2", 1, 8.00));

        double sum = lista.stream().mapToDouble(Filme::getPrecoLocacao).sum();
        boolean b = lista.stream().anyMatch(filme -> filme.getEstoque() == 0);

    }
    public Locacao alugarFilme(Usuario usuario, List<Filme> listaFilmes) throws Exception {

        if (usuario == null) {
            throw new LocadoraException("Usuário vazio!");
        }

        if (listaFilmes == null || listaFilmes.isEmpty()) {
            throw new LocadoraException("Lista de filmes vazia!");
        }

        if (listaFilmes.stream().anyMatch(filme -> filme.getEstoque() == 0)) {
            throw new FilmeSemEstoqueException("Filme sem estoque!");
        }

        Locacao locacao = new Locacao();
        locacao.setListaFilmes(listaFilmes);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(new Date());
        locacao.setValor(listaFilmes.stream().mapToDouble(Filme::getPrecoLocacao).sum());

        //Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = adicionarDias(dataEntrega, 1);
        locacao.setDataRetorno(dataEntrega);

        //Salvando a locação...
        //TODO adicionar método para salvar

        return locacao;
    }
}