package co.sunclick.ladycalendar.Model;

/**
 * Created by OZ on 12.05.16.
 */
public class LocalProfile {

    public static final String TABLE = "local_profile";

    public static final String KEY_ID = "local_profile_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_IS_ACTIVE = "is_active";

    private int localProfileId;
    private String name;
    private boolean isActive;

    public LocalProfile(int localProfileId, String name, int isActive) {
        this.localProfileId = localProfileId;
        this.name = name;
        this.isActive = isActive == 1;
    }

    public int getLocalProfileId() {
        return localProfileId;
    }

    public boolean getIsActive() {
        return isActive;
    }
}
