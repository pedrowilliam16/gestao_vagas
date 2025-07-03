package br.com.pw.gestao.exceptions;

public class UserNotFound extends RuntimeException {
    public UserNotFound() {
      super("Usuário não existe");
    }
}
