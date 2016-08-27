package co.sunclick.ladycalendar.Class;

import android.content.Context;

import java.util.ArrayList;

import co.sunclick.ladycalendar.Helper.LocalProfileRepo;
import co.sunclick.ladycalendar.Helper.SettingsRepo;
import co.sunclick.ladycalendar.Model.LocalProfile;
import co.sunclick.ladycalendar.Model.Settings;

/**
 * Created by OZ on 12.05.16.
 */
public class SharedInstance {

    private static SharedInstance sharedInstance;

    private ArrayList<LocalProfile> localProfilesList;
    private LocalProfile activeLocalProfile;
    private Settings settings;

    public static SharedInstance getSharedInstance(Context context){
        if (sharedInstance == null){
            sharedInstance = new SharedInstance(context);
        }
        return sharedInstance;
    }

    private SharedInstance(Context context){

        // получить список профайлов
        localProfilesList = new LocalProfileRepo(context).getLocalProfilesList();
        // получить активный профайл
        activeLocalProfile = new LocalProfileRepo(context).getActiveLocalProfile();
        if (activeLocalProfile == null && localProfilesList.size() > 0){
            activeLocalProfile = localProfilesList.get(1);
        }
        // получить настройки
        settings = new SettingsRepo(context).getSettingsByLocalProfile(activeLocalProfile.getLocalProfileId());
    }
}
