package br.ce.wcaquino.dao;

import br.ce.wcaquino.entidades.Locacao;

public interface LocacaoDAO {
    default void salvar(Locacao locacao) {

    }
}
