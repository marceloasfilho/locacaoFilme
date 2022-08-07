package br.ce.wcaquino.exceptions;

public class FilmeSemEstoqueException extends Exception {

    private static final long serialVersionUID = 7811922476437213600L;

    public FilmeSemEstoqueException(String message) {
        super(message);
    }
}
