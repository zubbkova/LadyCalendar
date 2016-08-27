package co.sunclick.ladycalendar.Helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import co.sunclick.ladycalendar.Model.Settings;

/**
 * Created by OZ on 15.05.16.
 */
public class SettingsRepo {

    private DBHelper dbHelper;

    public SettingsRepo(Context context){
        dbHelper = new DBHelper(context);
    }

    public Settings getSettingsByLocalProfile(int localProfileId){

        // возвращает настройки для профиля при запуске приложения, если их нет - создает по умолчанию
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + Settings.TABLE +
                " WHERE " + Settings.KEY_LOCAL_PROFILE_ID + " = " + localProfileId;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToNext()){
            return new Settings(cursor.getInt(cursor.getColumnIndex(Settings.KEY_THEME)),
                    cursor.getColumnIndex(Settings.KEY_IS_PREGNANCY), cursor.getColumnIndex(Settings.KEY_PERIOD_LENGTH),
                    cursor.getColumnIndex(Settings.KEY_IS_COUNT_PERIOD_LENGTH), cursor.getColumnIndex(Settings.KEY_CYCLE_LENGTH),
                    cursor.getColumnIndex(Settings.KEY_IS_COUNT_CYCLE_LENGTH), cursor.getColumnIndex(Settings.KEY_IS_IGNORE_IRREGULAR),
                    cursor.getColumnIndex(Settings.KEY_LUTEAL_PHASE_LENGTH), cursor.getColumnIndex(Settings.KEY_IS_COUNT_LUTEAL_PHASE_LENGTH));
        }
        // создать новые настройки
        query = "INSERT INTO " + Settings.TABLE +
                " VALUES (?,?,?,?,?,?,?,?,?,?)";
        String[] parameters = new String[10];
        parameters[0] = String.valueOf(localProfileId);
        parameters[1] = "1";
        parameters[2] = "0";
        parameters[3] = "4";
        parameters[4] = "1";
        parameters[5] = "28";
        parameters[6] = "1";
        parameters[7] = "1";
        parameters[8] = "14";
        parameters[9] = "1";
        db.execSQL(query, parameters);
        return new Settings(1, 0, 4, 1, 28, 1, 1, 14, 1);
    }

    public boolean setTheme(int localProfileId, int theme){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "UPDATE " + Settings.TABLE +
                " SET " + Settings.KEY_THEME + " = ?" +
                " WHERE " + Settings.KEY_LOCAL_PROFILE_ID + " = ?";
        String[] parameters = new String[2];
        parameters[0] = String.valueOf(theme);
        parameters[1] = String.valueOf(localProfileId);
        try {
            db.execSQL(query, parameters);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public boolean setPregnancy(int localProfileId, boolean isPregnancy){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "UPDATE " + Settings.TABLE +
                " SET " + Settings.KEY_IS_PREGNANCY + " = ?" +
                " WHERE " + Settings.KEY_LOCAL_PROFILE_ID + " = ?";
        String[] parameters = new String[2];
        parameters[0] = isPregnancy ? "1" : "0";
        parameters[1] = String.valueOf(localProfileId);
        try {
            db.execSQL(query, parameters);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public boolean setPeriodLength(int localProfileId, int periodLength){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "UPDATE " + Settings.TABLE +
                " SET " + Settings.KEY_PERIOD_LENGTH + " = ?" +
                " WHERE " + Settings.KEY_LOCAL_PROFILE_ID + " = ?";
        String[] parameters = new String[2];
        parameters[0] = String.valueOf(periodLength);
        parameters[1] = String.valueOf(localProfileId);
        try {
            db.execSQL(query, parameters);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public boolean setIsCountPeriodLength(int localProfileId, boolean isCountPeriodLength){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "UPDATE " + Settings.TABLE +
                " SET " + Settings.KEY_IS_COUNT_PERIOD_LENGTH + " = ?" +
                " WHERE " + Settings.KEY_LOCAL_PROFILE_ID + " = ?";
        String[] parameters = new String[2];
        parameters[0] = isCountPeriodLength ? "1" : "0";
        parameters[1] = String.valueOf(localProfileId);
        try {
            db.execSQL(query, parameters);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public boolean setCycleLength(int localProfileId, int cycleLength){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "UPDATE " + Settings.TABLE +
                " SET " + Settings.KEY_CYCLE_LENGTH + " = ?" +
                " WHERE " + Settings.KEY_LOCAL_PROFILE_ID + " = ?";
        String[] parameters = new String[2];
        parameters[0] = String.valueOf(cycleLength);
        parameters[1] = String.valueOf(localProfileId);
        try {
            db.execSQL(query, parameters);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public boolean setIsCountCycleLength(int localProfileId, boolean isCountCycleLength){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "UPDATE " + Settings.TABLE +
                " SET " + Settings.KEY_IS_COUNT_CYCLE_LENGTH + " = ?" +
                " WHERE " + Settings.KEY_LOCAL_PROFILE_ID + " = ?";
        String[] parameters = new String[2];
        parameters[0] = isCountCycleLength ? "1" : "0";
        parameters[1] = String.valueOf(localProfileId);
        try {
            db.execSQL(query, parameters);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public boolean setIsIgnoreIrregular(int localProfileId, boolean isIgnoreIrregular){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "UPDATE " + Settings.TABLE +
                " SET " + Settings.KEY_IS_IGNORE_IRREGULAR + " = ?" +
                " WHERE " + Settings.KEY_LOCAL_PROFILE_ID + " = ?";
        String[] parameters = new String[2];
        parameters[0] = isIgnoreIrregular ? "1" : "0";
        parameters[1] = String.valueOf(localProfileId);
        try {
            db.execSQL(query, parameters);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public boolean setLutealPhaseLength(int localProfileId, int lutealPhaseLength){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "UPDATE " + Settings.TABLE +
                " SET " + Settings.KEY_LUTEAL_PHASE_LENGTH + " = ?" +
                " WHERE " + Settings.KEY_LOCAL_PROFILE_ID + " = ?";
        String[] parameters = new String[2];
        parameters[0] = String.valueOf(lutealPhaseLength);
        parameters[1] = String.valueOf(localProfileId);
        try {
            db.execSQL(query, parameters);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public boolean setIsCountLutealPhaseLength(int localProfileId, boolean isCountLutealPhaseLength){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "UPDATE " + Settings.TABLE +
                " SET " + Settings.KEY_IS_COUNT_LUTEAL_PHASE_LENGTH + " = ?" +
                " WHERE " + Settings.KEY_LOCAL_PROFILE_ID + " = ?";
        String[] parameters = new String[2];
        parameters[0] = isCountLutealPhaseLength ? "1" : "0";
        parameters[1] = String.valueOf(localProfileId);
        try {
            db.execSQL(query, parameters);
            return true;
        }catch (Exception ex){
            return false;
        }
    }
}
