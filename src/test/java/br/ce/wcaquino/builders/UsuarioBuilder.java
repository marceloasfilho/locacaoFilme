package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Usuario;


public class UsuarioBuilder {
    private Usuario usuario;

    private UsuarioBuilder() {
    }

    public static UsuarioBuilder umUsuario() {
        UsuarioBuilder builder = new UsuarioBuilder();
        inicializarDadosPadroes(builder);
        return builder;
    }

    public static void inicializarDadosPadroes(UsuarioBuilder usuarioBuilder) {
        usuarioBuilder.usuario = new Usuario();
        Usuario usuario = usuarioBuilder.usuario;
        usuario.setNome("Usuário 1");
    }

    public UsuarioBuilder comNome(String nome) {
        this.usuario.setNome(nome);
        return this;
    }

    public Usuario agora() {
        return this.usuario;
    }
}
