package edu.ferris.seng350.midtermproject.exceptions;

public class NotEnoughTeamsAvailableException extends GameException {

    public NotEnoughTeamsAvailableException(int numberOfTeams) {
        super(String.format("At least two teams are needed to start the game. Currently, there are %d teams.", numberOfTeams));
    }
}
