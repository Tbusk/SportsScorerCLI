package edu.ferris.seng350.midtermproject;


import edu.ferris.seng350.midtermproject.exceptions.GameCannotEndException;
import edu.ferris.seng350.midtermproject.exceptions.GameException;
import edu.ferris.seng350.midtermproject.interfaces.Observer;
import edu.ferris.seng350.midtermproject.interfaces.Subject;
import edu.ferris.seng350.midtermproject.records.CurrentGameStatus;
import edu.ferris.seng350.midtermproject.records.Team;

import java.util.ArrayList;

public class Scoring implements Subject<CurrentGameStatus> {

    ArrayList<Observer<CurrentGameStatus>> observers = new ArrayList<>();
    private final Team teamOne;
    private final Team teamTwo;
    private int teamOneScore, teamTwoScore;
    private boolean gameEnded;

    public Scoring(Team teamOne, Team teamTwo) {

        this.teamOne = teamOne;
        this.teamTwo = teamTwo;

        teamOneScore = 0;
        teamTwoScore = 0;

        gameEnded = false;
    }

    @Override
    public void subscribe(Observer<CurrentGameStatus> observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(Observer<CurrentGameStatus> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifySubscribers() throws GameException {
        for(Observer<CurrentGameStatus> observer : observers){

            CurrentGameStatus event = new CurrentGameStatus(teamOneScore, teamTwoScore, teamOne, teamTwo, getPointDifference(), gameEnded);

            observer.update(event);
        }
    }

    public void addTeamQuarterScore(String teamOneName, int teamOneScore, String teamTwoName, int teamTwoScore) {

        if(teamOne.name().equals(teamOneName)) this.teamOneScore += teamOneScore;
        if(teamOne.name().equals(teamTwoName)) this.teamOneScore += teamTwoScore;

        if(teamTwo.name().equals(teamTwoName)) this.teamTwoScore += teamTwoScore;
        if(teamTwo.name().equals(teamOneName)) this.teamTwoScore += teamOneScore;

    }

    public void endGame() throws GameException {

        if(teamOneScore == teamTwoScore) throw new GameCannotEndException("there is a tie. Keep playing");

        if(gameEnded) throw new GameCannotEndException("the game had already ended");

        gameEnded = true;
    }

    public int getPointDifference() {
        return Math.abs(teamOneScore - teamTwoScore);
    }

    public boolean hasGameEnded() {
        return gameEnded;
    }

    public String getCurrentScore() {
        return String.format("%s(%d) to %s(%d)", teamOne.name(), teamOneScore, teamTwo.name(), teamTwoScore);
    }

    public String getTeamsInVsFormat() {
        return String.format("%s vs %s", teamOne.name(), teamTwo.name());
    }

    public String[] getTeamNames() {
        return new String[]{teamOne.name(), teamTwo.name()};
    }

    public Team getTeamOne() {
        return teamOne;
    }

    public Team getTeamTwo() {
        return teamTwo;
    }
}
