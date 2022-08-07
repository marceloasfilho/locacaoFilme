package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Filme;

public class FilmeBuilder {
    private Filme filme;

    private FilmeBuilder() {
    }

    public static FilmeBuilder umFilme() {
        FilmeBuilder builder = new FilmeBuilder();
        builder.filme = new Filme();
        builder.filme.setNome("Filme 1");
        builder.filme.setEstoque(2);
        builder.filme.setPrecoLocacao(4.00);
        return builder;
    }

    public FilmeBuilder semEstoque() {
        this.filme.setEstoque(0);
        return this;
    }

    public FilmeBuilder comValor(Double novoValor) {
        this.filme.setPrecoLocacao(novoValor);
        return this;
    }

    public Filme agora() {
        return this.filme;
    }

    public FilmeBuilder comNome(String filme) {
        this.filme.setNome(filme);
        return this;
    }
}
