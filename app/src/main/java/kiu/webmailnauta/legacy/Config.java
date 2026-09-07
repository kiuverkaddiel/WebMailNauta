package kiu.webmailnauta.legacy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kiu.webmailnauta.legacy.db.DataBaseHelper;

public class Config {
    public static final String BALL_COUNT_NUMBER = "ball_count_number";
    public static final String BALL_COUNT_NUMBER_BOTE = "ball_count_number_bote";
    public static final String BALL_COUNT_PASS = "ball_count_pass";
    public static final String BALL_MORE_THAN = "ball_more_than";
    public static final String BALL_PERCENT = "ball_percent";
    public static final String BALL_PERCENT_100 = "ball_percent_100";
    public static final String BALL_PERCENT_100_BOTE = "ball_percent_100_BOTE";
    public static final String BALL_PERCENT_BOTE = "ball_percent_bote";
    public static final String BASE_BALL_PRICE = "base_ball_price";
    public static final String BASE_BALL_PRICE_ALT = "base_ball_price_alt";
    public static final String BASE_BALL_PRICE_BOTE = "base_ball_price_bote";
    public static final String BASE_CENTENE_PRICE = "base_centene_price";
    public static final String BASE_CENTENE_PRICE_ALT = "base_centene_price_alt";
    public static final String BASE_CENTENE_PRICE_BOTE = "base_centene_price_bote";
    public static final String BASE_PARLLET_PRICE = "base_parllet_price";
    public static final String BASE_PARLLET_PRICE_ALT = "base_parllet_price_alt";
    public static final String BASE_PARLLET_PRICE_BOTE = "base_parllet_price_bote";
    public static final String CENTENE_PERCENT = "centene_percent";
    public static final String CENTENE_PERCENT_BOTE = "centene_percent_bote";
    public static final String CHECK_LIST = "check_list";
    public static final String CLOSED_LIST = "closed_list";
    public static final String COPADO_BALL_DAY = "copado_ball_day";
    public static final String COPADO_BALL_NIGHT = "copado_ball_night";
    public static final String COPAO_MAX_VALUE_BOTE = "copao_max_value_bote";
    public static final String COPAO_PERCENT = "copao_percent";
    public static final String COPAO_START_BOTE = "copao_percent_bote";
    public static final String COUNT_COPADO_NUMBER_BOTE = "count_copado_number_bote";
    public static final String COUNT_COPADO_NUMBER_LIST = "count_copado_number";
    public static final String COUNT_LIMIT_NUMBER_BOTE = "count_limit_number_bote";
    public static final String COUNT_LIMIT_NUMBER_LIST = "count_limit_number";
    public static final String COUNT_NOT_PLAY_NUMBER_BOTE = "count_not_play_number_bote";
    public static final String COUNT_NOT_PLAY_NUMBER_LIST = "count_not_play_number";
    public static final String DATE = "date";
    public static final String DEFAULT_APP = "default_app";
    public static final String DEFAULT_COPAO = "no";
    public static final String DEFAULT_LAST_SAVE = "9999999999999";
    public static final String DEFAULT_LAST_TOUCH = "0";
    public static final String DEFAULT_LAST_TURN = "0";
    public static final String DEFAULT_LIMIT = "no";
    public static final String DEFAULT_LOCK = "0";
    public static final String DEFAULT_NOT_PLAY = "no";
    public static final String DIRECT_SUM = "direct_sum";
    public static final String EMAIL_SERVER = "email_server";
    public static final String GIVE_CLEAN = "give_clean";
    public static final String GIVE_PERCENT = "give_percent";
    public static final String IS_IN_CLOUD = "is_in_cloud";
    public static final String LAST_SAVE = "last_save";
    public static final String LAST_TOUCH = "last_touch";
    public static final String LAST_TURN = "last_turn";
    public static final String LIMIT_BALL = "limit_ball";
    public static final String LIMIT_BALL_DAY = "limit_ball_day";
    public static final String LIMIT_BALL_NIGHT = "limit_ball_night";
    public static final String LIMIT_BALL_NOT_PLAY = "limit_ball_no_play";
    public static final String LIMIT_CENTENE = "limit_centene";
    public static final String LIMIT_CENTENE_DAY = "limit_centene_day";
    public static final String LIMIT_CENTENE_NIGHT = "limit_centene_night";
    public static final String LIMIT_CENTENE_NOT_PLAY = "limit_centene_no_play";
    public static final String LIMIT_PARLLET = "limit_parllet";
    public static final String LIMIT_PARLLET_DAY = "limit_parllet_day";
    public static final String LIMIT_PARLLET_NIGHT = "limit_parllet_night";
    public static final String LIMIT_PARLLET_NOT_PLAY = "limit_parllet_no_play";
    public static final String LIST_CODE = "list_code";
    public static final String LIST_ID = "list_id";
    public static final String LIST_NUMBER = "list_number";
    public static final String LIST_VERSION = "version";
    public static final String LOCK = "lock";
    public static final String LOCK_APP = "lock_app";
    public static final String MAX_BALL_PRICE = "max_ball_price";
    public static final String MAX_BALL_PRICE_ALT = "max_ball_price_alt";
    public static final String MAX_BALL_PRICE_BOTE = "max_ball_price_bote";
    public static final String MAX_CENTENE_PRICE = "max_centene_price";
    public static final String MAX_CENTENE_PRICE_ALT = "max_centene_price_alt";
    public static final String MAX_CENTENE_PRICE_BOTE = "max_centene_price_bote";
    public static final String MAX_PARLLET_PRICE = "max_parllet_price";
    public static final String MAX_PARLLET_PRICE_ALT = "max_parllet_price_alt";
    public static final String MAX_PARLLET_PRICE_BOTE = "max_parllet_price_bote";
    public static final String MIN_BALL_PERCENT = "min_ball_percent";
    public static final String MIN_BALL_PERCENT_BOTE = "min_ball_percent_bote";
    public static final String NAUTA_EMAIL_SERVER = "nauta_email_server";
    public static final String NOT_PASS_BOTE = "no_pass_bote";
    public static final String OPEN_BOTE = "open_bote";
    public static final String OPEN_BOTE_PLUS_BALL = "open_bote_plus_ball";
    public static final String OPEN_BOTE_PLUS_CENTENE = "open_bote_plus_centene";
    public static final String OPEN_BOTE_PLUS_PARLLET = "open_bote_plus_parllet";
    public static final String PARLLET_COUNT_NUMBER = "parllet_count_number";
    public static final String PARLLET_COUNT_NUMBER_BOTE = "parllet_count_number_bote";
    public static final String PARLLET_COUNT_PASS = "parllet_count_pass";
    public static final String PARLLET_MORE_THAN = "parllet_more_than";
    public static final String PARLLET_PERCENT = "parllet_percent";
    public static final String PARLLET_PERCENT_4850 = "parllet_percent_4850";
    public static final String PARLLET_PERCENT_4850_BOTE = "parllet_percent_4850_bote";
    public static final String PARLLET_PERCENT_BOTE = "parllet_percent_bote";
    public static final String PASSWORD = "password";
    public static final String PAY_BALL_CORRIDO = "pay_ball_corrido";
    public static final String PAY_BALL_CORRIDO_BOTE = "pay_ball_corrido_bote";
    public static final String PAY_BALL_CORRIDO_LIMIT = "pay_ball_corrido_limit";
    public static final String PAY_BALL_CORRIDO_LIMIT_BOTE = "pay_ball_corrido_limit_bote";
    public static final String PAY_BALL_FIJO = "pay_ball_fijo";
    public static final String PAY_BALL_FIJO_BOTE = "pay_ball_fijo_bote";
    public static final String PAY_BALL_FIJO_LIMIT = "pay_ball_fijo_limit";
    public static final String PAY_BALL_FIJO_LIMIT_BOTE = "pay_ball_fijo_limit_bote";
    public static final String PAY_CENTENE = "pay_centene";
    public static final String PAY_CENTENE_4 = "pay_centene_4";
    public static final String PAY_CENTENE_5 = "pay_centene_5";
    public static final String PAY_CENTENE_6 = "pay_centene_6";
    public static final String PAY_CENTENE_7 = "pay_centene_7";
    public static final String PAY_CENTENE_BOTE = "pay_centene_bote";
    public static final String PAY_CENTENE_BOTE_4 = "pay_centene_bote_4";
    public static final String PAY_CENTENE_BOTE_5 = "pay_centene_bote_5";
    public static final String PAY_CENTENE_BOTE_6 = "pay_centene_bote_6";
    public static final String PAY_CENTENE_BOTE_7 = "pay_centene_bote_7";
    public static final String PAY_CENTENE_LIMIT = "pay_centene_limit";
    public static final String PAY_CENTENE_LIMIT_4 = "pay_centene_limit_4";
    public static final String PAY_CENTENE_LIMIT_5 = "pay_centene_limit_5";
    public static final String PAY_CENTENE_LIMIT_6 = "pay_centene_limit_6";
    public static final String PAY_CENTENE_LIMIT_7 = "pay_centene_limit_7";
    public static final String PAY_CENTENE_LIMIT_BOTE = "pay_centene_limit_bote";
    public static final String PAY_CENTENE_LIMIT_BOTE_4 = "pay_centene_limit_bote_4";
    public static final String PAY_CENTENE_LIMIT_BOTE_5 = "pay_centene_limit_bote_5";
    public static final String PAY_CENTENE_LIMIT_BOTE_6 = "pay_centene_limit_bote_6";
    public static final String PAY_CENTENE_LIMIT_BOTE_7 = "pay_centene_limit_bote_7";
    public static final String PAY_FULL_PRICE = "pay_full_price";
    public static final String PAY_FULL_PRICE_BOTE = "pay_full_price_bote";
    public static final String PAY_PARLLET = "pay_parllet";
    public static final String PAY_PARLLET_BOTE = "pay_parllet_bote";
    public static final String PAY_PARLLET_LIMIT = "pay_parllet_limit";
    public static final String PAY_PARLLET_LIMIT_BOTE = "pay_parllet_limit_bote";
    public static final String PERSONAL_PHONE = "personal_phone";
    public static final String PHONES_NUMBERS = "phones_numbers";
    public static final String PLAY_ALL_NUMBER = "play_all_number";
    public static final String PLAY_NAME = "play_name";
    public static final String PRELOCK_TIME_DAY = "prelock_time_day";
    public static final String PRELOCK_TIME_NIGHT = "prelock_time_night";
    public static final String SEPARATE_LIMPIO = "separate_limpio";
    public static final String SEPARATE_LIMPIO_ALT = "separate_limpio_alt";
    public static final String SEPARATE_LIMPIO_BOTE = "separate_limpio_bote";
    public static final String SHOW_BOTE_BUTTON = "show_bote_button";
    public static final String TABLE_CONFIG = "config";
    public static final String TEST_LIST = "test_list";
    public static final String TOP_BALL = "top_ball";
    public static final String TOP_BALL_30 = "top_ball_30";
    public static final String TOP_BALL_30_BOTE = "top_ball_30_bote";
    public static final String TOP_BALL_BOTE = "top_ball_bote";
    public static final String TOP_PARLLET = "top_parllet";
    public static final String TOP_PARLLET_BOTE = "top_parllet_bote";
    public static final String VERSION = "version";
    private static Config instance = null;
    public List<String> FIELDS_KEY;
    private HashMap<String, String> fields = new HashMap<>();
    private boolean has_modified = false;
    private boolean load = false;

