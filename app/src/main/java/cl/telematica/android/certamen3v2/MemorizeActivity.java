package cl.telematica.android.certamen3v2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cl.telematica.android.certamen3v2.adapters.GridAdapter;
import cl.telematica.android.certamen3v2.interfaces.CardSelectedListener;
import cl.telematica.android.certamen3v2.interfaces.DialogListener;
import cl.telematica.android.certamen3v2.models.CardModel;
import cl.telematica.android.certamen3v2.presenters.MemorizePresenterImpl;
import cl.telematica.android.certamen3v2.utils.MessageFactory;
import cl.telematica.android.certamen3v2.utils.Utils;
import cl.telematica.android.certamen3v2.views.MemorizeView;

public class MemorizeActivity extends AppCompatActivity implements CardSelectedListener, MemorizeView {

    private GridView gridView;
    private TextView scoreText;
    private GridAdapter adapter;

    private MemorizePresenterImpl mPresenter;

    private static final String KEY_COUNTER = "Memorize::Counter";
    private static final String KEY_LAST_POSITION = "Memorize::LastPosition";
    private static final String KEY_PAIRS_COUNTER = "Memorize::PairsCounter";
    private static final String KEY_SCORE = "Memorize::Score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorize);
        mPresenter = new MemorizePresenterImpl(this);

        if ((savedInstanceState != null)){
            if(savedInstanceState.containsKey(KEY_COUNTER)) {
                mPresenter.setCounter(savedInstanceState.getInt(KEY_COUNTER));
            }
            if(savedInstanceState.containsKey(KEY_LAST_POSITION)) {
                mPresenter.setLastPosition(savedInstanceState.getInt(KEY_LAST_POSITION));
            }
            if(savedInstanceState.containsKey(KEY_PAIRS_COUNTER)) {
                mPresenter.setPairsCounter(savedInstanceState.getInt(KEY_PAIRS_COUNTER));
            }
            if(savedInstanceState.containsKey(KEY_SCORE)) {
                mPresenter.setScore(savedInstanceState.getInt(KEY_SCORE));
            }
        }


        createVariables();

        setGridData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.memorize, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.scoreButton:
                /**
                 * Call to the ranking*/
                Intent intent = new Intent(MemorizeActivity.this, RankingActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void createVariables(){
        gridView = (GridView) findViewById(R.id.cardsGrid);
        scoreText = (TextView) findViewById(R.id.scoreText);
    }

    @Override
    public void setGridData(){
        adapter = new GridAdapter(getApplicationContext(),
                R.id.scoreText,
                mPresenter.getCards(),
                this);
        gridView.setAdapter(adapter);

        changeScoreText();

    }

    @Override
    public void showDialogDownText() {
        Toast.makeText(getApplicationContext(),
                getString(R.string.dialog_down_text),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showWinnedText() {
        Toast.makeText(getApplicationContext(),
                getString(R.string.winned_text),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeScoreText() {
        String scoreString = String.format(getString(R.string.score_title),
                String.valueOf(mPresenter.getScore()));

        scoreText.setText(scoreString);
    }

    @Override
    public void setBadText() {
        scoreText.setText(getString(R.string.bad_text));
    }

    @Override
    public void dataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCardSelected(CardModel card, int position) {
        mPresenter.checkCards(card, position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_COUNTER, mPresenter.getCounter());
        outState.putInt(KEY_LAST_POSITION, mPresenter.getLastPosition());
        outState.putInt(KEY_PAIRS_COUNTER, mPresenter.getPairsCounter());
        outState.putInt(KEY_SCORE, mPresenter.getScore());
    }

}
