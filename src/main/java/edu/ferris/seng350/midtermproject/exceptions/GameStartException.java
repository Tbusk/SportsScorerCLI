package edu.ferris.seng350.midtermproject.exceptions;

public class GameStartException extends GameException{
    public GameStartException(String reason) {
        super(String.format("Cannot start a game because %s.", reason));
    }
}
