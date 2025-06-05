package br.com.pw.gestao.exceptions;

public class UserFoundExists extends RuntimeException {
    public UserFoundExists() {
        super("Usuário já existe");
    }
}
