package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

public class LocacaoService {

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
        locacao.setValor(this.obterValorLocacao(listaFilmes));

        //Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = adicionarDias(dataEntrega, 1);

        if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)){
            dataEntrega = adicionarDias(dataEntrega, 1);
        }

        locacao.setDataRetorno(dataEntrega);

        //Salvando a locação...
        //TODO adicionar método para salvar

        return locacao;
    }

    private double obterValorLocacao(List<Filme> filmes) {
        double soma = 0.0;
        for (int i = 0; i < filmes.size(); i++) {
            Filme filme = filmes.get(i);
            double precoLocacao = filme.getPrecoLocacao();

            switch (i){
                case 2: precoLocacao *= 0.75; break;
                case 3: precoLocacao *= 0.5; break;
                case 4: precoLocacao *= 0.25; break;
                case 5: precoLocacao *= 0.0; break;
            }

            soma += precoLocacao;
        }
        return soma;
    }
}