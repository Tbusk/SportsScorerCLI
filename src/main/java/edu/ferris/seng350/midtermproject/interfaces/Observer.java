package edu.ferris.seng350.midtermproject.interfaces;

import edu.ferris.seng350.midtermproject.exceptions.GameException;

public interface Observer <T extends Record> {

    void update(T record) throws GameException;

}
