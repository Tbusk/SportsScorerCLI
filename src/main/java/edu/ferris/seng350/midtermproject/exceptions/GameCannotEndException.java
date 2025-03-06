package edu.ferris.seng350.midtermproject.exceptions;

public class GameCannotEndException extends GameException {

  public GameCannotEndException(String message) {
    super(String.format("Game cannot end because %s.", message));
  }
}
