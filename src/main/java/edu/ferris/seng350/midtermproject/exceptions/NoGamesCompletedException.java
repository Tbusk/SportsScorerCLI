package edu.ferris.seng350.midtermproject.exceptions;

public class NoGamesCompletedException extends GameException {
    public NoGamesCompletedException(String reason) {
        super(String.format("Cannot %s because no games have been finished.", reason));
    }
}
