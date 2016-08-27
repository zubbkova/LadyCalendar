package co.sunclick.ladycalendar.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by OZ on 17.05.16.
 */
public class Contraceptive {

    public static final String TABLE = "contraceptive";

    public static final String KEY_ID = "contraceptive_id";
    public static final String KEY_TYPE = "type";
    public static final String KEY_IS_EVERY_DAY = "is_every_day";
    public static final String KEY_DAYS_OF_TAKE = "days_of_take";
    public static final String KEY_DAYS_OF_BREAK = "days_of_break";
    public static final String KEY_FIRST_DATE = "first_date";
    public static final String KEY_TAKE_EVERY = "take_every";

    private int contraceptiveId;
    private int type;
    private boolean isEveryDay;
    private int daysOfTake;
    private int daysOfBreak;
    private Date firstDate; // format yyyyMMdd
    private int takeEvery;

    public Contraceptive(int contraceptiveId, int type, int isEveryDay, int daysOfTake, int daysOfBreak, String firstDate, int takeEvery) throws ParseException {

        this.contraceptiveId = contraceptiveId;
        this.type = type;
        this.isEveryDay = isEveryDay == 1;
        this.daysOfTake = daysOfTake;
        this.daysOfBreak = daysOfBreak;
        this.firstDate = new SimpleDateFormat("yyyyMMdd").parse(firstDate);
        this.takeEvery = takeEvery;
    }
}
