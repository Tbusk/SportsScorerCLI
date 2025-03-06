package edu.ferris.seng350.midtermproject.interfaces;

public interface Subject<T extends Record> {

    void subscribe(Observer<T> observer);
    void unsubscribe(Observer<T> observer);
    void notifySubscribers() throws Exception;

}
