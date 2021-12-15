package dev.yekllurt.whatismymmr.exception;

/**
 * An exception that is thrown when trying to fetch data from a non-recorded summoner
 */
public class SummonerNotFoundException extends Exception {

    public SummonerNotFoundException(String message) {
        super(message);
    }

}
