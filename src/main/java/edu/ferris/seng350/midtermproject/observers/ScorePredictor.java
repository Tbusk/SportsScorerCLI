package edu.ferris.seng350.midtermproject.observers;

import edu.ferris.seng350.midtermproject.records.Team;
import edu.ferris.seng350.midtermproject.interfaces.Observer;
import edu.ferris.seng350.midtermproject.records.CurrentGameStatus;

public class ScorePredictor implements Observer<CurrentGameStatus> {

    private int predictionsCorrect = 0;
    private int totalPredictions = 0;

    @Override
    public void update(CurrentGameStatus event) {

        String latestPrediction = getPredictedWinner(event.teamOne().name(), event.teamTwo().name());

        if(event.gameEnded() ) {
            Team winningTeam = event.teamOneScore() > event.teamTwoScore() ? event.teamOne() : event.teamTwo();

            predictionsCorrect += winningTeam.name().equals(latestPrediction) ? 1 : 0;

            totalPredictions++;
        }

    }

    public int getWinHistory(String teamName) {
        return ScoreTracker.teamScores.getOrDefault(teamName, 0);
    }

    public int getPredictionsCorrect() {
        return predictionsCorrect;
    }

    public int getTotalPredictions() {
        return totalPredictions;
    }

    public String getPredictedWinner(String teamOneName, String teamTwoName) {

        int teamOneWinHistory = getWinHistory(teamOneName);
        int teamTwoWinHistory = getWinHistory(teamTwoName);

        return teamOneWinHistory >= teamTwoWinHistory ? teamOneName : teamTwoName;
    }
}
