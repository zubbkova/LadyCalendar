package co.sunclick.ladycalendar.Helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import co.sunclick.ladycalendar.Model.Mood;

/**
 * Created by OZ on 15.05.16.
 */
public class MoodRepo {

    private DBHelper dbHelper;

    public MoodRepo(Context context){

        dbHelper = new DBHelper(context);
    }

    public ArrayList<Mood> getMoodsList(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + Mood.TABLE;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Mood> list = new ArrayList<>();
        while (cursor.moveToNext()){
            list.add(new Mood(cursor.getInt(cursor.getColumnIndex(Mood.KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(Mood.KEY_TITLE)),
                    cursor.getString(cursor.getColumnIndex(Mood.KEY_ICON)),
                    cursor.getInt(cursor.getColumnIndex(Mood.KEY_IS_VISIBLE)),
                    cursor.getInt(cursor.getColumnIndex(Mood.KEY_IS_USER)),
                    cursor.getInt(cursor.getColumnIndex(Mood.KEY_LAST_USING))));
        }
        return list;
    }

    public ArrayList<Mood> getVisibleMoodsList(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT " +
                Mood.KEY_ID + "," +
                Mood.KEY_TITLE + "," +
                Mood.KEY_ICON + "," +
                Mood.KEY_IS_USER + "," +
                Mood.KEY_LAST_USING +
                " FROM " + Mood.TABLE +
                " WHERE " + Mood.KEY_IS_VISIBLE + " = 1";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Mood> list = new ArrayList<>();
        while (cursor.moveToNext()){
            list.add(new Mood(cursor.getInt(cursor.getColumnIndex(Mood.KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(Mood.KEY_TITLE)),
                    cursor.getString(cursor.getColumnIndex(Mood.KEY_ICON)),
                    1,
                    cursor.getInt(cursor.getColumnIndex(Mood.KEY_IS_USER)),
                    cursor.getInt(cursor.getColumnIndex(Mood.KEY_LAST_USING))));
        }
        return list;
    }

    public ArrayList<Mood> getLastUsingMoodsList(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT TOP 10 " +
                Mood.KEY_ID + "," +
                Mood.KEY_TITLE + "," +
                Mood.KEY_ICON + "," +
                Mood.KEY_IS_USER + "," +
                Mood.KEY_LAST_USING +
                " FROM " + Mood.TABLE +
                " WHERE " + Mood.KEY_IS_VISIBLE + " = 1" +
                " ORDER BY " + Mood.KEY_LAST_USING + " DESC";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Mood> list = new ArrayList<>();
        while (cursor.moveToNext()){
            list.add(new Mood(cursor.getInt(cursor.getColumnIndex(Mood.KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(Mood.KEY_TITLE)),
                    cursor.getString(cursor.getColumnIndex(Mood.KEY_ICON)),
                    1,
                    cursor.getInt(cursor.getColumnIndex(Mood.KEY_IS_USER)),
                    cursor.getInt(cursor.getColumnIndex(Mood.KEY_LAST_USING))));
        }
        return list;
    }

    public Mood setLastUsing(Mood mood){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "UPDATE " + Mood.TABLE +
                " SET " + Mood.KEY_LAST_USING + " = ?" +
                " WHERE " + Mood.KEY_ID + " = ?";
        String[] parameters = new String[2];
        parameters[0] = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        parameters[1] = String.valueOf(mood.getMoodId());
        try {
            db.execSQL(query, parameters);
            mood.setLastUsing(Integer.getInteger(parameters[0]));
            return mood;
        }catch (Exception ex){
            return null;
        }
    }

    public boolean setIsVisible(int moodId, boolean isVisible){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "UPDATE " + Mood.TABLE +
                " SET " + Mood.KEY_IS_VISIBLE + " = ?" +
                " WHERE " + Mood.KEY_ID + " = ?";
        String[] parameters = new String[2];
        parameters[0] = isVisible ? "1" : "0";
        parameters[1] = String.valueOf(moodId);
        try {
            db.execSQL(query, parameters);
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    public Mood addMood(String title, String icon){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "INSERT INTO " + Mood.TABLE +
                " (" + Mood.KEY_TITLE + "," + Mood.KEY_ICON + "," + Mood.KEY_IS_VISIBLE + "," + Mood.KEY_IS_USER + ")" +
                " VALUES (?,?,?,?)";
        String[] parameters = new String[4];
        parameters[0] = title;
        parameters[1] = icon;
        parameters[2] = "1";
        parameters[3] = "1";
        try {
            db.execSQL(query, parameters);
            query = "SELECT last_insert_rowid() as id";
            Cursor cursor = db.rawQuery(query, null);
            int moodId = cursor.getInt(cursor.getColumnIndex("id"));
            return new Mood(moodId,title,icon,1,1,0);
        } catch (Exception ex){
            return null;
        }
    }

    public boolean editMood(int moodId, String title, String icon){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "UPDATE " + Mood.TABLE +
                " SET " + Mood.KEY_TITLE + " = ?, " + Mood.KEY_ICON + " = ?" +
                " WHERE " + Mood.KEY_ID + " = ?";
        String[] parameters = new String[3];
        parameters[0] = title;
        parameters[1] = icon;
        parameters[2] = String.valueOf(moodId);
        try {
            db.execSQL(query, parameters);
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    public boolean deleteMood(Mood mood){

        if (!mood.getIsUser()) return false;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "DELETE FROM " + Mood.TABLE +
                " WHERE " + Mood.KEY_ID + " = " + mood.getMoodId();
        try {
            db.execSQL(query);
            return true;
        } catch (Exception ex){
            return false;
        }
    }
}
