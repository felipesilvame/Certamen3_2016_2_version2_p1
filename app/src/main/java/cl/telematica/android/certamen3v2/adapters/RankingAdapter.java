package cl.telematica.android.certamen3v2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cl.telematica.android.certamen3v2.R;
import cl.telematica.android.certamen3v2.models.RankingModel;

/**
 * Created by Pipos on 25-11-2016.
 */

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MyViewHolder> {
    private List<RankingModel> nameList;

    public RankingAdapter(List<RankingModel> nameList){this.nameList=nameList;}

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView position, name, score;

        public MyViewHolder(View view){
            super(view);
            position = (TextView) view.findViewById(R.id.rankingText);
            name = (TextView) view.findViewById(R.id.nameText);
            score = (TextView) view.findViewById(R.id.scoreText);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ranking_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final RankingModel user = nameList.get(position);
        holder.name.setText(user.getName());
        holder.position.setText(String.valueOf(position+1));
        holder.score.setText(String.valueOf(user.getScore()));
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }



}
