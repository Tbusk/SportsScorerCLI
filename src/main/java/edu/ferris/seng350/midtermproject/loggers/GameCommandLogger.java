package edu.ferris.seng350.midtermproject.loggers;

import edu.ferris.seng350.midtermproject.observers.ScoreTracker;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GameCommandLogger {

    private static final Logger log = LoggerFactory.getLogger(GameCommandLogger.class);

    @AfterReturning(value = "execution(void edu.ferris.seng350.midtermproject.commands.SportsGame.start(..))")
    public void logGameStart() {
        log.info("Sports Game Started.");
    }

    @AfterReturning(value = "execution(void edu.ferris.seng350.midtermproject.commands.SportsGame.end(..))")
    public void logGameEnd() {
        log.info("Sports Game Ended.");
    }

    @AfterReturning(value = "execution(void edu.ferris.seng350.midtermproject.commands.SportsGame.addScores(..)) && args(teamOneName, teamOneScore, teamTwoName, teamTwoScore)", argNames = "teamOneName, teamOneScore, teamTwoName, teamTwoScore")
    public void logGameScoreAdd(String teamOneName, int teamOneScore, String teamTwoName, int teamTwoScore) {
        log.info("{} added {} points, {} added {} points", teamOneName, teamOneScore, teamTwoName, teamTwoScore);
    }

    @AfterReturning(value = "execution(void edu.ferris.seng350.midtermproject.commands.SportsGame.simulateQuarter(..))")
    public void logSimulateQuarter() {
        log.info("Quarter has been simulated and scores have been added.");
    }

    @AfterReturning(value = "execution(void edu.ferris.seng350.midtermproject.commands.SportsGame.populateTeams(..))")
    public void logPopulateTeams() {
        log.info("Teams have been populated. There are now {} teams.", ScoreTracker.teams.size());
    }
}
