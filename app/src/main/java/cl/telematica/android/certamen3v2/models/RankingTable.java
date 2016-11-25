package cl.telematica.android.certamen3v2.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by Pipos on 25-11-2016.
 */

public class RankingTable extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "memorize";
    private static final String TABLE_RANKING = "ranking";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SCORE = "score";

    public RankingTable(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RANKING_TABLE = "CREATE TABLE " + TABLE_RANKING + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_SCORE + " INTEGER" +  ")";
        db.execSQL(CREATE_RANKING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RANKING);
        // Create tables again
        onCreate(db);
    }

    public void addToRanking(RankingModel user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName()); // Name
        values.put(KEY_SCORE, user.getScore()); //Score
        //insert
        db.insert(TABLE_RANKING,null,values);
        db.close();
    }

    public List<RankingModel> getAllRanking(){
        List<RankingModel> nameList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_RANKING + " ORDER BY "+ KEY_SCORE + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                RankingModel user = new RankingModel();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setScore(cursor.getInt(2));
                // Adding user to list
                nameList.add(user);
            } while (cursor.moveToNext());
        }

        // return ranking list
        return nameList;

    }

    public int updateRanking(RankingModel user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_SCORE, user.getScore());
        // updating row
        return db.update(TABLE_RANKING, values, KEY_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
    }
    public void deleteAllRanking(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RANKING,null,null);
        db.close();
    }
    public int getRankingCount(){
        String countQuery = "SELECT  * FROM " + TABLE_RANKING;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
}
