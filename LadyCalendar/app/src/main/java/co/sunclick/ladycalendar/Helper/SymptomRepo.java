package co.sunclick.ladycalendar.Helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import co.sunclick.ladycalendar.Model.Symptom;

/**
 * Created by OZ on 15.05.16.
 */
public class SymptomRepo {

    private DBHelper dbHelper;

    public SymptomRepo(Context context){

        dbHelper = new DBHelper(context);
    }

    public ArrayList<Symptom> getSymptomsList(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + Symptom.TABLE;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Symptom> list = new ArrayList<>();
        while (cursor.moveToNext()){
            list.add(new Symptom(cursor.getInt(cursor.getColumnIndex(Symptom.KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(Symptom.KEY_TITLE)),
                    cursor.getString(cursor.getColumnIndex(Symptom.KEY_ICON)),
                    cursor.getInt(cursor.getColumnIndex(Symptom.KEY_IS_VISIBLE)),
                    cursor.getInt(cursor.getColumnIndex(Symptom.KEY_IS_USER)),
                    cursor.getInt(cursor.getColumnIndex(Symptom.KEY_LAST_USING))));
        }
        return list;
    }

    public ArrayList<Symptom> getVisibleSymptomsList(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT " +
                Symptom.KEY_ID + "," +
                Symptom.KEY_TITLE + "," +
                Symptom.KEY_ICON + "," +
                Symptom.KEY_IS_USER + "," +
                Symptom.KEY_LAST_USING +
                " FROM " + Symptom.TABLE +
                " WHERE " + Symptom.KEY_IS_VISIBLE + " = 1";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Symptom> list = new ArrayList<>();
        while (cursor.moveToNext()){
            list.add(new Symptom(cursor.getInt(cursor.getColumnIndex(Symptom.KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(Symptom.KEY_TITLE)),
                    cursor.getString(cursor.getColumnIndex(Symptom.KEY_ICON)),
                    1,
                    cursor.getInt(cursor.getColumnIndex(Symptom.KEY_IS_USER)),
                    cursor.getInt(cursor.getColumnIndex(Symptom.KEY_LAST_USING))));
        }
        return list;
    }

    public ArrayList<Symptom> getLastUsingSymptomsList(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT TOP 10 " +
                Symptom.KEY_ID + "," +
                Symptom.KEY_TITLE + "," +
                Symptom.KEY_ICON + "," +
                Symptom.KEY_IS_USER + "," +
                Symptom.KEY_LAST_USING +
                " FROM " + Symptom.TABLE +
                " WHERE " + Symptom.KEY_IS_VISIBLE + " = 1" +
                " ORDER BY " + Symptom.KEY_LAST_USING + " DESC";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Symptom> list = new ArrayList<>();
        while (cursor.moveToNext()){
            list.add(new Symptom(cursor.getInt(cursor.getColumnIndex(Symptom.KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(Symptom.KEY_TITLE)),
                    cursor.getString(cursor.getColumnIndex(Symptom.KEY_ICON)),
                    1,
                    cursor.getInt(cursor.getColumnIndex(Symptom.KEY_IS_USER)),
                    cursor.getInt(cursor.getColumnIndex(Symptom.KEY_LAST_USING))));
        }
        return list;
    }

    public Symptom setLastUsing(Symptom symptom){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "UPDATE " + Symptom.TABLE +
                " SET " + Symptom.KEY_LAST_USING + " = ?" +
                " WHERE " + Symptom.KEY_ID + " = ?";
        String[] parameters = new String[2];
        parameters[0] = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        parameters[1] = String.valueOf(symptom.getSymptomId());
        try {
            db.execSQL(query, parameters);
            symptom.setLastUsing(Integer.getInteger(parameters[0]));
            return symptom;
        }catch (Exception ex){
            return null;
        }
    }

    public boolean setIsVisible(int symptomId, boolean isVisible){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "UPDATE " + Symptom.TABLE +
                " SET " + Symptom.KEY_IS_VISIBLE + " = ?" +
                " WHERE " + Symptom.KEY_ID + " = ?";
        String[] parameters = new String[2];
        parameters[0] = isVisible ? "1" : "0";
        parameters[1] = String.valueOf(symptomId);
        try {
            db.execSQL(query, parameters);
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    public Symptom addSymptom(String title, String icon){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "INSERT INTO " + Symptom.TABLE +
                " (" + Symptom.KEY_TITLE + "," + Symptom.KEY_ICON + "," + Symptom.KEY_IS_VISIBLE + "," + Symptom.KEY_IS_USER + ")" +
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
            int symptomId = cursor.getInt(cursor.getColumnIndex("id"));
            return new Symptom(symptomId,title,icon,1,1,0);
        } catch (Exception ex){
            return null;
        }
    }

    public boolean editSymptom(int symptomId, String title, String icon){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "UPDATE " + Symptom.TABLE +
                " SET " + Symptom.KEY_TITLE + " = ?, " + Symptom.KEY_ICON + " = ?" +
                " WHERE " + Symptom.KEY_ID + " = ?";
        String[] parameters = new String[3];
        parameters[0] = title;
        parameters[1] = icon;
        parameters[2] = String.valueOf(symptomId);
        try {
            db.execSQL(query, parameters);
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    public boolean deleteSymptom(Symptom symptom){

        if (!symptom.getIsUser()) return false;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "DELETE FROM " + Symptom.TABLE +
                " WHERE " + Symptom.KEY_ID + " = ?";
        String[] parameters = new String[1];
        parameters[0] = String.valueOf(symptom.getSymptomId());
        try {
            db.execSQL(query, parameters);
            return true;
        } catch (Exception ex){
            return false;
        }
    }
}
