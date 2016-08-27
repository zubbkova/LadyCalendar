package co.sunclick.ladycalendar.Helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import co.sunclick.ladycalendar.Model.Contraceptive;
import co.sunclick.ladycalendar.Model.Medicine;

/**
 * Created by OZ on 15.05.16.
 */
public class MedicineRepo {

    DBHelper dbHelper;

    public MedicineRepo(Context context){

        dbHelper = new DBHelper(context);
    }

    public ArrayList<Medicine> getMedicinesList(int localProfileId) throws ParseException {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT " +
                Medicine.TABLE + "." + Medicine.KEY_ID + "," +
                Medicine.TABLE + "." + Medicine.KEY_TITLE + "," +
                Medicine.TABLE + "." + Medicine.KEY_DESCRIPTION + "," +
                Medicine.TABLE + "." + Medicine.KEY_CONTRACEPTIVE_ID + "," +
                Contraceptive.TABLE + "." + Contraceptive.KEY_ID + "," +
                Contraceptive.TABLE + "." + Contraceptive.KEY_TYPE + "," +
                Contraceptive.TABLE + "." + Contraceptive.KEY_IS_EVERY_DAY + "," +
                Contraceptive.TABLE + "." + Contraceptive.KEY_DAYS_OF_TAKE + "," +
                Contraceptive.TABLE + "." + Contraceptive.KEY_DAYS_OF_BREAK + "," +
                Contraceptive.TABLE + "." + Contraceptive.KEY_FIRST_DATE + "," +
                Contraceptive.TABLE + "." + Contraceptive.KEY_TAKE_EVERY + "," +
                " FROM " + Medicine.TABLE +
                " LEFT JOIN " + Contraceptive.TABLE + " ON " + Contraceptive.TABLE + "." + Contraceptive.KEY_ID + " = " + Medicine.KEY_CONTRACEPTIVE_ID +
                " WHERE " + Medicine.KEY_LOCAL_PROFILE_ID + " = " + localProfileId;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Medicine> list = new ArrayList<>();
        while (cursor.moveToNext()){
            if (cursor.getInt(cursor.getColumnIndex(Medicine.KEY_CONTRACEPTIVE_ID)) == 0){
                list.add(new Medicine(
                        cursor.getInt(cursor.getColumnIndex(Medicine.KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(Medicine.KEY_TITLE)),
                        cursor.getString(cursor.getColumnIndex(Medicine.KEY_DESCRIPTION)),
                        null));
            }else {
                list.add(new Medicine(
                        cursor.getInt(cursor.getColumnIndex(Medicine.KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(Medicine.KEY_TITLE)),
                        cursor.getString(cursor.getColumnIndex(Medicine.KEY_DESCRIPTION)),
                        new Contraceptive(
                                cursor.getInt(cursor.getColumnIndex(Contraceptive.KEY_ID)),
                                cursor.getInt(cursor.getColumnIndex(Contraceptive.KEY_TYPE)),
                                cursor.getInt(cursor.getColumnIndex(Contraceptive.KEY_IS_EVERY_DAY)),
                                cursor.getInt(cursor.getColumnIndex(Contraceptive.KEY_DAYS_OF_TAKE)),
                                cursor.getInt(cursor.getColumnIndex(Contraceptive.KEY_DAYS_OF_BREAK)),
                                cursor.getString(cursor.getColumnIndex(Contraceptive.KEY_FIRST_DATE)),
                                cursor.getInt(cursor.getColumnIndex(Contraceptive.KEY_TAKE_EVERY)))
                ));
            }

        }
        return list;
    }

    public boolean editMedicine(int medicineId, String title, String description, boolean isContraceptive,
                                int contraceptiveId, int contraceptiveType, boolean isEveryDay, int daysOfTake,
                                int daysOfBreak, String firstDate, int takeEvery){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query;
        if (isContraceptive){
            // значит есть контрацептив
            if (contraceptiveId == 0){
                // значит надо добавить

            }else {
                // значит надо отредактировать
                query = "UPDATE " + Contraceptive.TABLE +
                        " SET " +
            }
        }else {
            // нет контрацептива, просто редактируем
        }

        query = "UPDATE " + Medicine.TABLE +
                " SET " + Medicine.KEY_TITLE + " = ?, " + Medicine.KEY_DESCRIPTION + " = ?" +
                " WHERE " + Medicine.KEY_ID + " = ?";
        String[] parameters = new String[3];
        parameters[0] = title;
        parameters[1] = description;
        parameters[2] = String.valueOf(medicineId);
        try {
            db.execSQL(query, parameters);

            return true;
        } catch (Exception ex){
            return false;
        }
    }
}
