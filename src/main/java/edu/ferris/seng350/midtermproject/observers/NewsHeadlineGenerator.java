package edu.ferris.seng350.midtermproject.observers;


import edu.ferris.seng350.midtermproject.interfaces.Observer;
import edu.ferris.seng350.midtermproject.records.CurrentGameStatus;

import java.util.ArrayList;
import java.util.List;

public class NewsHeadlineGenerator implements Observer<CurrentGameStatus> {

    public static final List<String> newsHeadlines = new ArrayList<>();

    private String latestNewsHeadline;

    @Override
    public void update(CurrentGameStatus event) {

        if(event.gameEnded()) {

            String winner = event.teamOneScore() > event.teamTwoScore() ? event.teamOne().name() : event.teamTwo().name();
            String loser = event.teamOneScore() > event.teamTwoScore() ? event.teamTwo().name() : event.teamOne().name();

            String levelOfWin = event.pointDifference() > 10 ? "landslide" : "tightly contested";

            String newsHeadline = String.format("%s beat %s by %d points in a %s game.", winner, loser, event.pointDifference(), levelOfWin);

            latestNewsHeadline = newsHeadline;
            newsHeadlines.add(newsHeadline);
        }
    }

    public String getLatestNewsHeadline() {
        return latestNewsHeadline;
    }

}