    private Config() {
        ArrayList arrayList = new ArrayList();
        this.FIELDS_KEY = arrayList;
        arrayList.add("list_number");
        this.FIELDS_KEY.add(SEPARATE_LIMPIO);
        this.FIELDS_KEY.add(MAX_BALL_PRICE);
        this.FIELDS_KEY.add(BASE_BALL_PRICE);
        this.FIELDS_KEY.add(MAX_PARLLET_PRICE);
        this.FIELDS_KEY.add(BASE_PARLLET_PRICE);
        this.FIELDS_KEY.add(MAX_CENTENE_PRICE);
        this.FIELDS_KEY.add(BASE_CENTENE_PRICE);
        this.FIELDS_KEY.add(MIN_BALL_PERCENT);
        this.FIELDS_KEY.add(BALL_PERCENT);
        this.FIELDS_KEY.add(BALL_COUNT_NUMBER);
        this.FIELDS_KEY.add(BALL_PERCENT_100);
        this.FIELDS_KEY.add(PARLLET_PERCENT);
        this.FIELDS_KEY.add(PARLLET_COUNT_NUMBER);
        this.FIELDS_KEY.add(PARLLET_PERCENT_4850);
        this.FIELDS_KEY.add(CENTENE_PERCENT);
        this.FIELDS_KEY.add(PAY_CENTENE);
        this.FIELDS_KEY.add(PAY_BALL_FIJO);
        this.FIELDS_KEY.add(PAY_BALL_CORRIDO);
        this.FIELDS_KEY.add(PAY_PARLLET);
        this.FIELDS_KEY.add(PAY_CENTENE_LIMIT);
        this.FIELDS_KEY.add(PAY_BALL_FIJO_LIMIT);
        this.FIELDS_KEY.add(PAY_BALL_CORRIDO_LIMIT);
        this.FIELDS_KEY.add(PAY_PARLLET_LIMIT);
        this.FIELDS_KEY.add(SEPARATE_LIMPIO_BOTE);
        this.FIELDS_KEY.add(MAX_BALL_PRICE_BOTE);
        this.FIELDS_KEY.add(BASE_BALL_PRICE_BOTE);
        this.FIELDS_KEY.add(MAX_PARLLET_PRICE_BOTE);
        this.FIELDS_KEY.add(BASE_PARLLET_PRICE_BOTE);
        this.FIELDS_KEY.add(MAX_CENTENE_PRICE_BOTE);
        this.FIELDS_KEY.add(BASE_CENTENE_PRICE_BOTE);
        this.FIELDS_KEY.add(MIN_BALL_PERCENT_BOTE);
        this.FIELDS_KEY.add(BALL_PERCENT_BOTE);
        this.FIELDS_KEY.add(BALL_COUNT_NUMBER_BOTE);
        this.FIELDS_KEY.add(BALL_PERCENT_100_BOTE);
        this.FIELDS_KEY.add(PARLLET_PERCENT_BOTE);
        this.FIELDS_KEY.add(PARLLET_COUNT_NUMBER_BOTE);
        this.FIELDS_KEY.add(PARLLET_PERCENT_4850_BOTE);
        this.FIELDS_KEY.add(CENTENE_PERCENT_BOTE);
        this.FIELDS_KEY.add(PAY_CENTENE_BOTE);
        this.FIELDS_KEY.add(PAY_BALL_FIJO_BOTE);
        this.FIELDS_KEY.add(PAY_BALL_CORRIDO_BOTE);
        this.FIELDS_KEY.add(PAY_PARLLET_BOTE);
        this.FIELDS_KEY.add(PAY_CENTENE_LIMIT_BOTE);
        this.FIELDS_KEY.add(PAY_BALL_FIJO_LIMIT_BOTE);
        this.FIELDS_KEY.add(PAY_BALL_CORRIDO_LIMIT_BOTE);
        this.FIELDS_KEY.add(PAY_PARLLET_LIMIT_BOTE);
        this.FIELDS_KEY.add(SHOW_BOTE_BUTTON);
        this.FIELDS_KEY.add(LOCK);
        this.FIELDS_KEY.add(CHECK_LIST);
        this.FIELDS_KEY.add(LIMIT_BALL_DAY);
        this.FIELDS_KEY.add(LIMIT_PARLLET_DAY);
        this.FIELDS_KEY.add(LIMIT_CENTENE_DAY);
        this.FIELDS_KEY.add(LIMIT_BALL_NIGHT);
        this.FIELDS_KEY.add(LIMIT_PARLLET_NIGHT);
        this.FIELDS_KEY.add(LIMIT_CENTENE_NIGHT);
        this.FIELDS_KEY.add(LIMIT_BALL_NOT_PLAY);
        this.FIELDS_KEY.add(LIMIT_PARLLET_NOT_PLAY);
        this.FIELDS_KEY.add(LIMIT_CENTENE_NOT_PLAY);
        this.FIELDS_KEY.add(PLAY_NAME);
        this.FIELDS_KEY.add(GIVE_PERCENT);
        this.FIELDS_KEY.add(GIVE_CLEAN);
        this.FIELDS_KEY.add(LAST_TOUCH);
        this.FIELDS_KEY.add(LAST_TURN);
        this.FIELDS_KEY.add(LAST_SAVE);
        this.FIELDS_KEY.add(PLAY_ALL_NUMBER);
        this.FIELDS_KEY.add(PAY_FULL_PRICE);
        this.FIELDS_KEY.add(PAY_FULL_PRICE_BOTE);
        this.FIELDS_KEY.add(COPAO_START_BOTE);
        this.FIELDS_KEY.add(COPAO_MAX_VALUE_BOTE);
        this.FIELDS_KEY.add(DIRECT_SUM);
    }

