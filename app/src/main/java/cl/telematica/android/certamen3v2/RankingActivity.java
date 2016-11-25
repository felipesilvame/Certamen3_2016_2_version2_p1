package cl.telematica.android.certamen3v2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cl.telematica.android.certamen3v2.adapters.RankingAdapter;
import cl.telematica.android.certamen3v2.models.RankingModel;
import cl.telematica.android.certamen3v2.presenters.RankingPresenterImpl;
import cl.telematica.android.certamen3v2.presenters.contract.RankingPresenter;
import cl.telematica.android.certamen3v2.views.RankingView;

public class RankingActivity extends AppCompatActivity implements RankingView{

    private RecyclerView recyclerView;
    //private ListView listView;
    private RankingAdapter userAdapter;
    private RankingPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        mPresenter = new RankingPresenterImpl(this);
        manageExtras();
        mPresenter.getNameList();
        //listView = (ListView) findViewById(R.id.rankingList);
        recyclerView = (RecyclerView) findViewById(R.id.rankingList);
        userAdapter = new RankingAdapter(mPresenter.getNameList());
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager repoLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(repoLayoutManager);
        recyclerView.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();

    }

    @Override
    public void manageExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String name = extras.getString("name");
            int score = extras.getInt("score");
            RankingModel user = new RankingModel(name,score);
            mPresenter.addUser(user);
        }
    }
}
