package co.sunclick.ladycalendar.Model;

/**
 * Created by OZ on 15.05.16.
 */
public class Mood {

    public static final String TABLE = "mood";

    public static final String KEY_ID = "mood_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ICON = "icon";
    public static final String KEY_IS_VISIBLE = "is_visible";
    public static final String KEY_IS_USER = "is_user";
    public static final String KEY_LAST_USING = "last_using";

    private int moodId;
    private String title;
    private String icon;
    private Boolean isVisible;
    private Boolean isUser;
    private int lastUsing; // format yyyyMMddHHmmss

    public Mood(int moodId, String title, String icon, int isVisible, int isUser, int lastUsing){

        this.moodId = moodId;
        this.title = title;
        this.icon = icon;
        this.isVisible = isVisible == 1;
        this.isUser = isUser == 1;
        this.lastUsing = lastUsing;
    }

    public int getMoodId() {
        return moodId;
    }

    public Boolean getIsUser() {
        return isUser;
    }

    public void setLastUsing(int lastUsing) {
        this.lastUsing = lastUsing;
    }
}