    public String[] getBoteFields() {
        return new String[]{SEPARATE_LIMPIO_BOTE, MAX_BALL_PRICE_BOTE, BASE_BALL_PRICE_BOTE, MAX_PARLLET_PRICE_BOTE, BASE_PARLLET_PRICE_BOTE, MAX_CENTENE_PRICE_BOTE, BASE_CENTENE_PRICE_BOTE, MIN_BALL_PERCENT_BOTE, BALL_PERCENT_BOTE, BALL_COUNT_NUMBER_BOTE, BALL_PERCENT_100_BOTE, PARLLET_PERCENT_BOTE, PARLLET_COUNT_NUMBER_BOTE, PARLLET_PERCENT_4850_BOTE, CENTENE_PERCENT_BOTE, PAY_CENTENE_BOTE, PAY_BALL_FIJO_BOTE, PAY_BALL_CORRIDO_BOTE, PAY_PARLLET_BOTE, PAY_CENTENE_LIMIT_BOTE, PAY_BALL_FIJO_LIMIT_BOTE, PAY_BALL_CORRIDO_LIMIT_BOTE, PAY_PARLLET_LIMIT_BOTE};
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public void reloadData(DataBaseHelper db) {
        this.fields = db.getConfigValues();
        setField(CLOSED_LIST, "false");
        setField(SEPARATE_LIMPIO_ALT, get(SEPARATE_LIMPIO));
        setField(MAX_BALL_PRICE_ALT, get(MAX_BALL_PRICE));
        setField(MAX_PARLLET_PRICE_ALT, get(MAX_PARLLET_PRICE));
        setField(MAX_CENTENE_PRICE_ALT, get(MAX_CENTENE_PRICE));
        setField(BASE_BALL_PRICE_ALT, get(BASE_BALL_PRICE));
        setField(BASE_PARLLET_PRICE_ALT, get(BASE_PARLLET_PRICE));
        setField(BASE_CENTENE_PRICE_ALT, get(BASE_CENTENE_PRICE));
        this.load = true;
    }

    public void loadData(DataBaseHelper db) {
        if (!this.load) {
            reloadData(db);
        }
    }

    public boolean isDataLoaded() {
        return this.load;
    }

    public boolean isAnyFieldEmpty() {
        for (String key : this.FIELDS_KEY) {
            if (getString(key) == null) {
                return true;
            }
        }
        return false;
    }

    public void removeField(String field) {
        this.fields.put(field, Constants.NULL_STRING);
    }

    private String get(String field) {
        String value = this.fields.get(field);
        if (value == null || value.isEmpty()) {
            return null;
        }
        return value;
    }

    public String getString(String field) {
        return get(field);
    }

    public Integer getInteger(String field) {
        String sField = get(field);
        if (sField != null) {
            return Integer.valueOf(sField);
        }
        return null;
    }

    public Float getFloat(String field) {
        String sField = get(field);
        if (sField != null) {
            return Float.valueOf(sField);
        }
        return null;
    }

    public Long getLong(String field) {
        String sField = get(field);
        if (sField != null) {
            return Long.valueOf(sField);
        }
        return null;
    }

    public Boolean getBoolean(String field) {
        String sField = get(field);
        if (sField != null) {
            return Boolean.valueOf(sField);
        }
        return null;
    }

    public List<String> getListString(String field) {
        String sField = get(field);
        if (sField != null) {
            return convertStringToList(sField);
        }
        return null;
    }

    private List<String> convertStringToList(String items) {
        if (items == null) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (String part : items.split(",")) {
            if (!part.isEmpty()) {
                list.add(part);
            }
        }
        return list;
    }

    private String convertListToString(List<String> list) {
        if (list == null) {
            return null;
        }
        String items = Constants.NULL_STRING;
        for (int i = 0; i < list.size() - 1; i++) {
            String item = list.get(i);
            if (!item.isEmpty()) {
                items = items + item + ",";
            }
        }
        String item2 = list.get(list.size() - 1);
        if (item2.isEmpty()) {
            return items;
        }
        return items + item2;
    }

    public void setField(String field, String value) {
        if (value != null && !value.equals(Constants.NULL_STRING)) {
            this.fields.put(field, value);
        }
    }

    public void setItem(String field, String item) {
        String sField;
        if (item != null && !item.equals(Constants.NULL_STRING)) {
            String sField2 = get(field);
            if (sField2 != null) {
                sField = sField2 + "," + item;
            } else {
                sField = item;
            }
            setField(field, sField);
        }
    }

    public void delItem(String field, String item) {
        String sField;
        if (item != null && (sField = get(field)) != null) {
            List<String> list = convertStringToList(sField);
            list.remove(item);
            setField(field, convertListToString(list));
        }
    }

    public void updateField(DataBaseHelper db, String field) {
        String value = this.fields.get(field);
        if (value != null) {
            db.updateConfigValue(field, value);
        }
    }

    public void update(DataBaseHelper db) {
        for (String key : this.fields.keySet()) {
            updateField(db, key);
        }
        this.has_modified = false;
    }

    public boolean needUpdate() {
        return this.has_modified;
    }
}
