package cl.telematica.android.certamen3v2.presenters;

import java.util.ArrayList;
import java.util.List;

import cl.telematica.android.certamen3v2.RankingActivity;
import cl.telematica.android.certamen3v2.models.RankingModel;
import cl.telematica.android.certamen3v2.models.RankingTable;
import cl.telematica.android.certamen3v2.presenters.contract.RankingPresenter;

/**
 * Created by Pipos on 25-11-2016.
 */

public class RankingPresenterImpl implements RankingPresenter {
    private List<RankingModel> nameList;
    private RankingTable db;
    private RankingActivity mActivity;

    public RankingPresenterImpl(RankingActivity activity){
        nameList = new ArrayList<>();
        mActivity = activity;
        db = new RankingTable(mActivity.getApplicationContext());
    }
    public boolean addUser(RankingModel user){
        getDataFromDatabase();
        for(RankingModel e:nameList){
            if (e.getName().equals(user.getName()) && e.getScore() < user.getScore()){
                db.updateRanking(user);
                return true;
            } else if (e.getName().equals(user.getName()) && e.getScore() < user.getScore()){
                return false;
            }
        }
        db.addToRanking(user);
        return true;
    }
    @Override
    public void getDataFromDatabase() {
        nameList = db.getAllRanking();
    }
    public List<RankingModel> getNameList(){
        getDataFromDatabase();
        return nameList;
    }
}
