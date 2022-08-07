package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Usuario;

import java.util.Arrays;
import java.lang.Double;
import java.util.Date;
import java.util.List;

import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.utils.DataUtils;

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
        locacao.setDataLocacao(new Date());
        locacao.setDataRetorno(DataUtils.obterDataComDiferencaDias(1));
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

    public LocacaoBuilder comDataLocacao(Date dataLocacao) {
        locacao.setDataLocacao(dataLocacao);
        return this;
    }

    public LocacaoBuilder comDataRetorno(Date dataRetorno) {
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
