package edu.ferris.seng350.midtermproject.exceptions;

public class GameHasNotStartedException extends GameException {

    public GameHasNotStartedException(String reason) {
        super(String.format("Cannot %s because a game has not started yet.", reason));
    }
}
