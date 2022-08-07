package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class LocacaoBuilder {
    private Locacao locacao;

    private LocacaoBuilder() {
    }

    public static LocacaoBuilder umLocacao() {
        LocacaoBuilder builder = new LocacaoBuilder();
        inicializarDadosPadroes(builder);
        return builder;
    }

    public static void inicializarDadosPadroes(LocacaoBuilder builder) {
        builder.locacao = new Locacao();
        Locacao locacao = builder.locacao;


        locacao.setUsuario(UsuarioBuilder.umUsuario().agora());
        locacao.setListaFilmes(List.of(FilmeBuilder.umFilme().agora()));
        locacao.setDataLocacao(LocalDate.now());
        locacao.setDataRetorno(LocalDate.now().plusDays(1));
        locacao.setValor(4.0);
    }

    public LocacaoBuilder comUsuario(Usuario usuario) {
        locacao.setUsuario(usuario);
        return this;
    }

    public LocacaoBuilder comListaListaFilmes(Filme... params) {
        locacao.setListaFilmes(Arrays.asList(params));
        return this;
    }

    public LocacaoBuilder comDataLocacao(LocalDate dataLocacao) {
        locacao.setDataLocacao(dataLocacao);
        return this;
    }

    public LocacaoBuilder comDataRetorno(LocalDate dataRetorno) {
        locacao.setDataRetorno(dataRetorno);
        return this;
    }

    public LocacaoBuilder comValor(Double valor) {
        locacao.setValor(valor);
        return this;
    }

    public Locacao agora() {
        return locacao;
    }
}
