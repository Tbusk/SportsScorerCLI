package edu.ferris.seng350.midtermproject.exceptions;

public class TeamAlreadyExistsException extends GameException {

    public TeamAlreadyExistsException(String teamName) {
        super(String.format("Team %s already exists.", teamName));
    }
}
