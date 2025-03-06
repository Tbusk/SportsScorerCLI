package edu.ferris.seng350.midtermproject.observers;

import edu.ferris.seng350.midtermproject.records.Team;
import edu.ferris.seng350.midtermproject.exceptions.TeamAlreadyExistsException;
import edu.ferris.seng350.midtermproject.exceptions.TeamDoesNotExistException;
import edu.ferris.seng350.midtermproject.interfaces.Observer;
import edu.ferris.seng350.midtermproject.records.CurrentGameStatus;
import edu.ferris.seng350.midtermproject.records.GameCompletion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ScoreTracker implements Observer<CurrentGameStatus> {

    public static final HashMap<String, Integer> teamScores = new HashMap<>();
    public static final List<Team> teams = new ArrayList<>();
    public static List<GameCompletion> gameCompletions = new ArrayList<>();

    @Override
    public void update(CurrentGameStatus event) {
        if(event.gameEnded()) {

            String winningTeamName = getWinningTeamName(event);
            String losingTeamName = getLosingTeamName(event);

            teamScores.put(winningTeamName, teamScores.get(winningTeamName) + 1);
            gameCompletions.add(createGameCompletionRecord(event, winningTeamName, losingTeamName));
        }
    }

    private static GameCompletion createGameCompletionRecord(CurrentGameStatus event, String winningTeamName, String losingTeamName) {
        return new GameCompletion(
                event.teamOne().name(),
                event.teamTwo().name(),
                event.teamOneScore(),
                event.teamTwoScore(),
                winningTeamName,
                losingTeamName
        );
    }

    private static String getLosingTeamName(CurrentGameStatus event) {
        return event.teamOneScore() < event.teamTwoScore() ? event.teamOne().name() : event.teamTwo().name();
    }

    private String getWinningTeamName(CurrentGameStatus event) {
        return event.teamOneScore() > event.teamTwoScore() ? event.teamOne().name() : event.teamTwo().name();
    }

    public static void addTeam(String teamName) throws TeamAlreadyExistsException {

        if(teamScores.containsKey(teamName.toLowerCase())) throw new TeamAlreadyExistsException(teamName);

        teams.add(new Team(teamName.toLowerCase()));
        teamScores.putIfAbsent(teamName.toLowerCase(), 0);
    }

    public int getTeamScore(String teamName) throws TeamDoesNotExistException {
        Integer score = teamScores.get(teamName);

        if(score == null) throw new TeamDoesNotExistException(teamName);

        return score;
    }

    public static int getRandomTeamIndex(int otherTeamIndex) {
        Random random = new Random();

        int randomIndex = random.nextInt(ScoreTracker.teams.size());

        while(randomIndex == otherTeamIndex) {
            randomIndex = random.nextInt(ScoreTracker.teams.size());
        }

        return randomIndex;
    }
}
