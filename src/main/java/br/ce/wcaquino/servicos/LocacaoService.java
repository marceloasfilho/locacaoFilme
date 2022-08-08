package br.ce.wcaquino.servicos;

import br.ce.wcaquino.dao.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class LocacaoService {
    private LocacaoDAO locacaoDAO;
    private SPCService spcService;
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

        if (this.spcService.possuiNegativacao(usuario)){
            throw new LocadoraException("Usuário negativado!");
        }

        Locacao locacao = new Locacao();
        locacao.setListaFilmes(listaFilmes);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(LocalDate.now());
        locacao.setValor(this.obterValorLocacao(listaFilmes));

        //Entrega no dia seguinte
        LocalDate dataEntrega = LocalDate.now().plusDays(1);

        if (dataEntrega.getDayOfWeek() == DayOfWeek.SUNDAY) {
            dataEntrega = dataEntrega.plusDays(1);
        }

        locacao.setDataRetorno(dataEntrega);

        //Salvando a locação...
        this.locacaoDAO.salvar(locacao);

        return locacao;
    }

    private double obterValorLocacao(List<Filme> filmes) {
        double soma = 0.0;
        for (int i = 0; i < filmes.size(); i++) {
            Filme filme = filmes.get(i);
            double precoLocacao = filme.getPrecoLocacao();

            switch (i) {
                case 2:
                    precoLocacao *= 0.75;
                    break;
                case 3:
                    precoLocacao *= 0.5;
                    break;
                case 4:
                    precoLocacao *= 0.25;
                    break;
                case 5:
                    precoLocacao *= 0.0;
                    break;
            }

            soma += precoLocacao;
        }
        return soma;
    }

    public void setLocacaoDAO(LocacaoDAO locacaoDAO) {
        this.locacaoDAO = locacaoDAO;
    }

    public void setSpcService (SPCService spcService){
        this.spcService = spcService;
    }
}