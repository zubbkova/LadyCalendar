package co.sunclick.ladycalendar.Model;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by OZ on 15.05.16.
 */
public class Notification {

    public static final String TABLE = "notification";

    public static final String KEY_ID = "notification_id";
    public static final String KEY_LOCAL_PROFILE_ID = "local_profile_id";
    public static final String KEY_MEDICINE_ID = "medicine_id";
    public static final String KEY_TIME = "time";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_MELODY = "melody";
    public static final String KEY_IS_VIBRATE = "is_vibrate";

    private int notificationId;
    private int medicineId;
    private Time time; // format HHmm
    private String message;
    private String melody;
    private boolean isVibrate;

    public Notification(int notificationId, int medicineId, String time, String message, String melody, int isVibrate) throws ParseException {

        this.notificationId = notificationId;
        this.medicineId = medicineId;
        this.time = new Time(new SimpleDateFormat("HHmm").parse(time).getTime());
        this.message = message;
        this.melody = melody;
        this.isVibrate = isVibrate == 1;
    }
}
