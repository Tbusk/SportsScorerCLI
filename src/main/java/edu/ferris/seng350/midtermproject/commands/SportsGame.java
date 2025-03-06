package edu.ferris.seng350.midtermproject.commands;

import edu.ferris.seng350.midtermproject.constants.ConsoleColors;
import edu.ferris.seng350.midtermproject.Scoring;
import edu.ferris.seng350.midtermproject.exceptions.*;
import edu.ferris.seng350.midtermproject.observers.NewsHeadlineGenerator;
import edu.ferris.seng350.midtermproject.observers.ScorePredictor;
import edu.ferris.seng350.midtermproject.observers.ScoreTracker;
import edu.ferris.seng350.midtermproject.records.GameCompletion;
import org.springframework.shell.command.annotation.ExceptionResolver;
import org.springframework.shell.command.annotation.ExitCode;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

@ShellComponent
public class SportsGame {

    Scoring scoring;
    ScorePredictor scorePredictor;
    ScoreTracker scoreTracker;
    NewsHeadlineGenerator newsHeadlineGenerator;

    @ExceptionResolver({GameException.class})
    @ExitCode(code = 5)
    String handle(GameException exception) {
        return String.format("%s%s%s\n", ConsoleColors.RED, exception.getMessage(), ConsoleColors.DEFAULT);
    }

    public SportsGame() {
        this.newsHeadlineGenerator = new NewsHeadlineGenerator();
        this.scoreTracker = new ScoreTracker();
        this.scorePredictor = new ScorePredictor();
    }

    @ShellMethod("Start a sports game with random teams.")
    public void start() throws GameException {

        if(ScoreTracker.teams.size() < 2) throw new NotEnoughTeamsAvailableException(ScoreTracker.teams.size());

        if(scoring == null || scoring.hasGameEnded()) {

            int teamOneIndex = ScoreTracker.getRandomTeamIndex(-1);
            int teamTwoIndex = ScoreTracker.getRandomTeamIndex(teamOneIndex);

            scoring = new Scoring(ScoreTracker.teams.get(teamOneIndex), ScoreTracker.teams.get(teamTwoIndex));

            scoring.subscribe(newsHeadlineGenerator);
            scoring.subscribe(scorePredictor);
            scoring.subscribe(scoreTracker);
        } else {
            throw new GameStartException("a game has already started");
        }
    }

    @ShellMethod("End a sports game.")
    public void end() throws GameException {

        if(scoring == null) throw new GameCannotEndException("a game has not started");

        scoring.endGame();
        scoring.notifySubscribers();
    }

    @ShellMethod("Get latest score for the running sports game.")
    public void currentScore() throws GameException {

        if(scoring == null || scoring.hasGameEnded()) throw new GameHasNotStartedException("get latest score");

        System.out.println(scoring.getCurrentScore());
    }

    @ShellMethod("Add scores for last quarter.")
    public void addScores(String teamOneName, int teamOneScore, String teamTwoName, int teamTwoScore) throws GameException {

        if(scoring == null) throw new GameHasNotStartedException("add scores for last quarter");

        scoring.addTeamQuarterScore(teamOneName.toLowerCase(), teamOneScore, teamTwoName.toLowerCase(), teamTwoScore);
        scoring.notifySubscribers();
    }

    @ShellMethod("Get who is playing the sports game.")
    public void whoIsPlaying() throws GameException {

        if(scoring == null || scoring.hasGameEnded()) throw new GameHasNotStartedException("get who is playing");

        System.out.println(scoring.getTeamsInVsFormat());
    }

    @ShellMethod("Get team win history.")
    public void teamWinHistory(String teamName) throws GameException {
        System.out.println(scoreTracker.getTeamScore(teamName.toLowerCase()) + " wins.");
    }

    @ShellMethod("Get predicted winner of current game.")
    public void predictWinner() throws GameException {

        if(scoring == null || scoring.hasGameEnded()) throw new GameHasNotStartedException("get prediction");

        String[] teamNames = scoring.getTeamNames();
        System.out.printf("I predict %s will win.\n", scorePredictor.getPredictedWinner(teamNames[0], teamNames[1]));
    }

    @ShellMethod("Get prediction stats.")
    public void predictionStats() {

        double winPercentage = 100 * ((double) scorePredictor.getPredictionsCorrect() / (double) scorePredictor.getTotalPredictions());

        System.out.printf("Total games: %d\n", scorePredictor.getTotalPredictions());
        System.out.printf("Games predicted correctly: %d\n", scorePredictor.getPredictionsCorrect());
        System.out.printf("Accuracy: %.2f%%\n", Double.isNaN(winPercentage) ? 0 : winPercentage);
    }

    @ShellMethod("Get scores of all finished games.")
    public void finishedGamesTable() throws GameException {

        if(ScoreTracker.gameCompletions.isEmpty()) throw new NoGamesCompletedException("get finished games");

        List<GameCompletion> completedGames = ScoreTracker.gameCompletions;

        System.out.printf("%-32s\t%-10s\t%-32s\t%-10s\n", "Team", "Score", "Team", "Score");

        for(GameCompletion gameCompletion : completedGames) {

            System.out.printf("%-32s\t%-10d\t%-32s\t%-10d\n",
                    gameCompletion.teamOneName(),
                    gameCompletion.teamOneScore(),
                    gameCompletion.teamTwoName(),
                    gameCompletion.teamTwoScore()
            );
        }
    }

    @ShellMethod("Get news headlines for all finished games.")
    public void newsHeadlines() throws GameException {

        if(NewsHeadlineGenerator.newsHeadlines.isEmpty()) throw new NoGamesCompletedException("get news headlines");

        List<String> newsHeadlines = NewsHeadlineGenerator.newsHeadlines;

        for(String headline : newsHeadlines) {

            System.out.printf("%s\n", headline);

        }
    }

    @ShellMethod("Get latest news headline.")
    public void latestNews() throws GameException {

        if(newsHeadlineGenerator.getLatestNewsHeadline() == null) throw new NoGamesCompletedException("get latest news headline");

        System.out.println(newsHeadlineGenerator.getLatestNewsHeadline());
    }

    @ShellMethod("Simulate a quarter by adding random scores to each team.")
    public void simulateQuarter() throws GameException {

        if(scoring == null || scoring.hasGameEnded()) throw new GameHasNotStartedException("simulate quarter");

        Random random = new Random();

        scoring.addTeamQuarterScore(
                scoring.getTeamOne().name(),
                random.nextInt(51),
                scoring.getTeamTwo().name(),
                random.nextInt(51)
        );
    }

    @ShellMethod("Populate teams with stored data.")
    public void populateTeams() throws GameException, IOException {

        Path path = Path.of("team_names.txt"); // Source: https://github.com/Brescou/NBA-dataset-stats-player-team/blob/main/team/team_stats_defense_rs.csv
        List<String> teamNames = Files.readAllLines(path);

        for(String teamName : teamNames) {

            if(!ScoreTracker.teamScores.containsKey(teamName.toLowerCase())) ScoreTracker.addTeam(teamName);

        }
    }
}
