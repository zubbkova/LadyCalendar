package co.sunclick.ladycalendar.Helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import co.sunclick.ladycalendar.Model.LocalProfile;

/**
 * Created by OZ on 12.05.16.
 */
public class LocalProfileRepo {

    private DBHelper dbHelper;

    public LocalProfileRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public ArrayList<LocalProfile> getLocalProfilesList() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                LocalProfile.KEY_ID + "," +
                LocalProfile.KEY_NAME + "," +
                LocalProfile.KEY_IS_ACTIVE +
                " FROM " + LocalProfile.TABLE;
        ArrayList<LocalProfile> localProfilesList = new ArrayList<LocalProfile>();
        Cursor cursor = db.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                LocalProfile localProfile = new LocalProfile(cursor.getInt(cursor.getColumnIndex(LocalProfile.KEY_ID)),cursor.getString(cursor.getColumnIndex(LocalProfile.KEY_NAME)),cursor.getInt(cursor.getColumnIndex(LocalProfile.KEY_IS_ACTIVE)));
                localProfilesList.add(localProfile);
            }
        cursor.close();
        db.close();
        return localProfilesList;
    }

    public LocalProfile getActiveLocalProfile(){

        // метод вызывается при запуске приложения
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT " +
                LocalProfile.KEY_ID + "," +
                LocalProfile.KEY_NAME + "," +
                LocalProfile.KEY_IS_ACTIVE +
                " FROM " + LocalProfile.TABLE +
                " WHERE " + LocalProfile.KEY_IS_ACTIVE + " = 1";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToNext()){
            return new LocalProfile(cursor.getInt(cursor.getColumnIndex(LocalProfile.KEY_ID)),cursor.getString(cursor.getColumnIndex(LocalProfile.KEY_NAME)),cursor.getInt(cursor.getColumnIndex(LocalProfile.KEY_IS_ACTIVE)));
        }
        // если вдруг нет активного - проставить первому по умолчанию
        query = "UPDATE " + LocalProfile.TABLE +
                " SET " + LocalProfile.KEY_IS_ACTIVE + " = ?" +
                " WHERE " + LocalProfile.KEY_ID + " = ?";
        String[] parameters = new String[2];
        parameters[0] = "1";
        parameters[1] = "1";
        try {
            db.execSQL(query, parameters);
        } catch (Exception ex){
            // если вдруг нет никакого профайла - добавить
            query = "INSERT INTO " + LocalProfile.TABLE +
                    " VALUES (?,?,?,?)";
            parameters = new String[4];
            parameters[0] = "1";
            parameters[1] = "User by default";
            parameters[2] = "";
            parameters[3] = "1";
            db.execSQL(query, parameters);
        }
        return getLocalProfileById(1);
    }

    private LocalProfile getLocalProfileById(int localProfileId){

        // возвращает объект по ИД
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT " +
                LocalProfile.KEY_ID + "," +
                LocalProfile.KEY_NAME + "," +
                LocalProfile.KEY_IS_ACTIVE +
                " FROM " + LocalProfile.TABLE +
                " WHERE " + LocalProfile.KEY_ID + " = " + localProfileId;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToNext()){
            return new LocalProfile(cursor.getInt(cursor.getColumnIndex(LocalProfile.KEY_ID)),cursor.getString(cursor.getColumnIndex(LocalProfile.KEY_NAME)),cursor.getInt(cursor.getColumnIndex(LocalProfile.KEY_IS_ACTIVE)));
        }
        return null;
    }

    public boolean login(String password){

        // метод при введении пин-кода
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT " +
                LocalProfile.KEY_ID +
                " FROM " + LocalProfile.TABLE +
                " WHERE " + LocalProfile.KEY_IS_ACTIVE + " = 1" +
                " AND " + LocalProfile.KEY_PASSWORD + " = " + password;
        Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToNext();
    }

    public boolean setActiveUserProfile(int localProfileId){

        // выставляет активность профиля, когда пользователь его меняет в настройках
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "UPDATE " + LocalProfile.TABLE +
                " SET " + LocalProfile.KEY_IS_ACTIVE + " = ?" +
                " WHERE " + LocalProfile.KEY_ID + " = ?";
        String[] parameters = new String[2];
        parameters[0] = "1";
        parameters[1] = String.valueOf(localProfileId);
        try {
            db.execSQL(query, parameters);
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    public boolean editUserName(int localProfileId, String userName){

        // выставляет имя профиля, когда пользователь его меняет в настройках или при приветствии
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "UPDATE " + LocalProfile.TABLE +
                " SET " + LocalProfile.KEY_NAME + " = ?" +
                " WHERE " + LocalProfile.KEY_ID + " = ?";
        String[] parameters = new String[2];
        parameters[0] = userName;
        parameters[1] = String.valueOf(localProfileId);
        try {
            db.execSQL(query, parameters);
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    public LocalProfile addLocalProfile(String userName){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "INSERT INTO " + LocalProfile.TABLE +
                " (" + LocalProfile.KEY_NAME + ")" +
                " VALUES (?)";
        String[] parameters = new String[1];
        parameters[0] = userName;
        try {
            db.execSQL(query, parameters);
            query = "SELECT last_insert_rowid() as id";
            Cursor cursor = db.rawQuery(query, null);
            int localProfileId = cursor.getInt(cursor.getColumnIndex("id"));
            return new LocalProfile(localProfileId,userName,0);
        } catch (Exception ex){
            return null;
        }
    }

    public boolean deleteLocalProfile(LocalProfile localProfile){

        if (!localProfile.getIsActive()) return false;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "DELETE FROM " + LocalProfile.TABLE +
                " WHERE " + LocalProfile.KEY_ID + " = " + localProfile.getLocalProfileId();
        try {
            db.execSQL(query);
            return true;
        } catch (Exception ex){
            return false;
        }
    }
}
