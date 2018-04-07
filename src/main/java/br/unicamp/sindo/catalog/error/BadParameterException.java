package br.unicamp.sindo.catalog.error;

public class BadParameterException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadParameterException(String message) {
        super(message);
    }
}
