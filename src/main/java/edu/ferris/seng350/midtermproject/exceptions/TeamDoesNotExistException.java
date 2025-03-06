package edu.ferris.seng350.midtermproject.exceptions;

public class TeamDoesNotExistException extends GameException {

    public TeamDoesNotExistException(String teamName) {
        super(String.format("Team %s does not exist.", teamName));
    }
}
