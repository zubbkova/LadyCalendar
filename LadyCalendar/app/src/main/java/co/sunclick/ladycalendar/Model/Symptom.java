package co.sunclick.ladycalendar.Model;

/**
 * Created by OZ on 15.05.16.
 */
public class Symptom {

    public static final String TABLE = "symptom";

    public static final String KEY_ID = "symptom_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ICON = "icon";
    public static final String KEY_IS_VISIBLE = "is_visible";
    public static final String KEY_IS_USER = "is_user";
    public static final String KEY_LAST_USING = "last_using";

    private int symptomId;
    private String title;
    private String icon;
    private Boolean isVisible;
    private Boolean isUser;
    private int lastUsing; // format yyyyMMddHHmmss

    public Symptom(int symptomId, String title, String icon, int isVisible, int isUser, int lastUsing){

        this.symptomId = symptomId;
        this.title = title;
        this.icon = icon;
        this.isVisible = isVisible == 1;
        this.isUser = isUser == 1;
        this.lastUsing = lastUsing;
    }

    public int getSymptomId() {
        return symptomId;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public Boolean getIsUser() {
        return isUser;
    }

    public void setLastUsing(int lastUsing) {
        this.lastUsing = lastUsing;
    }
}
