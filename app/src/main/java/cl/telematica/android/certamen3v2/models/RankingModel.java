package cl.telematica.android.certamen3v2.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pipos on 25-11-2016.
 */

public class RankingModel implements Parcelable {
    private int _id;
    private String _name;
    private int _score;

    public RankingModel(){} //Dummy Constructor

    public RankingModel(Parcel in) {
        _id = in.readInt();
        _name = in.readString();
        _score = in.readInt();

    }

    public RankingModel(int id, String name, int score){
        _id = id;
        _name = name;
        _score = score;
    }

    public RankingModel(String name, int score){
        _name = name;
        _score = score;
    }

    //setters / getters;
    public int getId(){
        return _id;
    }
    public void setId(int id){
        _id = id;
    }
    public String getName(){
        return _name;
    }
    public void setName(String name){
        _name = name;
    }
    public int getScore(){
        return _score;
    }
    public void setScore(int score){
        _score = score;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getName());
        dest.writeLong(getScore());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RankingModel> CREATOR = new Creator<RankingModel>() {
        @Override
        public RankingModel createFromParcel(Parcel in) {
            return new RankingModel(in);
        }

        @Override
        public RankingModel[] newArray(int size) {
            return new RankingModel[size];
        }
    };
}
