package co.sunclick.ladycalendar.Model;

import java.util.ArrayList;

/**
 * Created by OZ on 15.05.16.
 */
public class Medicine {

    public static final String TABLE = "medicine";

    public static final String KEY_ID = "medicine_id";
    public static final String KEY_LOCAL_PROFILE_ID = "local_profile_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_CONTRACEPTIVE_ID = "contraceptive_id";

    private int medicineId;
    private String title;
    private String description;
    private Contraceptive contraceptive;
    private ArrayList<Notification> notifications;

    public Medicine(int medicineId, String title, String description, Contraceptive contraceptive){

        this.medicineId = medicineId;
        this.title = title;
        this.description = description;
        this.contraceptive = contraceptive;
    }

    public void setNotifications(ArrayList<Notification> notifications) {

        this.notifications = notifications;
    }
}
