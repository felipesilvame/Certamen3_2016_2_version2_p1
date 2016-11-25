package cl.telematica.android.certamen3v2.presenters.contract;

import java.util.List;

import cl.telematica.android.certamen3v2.models.CardModel;

/**
 * Created by Pipos on 25-11-2016.
 */

public interface MemorizePresenter {
    void setCounter(int counter);
    int getCounter();
    void setLastPosition(int lastPosition);
    int getLastPosition();
    void setPairsCounter(int pairsCounter);
    int getPairsCounter();
    void setScore(int score);
    int getScore();
    List<CardModel> getCards();
    void checkCards(CardModel card, int position);
}
