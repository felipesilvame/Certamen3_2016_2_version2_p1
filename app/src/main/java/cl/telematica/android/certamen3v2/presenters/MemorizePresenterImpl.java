package cl.telematica.android.certamen3v2.presenters;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import java.util.List;

import javax.crypto.Mac;

import cl.telematica.android.certamen3v2.MemorizeActivity;
import cl.telematica.android.certamen3v2.R;
import cl.telematica.android.certamen3v2.RankingActivity;
import cl.telematica.android.certamen3v2.adapters.GridAdapter;
import cl.telematica.android.certamen3v2.interfaces.DialogListener;
import cl.telematica.android.certamen3v2.models.CardModel;
import cl.telematica.android.certamen3v2.presenters.contract.MemorizePresenter;
import cl.telematica.android.certamen3v2.utils.MessageFactory;
import cl.telematica.android.certamen3v2.utils.Utils;

/**
 * Created by Pipos on 25-11-2016.
 */

public class MemorizePresenterImpl implements MemorizePresenter{
    private MemorizeActivity mActivity;
    private List<CardModel> cards;
    private int counter;
    private int lastPosition;
    private int pairsCounter;
    private int score;
    private CardModel lastCard;

    public MemorizePresenterImpl(MemorizeActivity mActivity){
        this.mActivity = mActivity;
        counter = 0;
        lastPosition = 0;
        pairsCounter = 0;
        score = 0;
    }

    @Override
    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public int getCounter() {
        return counter;
    }

    @Override
    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    @Override
    public int getLastPosition() {
        return lastPosition;
    }

    @Override
    public void setPairsCounter(int pairsCounter) {
        this.pairsCounter = pairsCounter;
    }

    @Override
    public int getPairsCounter() {
        return pairsCounter;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public List<CardModel> getCards() {
        counter = 0;
        lastPosition = 0;
        pairsCounter = 0;
        score = 0;
        cards = Utils.createCards();
        return cards;
    }

    @Override
    public void checkCards(CardModel card, int position) {
        if(++counter == 2 && lastCard != null){
            if(lastCard.getCard() == card.getCard()){
                if(++pairsCounter == 8){
                    Dialog dialog = MessageFactory.createDialog(mActivity, new DialogListener() {

                        @Override
                        public void onCancelPressed(DialogInterface dialog) {
                            dialog.dismiss();
                            mActivity.setGridData();
                        }

                        @Override
                        public void onAcceptPressed(DialogInterface dialog, String name) {
                            if(!Utils.isEmptyOrNull(name)){
                                dialog.dismiss();
                                /**
                                 * Call to the ranking
                                 */
                                Intent intent = new Intent(mActivity, RankingActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("score", score);
                                mActivity.setGridData();
                                mActivity.startActivity(intent);
                            } else {
                                mActivity.showDialogDownText();
                            }
                        }
                    });
                    dialog.show();
                } else {
                   mActivity.showWinnedText();
                }
                score = score + 2;
                counter = 0;
                lastCard = null;
                mActivity.changeScoreText();

            } else {
                mActivity.setBadText();
                executePostDelayed(card, position);
            }
        } else {
            lastCard = card;
            lastPosition = position;
        }
    }
    private void executePostDelayed(final CardModel card, final int position){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                lastCard.setSelected(false);
                card.setSelected(false);

                cards.set(lastPosition, lastCard);
                cards.set(position, card);

                mActivity.dataSetChanged();

                counter = 0;
                lastCard = null;

                score--;

                mActivity.changeScoreText();
            }
        }, 500);
    }

}
