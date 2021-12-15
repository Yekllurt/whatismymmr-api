package dev.yekllurt.whatismymmr.exception;

/**
 * Thrown when an unknown error recorded accessing the API (unknown status code)
 */
public class UnknownException extends Exception {

    public UnknownException(String message) {
        super(message);
    }

}
