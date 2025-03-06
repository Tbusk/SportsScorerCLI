package edu.ferris.seng350.midtermproject.records;

public record GameCompletion(
        String teamOneName,
        String teamTwoName,
        int teamOneScore,
        int teamTwoScore,
        String winningTeamName,
        String losingTeamName
) {
}
