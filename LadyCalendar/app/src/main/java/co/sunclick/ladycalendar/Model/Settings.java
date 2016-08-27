package co.sunclick.ladycalendar.Model;

/**
 * Created by OZ on 15.05.16.
 */
public class Settings {

    public static final String TABLE = "settings";

    public static final String KEY_LOCAL_PROFILE_ID = "local_profile_id";
    public static final String KEY_THEME = "theme";
    public static final String KEY_IS_PREGNANCY = "is_pregnancy";
    public static final String KEY_PERIOD_LENGTH = "period_length";
    public static final String KEY_IS_COUNT_PERIOD_LENGTH = "is_count_period_length";
    public static final String KEY_CYCLE_LENGTH = "cycle_length";
    public static final String KEY_IS_COUNT_CYCLE_LENGTH = "is_count_cycle_length";
    public static final String KEY_IS_IGNORE_IRREGULAR = "is_ignore_irregular";
    public static final String KEY_LUTEAL_PHASE_LENGTH = "luteal_phase_length";
    public static final String KEY_IS_COUNT_LUTEAL_PHASE_LENGTH = "IS_COUNT_luteal_phase_length";

    private int theme;
    private boolean isPregnancy;
    private int periodLength;
    private boolean isCountPeriodLength;
    private int cycleLength;
    private boolean isCountCycleLength;
    private boolean isIgnoreIrregular;
    private int lutealPhaseLength;
    private boolean isCountLutealPhaseLength;

    public Settings(int theme, int isPregnancy, int periodLength, int isCountPeriodLength, int cycleLength, int isCountCycleLength, int isIgnoreIrregular, int lutealPhaseLength, int isCountLutealPhaseLength){

        this.theme = theme;
        this.isPregnancy = isPregnancy == 1;
        this.periodLength = periodLength;
        this.isCountPeriodLength = isCountPeriodLength == 1;
        this.cycleLength = cycleLength;
        this.isCountCycleLength = isCountCycleLength == 1;
        this.isIgnoreIrregular = isIgnoreIrregular == 1;
        this.lutealPhaseLength = lutealPhaseLength;
        this.isCountLutealPhaseLength = isCountLutealPhaseLength == 1;
    }
}
