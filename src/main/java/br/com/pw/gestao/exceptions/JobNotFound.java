package br.com.pw.gestao.exceptions;

public class JobNotFound extends RuntimeException {
    public JobNotFound() {
        super("Essa vaga não existe");
    }
}
