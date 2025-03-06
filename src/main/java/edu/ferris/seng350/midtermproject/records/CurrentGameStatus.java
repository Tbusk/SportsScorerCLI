package edu.ferris.seng350.midtermproject.records;

public record CurrentGameStatus(
        int teamOneScore,
        int teamTwoScore,
        Team teamOne,
        Team teamTwo,
        int pointDifference,
        boolean gameEnded
) {
}
