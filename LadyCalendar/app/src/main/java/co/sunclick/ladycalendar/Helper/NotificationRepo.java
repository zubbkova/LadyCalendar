package co.sunclick.ladycalendar.Helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.util.ArrayList;

import co.sunclick.ladycalendar.Model.Notification;

/**
 * Created by OZ on 15.05.16.
 */
public class NotificationRepo {

    private DBHelper dbHelper;

    public NotificationRepo(Context context){

        dbHelper = new DBHelper(context);
    }

    public ArrayList<Notification> getNotificationsList(int localProfileId) throws ParseException {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + Notification.TABLE +
                " WHERE " + Notification.KEY_LOCAL_PROFILE_ID + " = " + localProfileId;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Notification> list = new ArrayList<>();
        while (cursor.moveToNext()){
            list.add(new Notification(
                    cursor.getInt(cursor.getColumnIndex(Notification.KEY_ID)),
                    cursor.getInt(cursor.getColumnIndex(Notification.KEY_MEDICINE_ID)),
                    cursor.getString(cursor.getColumnIndex(Notification.KEY_TIME)),
                    cursor.getString(cursor.getColumnIndex(Notification.KEY_MESSAGE)),
                    cursor.getString(cursor.getColumnIndex(Notification.KEY_MELODY)),
                    cursor.getInt(cursor.getColumnIndex(Notification.KEY_IS_VIBRATE))));
        }
        return list;
    }

    public ArrayList<Notification> getNotificationsListByMedicine(int localProfileId, int medicineId) throws ParseException {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + Notification.TABLE +
                " WHERE " + Notification.KEY_LOCAL_PROFILE_ID + " = " + localProfileId +
                " AND " + Notification.KEY_MEDICINE_ID + " = " + medicineId;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Notification> list = new ArrayList<>();
        while (cursor.moveToNext()){
            list.add(new Notification(
                    cursor.getInt(cursor.getColumnIndex(Notification.KEY_ID)),
                    medicineId,
                    cursor.getString(cursor.getColumnIndex(Notification.KEY_TIME)),
                    cursor.getString(cursor.getColumnIndex(Notification.KEY_MESSAGE)),
                    cursor.getString(cursor.getColumnIndex(Notification.KEY_MELODY)),
                    cursor.getInt(cursor.getColumnIndex(Notification.KEY_IS_VIBRATE))));
        }
        return list;
    }

    public Notification addNotification(int localProfileId, int medicineId, String time, String message, String melody, boolean isVibrate){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "INSERT INTO " + Notification.TABLE + " (" +
                Notification.KEY_LOCAL_PROFILE_ID + "," +
                Notification.KEY_MEDICINE_ID + "," +
                Notification.KEY_TIME + "," +
                Notification.KEY_MESSAGE + "," +
                Notification.KEY_MELODY + "," +
                Notification.KEY_IS_VIBRATE  +
                " VALUES (?,?,?,?,?,?)";
        String[] parameters = new String[6];
        parameters[0] = String.valueOf(localProfileId);
        parameters[1] = String.valueOf(medicineId);
        parameters[2] = time;
        parameters[3] = message;
        parameters[4] = melody;
        parameters[5] = isVibrate ? "1" : "0";

        try {
            db.execSQL(query, parameters);
            query = "SELECT last_insert_rowid() as id";
            Cursor cursor = db.rawQuery(query, null);
            int notificationId = cursor.getInt(cursor.getColumnIndex("id"));
            return new Notification(notificationId,medicineId,time,message,melody,isVibrate ? 1 : 0);
        }catch (Exception ex){
            return null;
        }
    }

    public boolean editNotification(int notificationId, String time, String message, String melody, boolean isVibrate){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "UPDATE " + Notification.TABLE + " SET " +
                Notification.KEY_TIME + " = ?, " +
                Notification.KEY_MESSAGE + " = ?" +
                Notification.KEY_MELODY + " = ?" +
                Notification.KEY_IS_VIBRATE + " = ?" +
                " WHERE " + Notification.KEY_ID + " = ?";
        String[] parameters = new String[5];
        parameters[0] = time;
        parameters[1] = message;
        parameters[2] = melody;
        parameters[3] = isVibrate ? "1" : "0";
        parameters[4] = String.valueOf(notificationId);
        try {
            db.execSQL(query, parameters);
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    public boolean deleteNotification(int notificationId){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "DELETE FROM " + Notification.TABLE +
                " WHERE " + Notification.KEY_ID + " = " + notificationId;
        try {
            db.execSQL(query);
            return true;
        } catch (Exception ex){
            return false;
        }
    }
}
