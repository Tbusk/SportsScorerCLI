package edu.ferris.seng350.midtermproject.commands;

import edu.ferris.seng350.midtermproject.constants.ConsoleColors;
import edu.ferris.seng350.midtermproject.exceptions.GameException;
import edu.ferris.seng350.midtermproject.exceptions.TeamAlreadyExistsException;
import edu.ferris.seng350.midtermproject.observers.ScoreTracker;
import org.springframework.shell.command.annotation.ExceptionResolver;
import org.springframework.shell.command.annotation.ExitCode;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SportsTeam {

    @ExceptionResolver({GameException.class})
    @ExitCode(code = 5)
    String handle(GameException exception) {
        return String.format("%s%s%s\n", ConsoleColors.RED, exception.getMessage(), ConsoleColors.DEFAULT);
    }

    @ShellMethod("Add a new team.")
    public void addTeam(String teamName) throws TeamAlreadyExistsException {
        ScoreTracker.addTeam(teamName);
    }
}
