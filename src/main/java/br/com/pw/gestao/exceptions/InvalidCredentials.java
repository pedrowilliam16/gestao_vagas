package br.com.pw.gestao.exceptions;

public class InvalidCredentials extends RuntimeException {
    public InvalidCredentials () {
        super("Username/Password incorret");
    }
}
